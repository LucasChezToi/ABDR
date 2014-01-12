package kvstoreRmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Gateway extends UnicastRemoteObject implements IGateway{

	public static Map<String, IServeur> mapServeur = new HashMap<String, IServeur>();
	public static Map<String, Integer> mapProfile = new HashMap<String, Integer>();

	
	private int MAX_SERVEUR = 2;
	private static final Map<IServeur, String[]> confServeur = new HashMap<IServeur, String[]>();
	private static Map<IServeur, Integer> serveurSize = new HashMap<IServeur, Integer>();
	
	private Registry myRegistry[] = new Registry[2];

	public Gateway() throws RemoteException{
		myRegistry[0] = LocateRegistry.getRegistry("localhost", 55553);
		myRegistry[1] = LocateRegistry.getRegistry("localhost", 55555);

	}
	//remlpir serveurSize	
	@Override
	public int comit(int profile) throws RemoteException{
		IServeur serveurDest = mapServeur.get("profile"+profile);
		if (serveurDest == null){
			try {
				serveurDest = (IServeur) myRegistry[profile%MAX_SERVEUR].lookup("Serveur"+(profile%MAX_SERVEUR));
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mapServeur.put("profile"+profile, serveurDest);
			mapProfile.put("profile"+profile, 0);
			serveurSize.put(serveurDest,0);
			String confKvStore[] = {"kvstore"+profile%MAX_SERVEUR,"ari-31-201-05","500"+((profile%MAX_SERVEUR)*2)};
			confServeur.put(serveurDest, confKvStore);
		}
		serveurDest.commit("profile"+profile, mapProfile.get("profile"+profile));
		serveurSize.put(serveurDest,serveurSize.get(serveurDest)+10);
		mapProfile.put("profile"+profile,mapProfile.get("profile"+profile)+10);		

		IServeur migre = needsMigration(serveurDest);
		if(migre != null){
			System.out.println("migration du profile"+profile+" de "+serveurDest.getNameServeur()+" vers"+migre.getNameServeur() );
			migrate("profile"+profile,migre);
		}
//		System.out.println("test migration ok");
		return 0;
	}

	@Override
	public int delete(int profile) throws RemoteException{
		IServeur tmp = mapServeur.get("profile"+profile);
		if(tmp == null){
			return -1;
		}
		serveurSize.put(tmp, serveurSize.get(tmp)-mapProfile.get("profile"+profile));
		tmp.delete("profile"+profile,mapProfile.get("profile"+profile));
		mapServeur.remove("profile"+profile);

		return 0;
	}

	@Override
	public synchronized String display(String profile)throws RemoteException{
		if (mapServeur.get(profile)==null){
			return "le "+profile+" n'existe pas !";
		}
		return mapServeur.get(profile).displayTr(profile,mapProfile.get(profile));
	}

	private IServeur needsMigration(IServeur serv){
		int nbObjet = serveurSize.get(serv);
		if(nbObjet==0){
			return null;
		}
		int min =0;
		Iterator<IServeur> iter = serveurSize.keySet().iterator();
		IServeur tmp;
		while(iter.hasNext()){
			tmp = iter.next();
			min = serveurSize.get(tmp);
			if( min <= nbObjet/2) return tmp; 
		}
		return null;
	}

	private int migrate(String profile, IServeur serveurDest) throws RemoteException{
		IServeur serveurSrc = mapServeur.get(profile);
		serveurSrc.migration(profile, confServeur.get(serveurDest), mapProfile.get(profile));
		
		int sizeSrc = serveurSize.get(serveurSrc) - mapProfile.get(profile);
		serveurSize.put(serveurSrc, sizeSrc);
		serveurSize.put(serveurDest, (serveurSize.get(serveurDest)+mapProfile.get(profile)));
		mapServeur.put(profile, serveurDest);
		return 0;
	}	

	public static void main(String[] argv){
		System.out.println("Gateway : 49999");
		try {
			IGateway gt = new Gateway();
			Registry registry = LocateRegistry.createRegistry(49999);
			registry.rebind("Gateway", gt);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}