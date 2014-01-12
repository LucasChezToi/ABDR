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
	
	private Registry myRegistry[];

	public Gateway(int nbServ) throws RemoteException{
		myRegistry = new Registry[nbServ];
		int port;
		for(int i=0;i<nbServ;i++){
			port = 55550+i*2+3;
//			System.out.println(port);
			myRegistry[i] = LocateRegistry.getRegistry("132.227.114.37", port);
		}
		MAX_SERVEUR = nbServ;

	}
	@Override
	public void setRegistry(int idRegistre,String ip,int port)throws RemoteException{
		try {
			myRegistry[idRegistre] = LocateRegistry.getRegistry(ip,port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
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
		serveurSize.put(serveurDest,serveurSize.get(serveurDest)+serveurDest.getMaxObjet());
		mapProfile.put("profile"+profile,mapProfile.get("profile"+profile)+serveurDest.getMaxObjet());		

		IServeur migre = needsMigration(serveurDest,"profile"+profile);
		if(migre != null){
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
	
	@Override
	public synchronized String displayNbObjets(String profile)throws RemoteException{
		if (mapServeur.get(profile)==null){
			return "le "+profile+" n'existe pas !";
		}
		return "le "+profile+" possede :"+mapProfile.get(profile)+" objets sur :"+mapServeur.get(profile).getNameServeur();
	}
	
	
	
	/*
	 * IServeur needsMigration(IServeur serv);
	 * decide si l'opperation doit faire migrer le profile ou non
	 */
	private IServeur needsMigration(IServeur serv,String profil){
		
		int nbObjet = serveurSize.get(serv);
		int tailleProfile = mapProfile.get(profil);
		if(nbObjet==0){
			return null;
		}
		int min =0;
		
		Iterator<IServeur> iter = serveurSize.keySet().iterator();
		IServeur tmp;
		while(iter.hasNext()){
			tmp = iter.next();
			min = serveurSize.get(tmp);
			
			if( (min <= nbObjet/2) && (min + tailleProfile < nbObjet )){
				return tmp; 
			}
		}
		return null;
	}
	
	

	private int migrate(String profile, IServeur serveurDest) throws RemoteException{
		IServeur serveurSrc = mapServeur.get(profile);
		if(serveurSrc.getNameServeur().equals(serveurDest.getNameServeur())){
			return 0;
		}
		System.out.println("migration de "+profile+" de "+serveurSrc.getNameServeur()+" vers"+serveurDest.getNameServeur() );
		serveurSrc.migration(profile, confServeur.get(serveurDest), mapProfile.get(profile));
		
		int sizeSrc = serveurSize.get(serveurSrc) - mapProfile.get(profile);
		serveurSize.put(serveurSrc, sizeSrc);
		serveurSize.put(serveurDest, (serveurSize.get(serveurDest)+mapProfile.get(profile)));
		mapServeur.put(profile, serveurDest);
		return 0;
	}	

	@Override
	public int comitMultiCle(int[] profiles) throws RemoteException {
		/* Retrouver le store le moins chargé des profiles
		 * Migrer tous les profiles vers lui
		 * executer le operationStore pour tous les profils de son store
		 */
		IServeur serv = mapServeur.get("profile"+profiles[0]);
		IServeur tmp = null;
		
		for(int i=1;i<profiles.length;i++){
			tmp = mapServeur.get("profile"+profiles[i]);
			if(serveurSize.get(serv) > serveurSize.get(tmp)){
				serv = tmp;
			}			
		}//serv est le serveur le moins chargé
		
		for(int i=0;i<profiles.length;i++){
			migrate("profile"+profiles[i], serv);
		}//toutes les données sont sur le meme serv;
		
		for(int i=0;i<profiles.length;i++){
			comit(profiles[i]);
		}
		
		return 0;
	}
	
	public static void main(String[] argv){
		System.out.println("Gateway : 49999");
		try {
			IGateway gt = new Gateway(2); // creation de 4 gateway sur l'addresse locale
			
//			gt.setRegistry(2, "132.227.114.38", 55553); // modification des 2 dernier pour les positionner 
//			gt.setRegistry(3, "132.227.114.38", 55555); // sur une autre machine 
			
			Registry registry = LocateRegistry.createRegistry(49999);
			registry.rebind("Gateway", gt);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

}