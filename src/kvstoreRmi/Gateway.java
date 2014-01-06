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
	
	private Registry myRegistry[] = new Registry[2];
	private int MAX_SERVEUR = 2;
	private static final Map<IServeur, String[]> confServeur = new HashMap<IServeur, String[]>();
	private static Map<IServeur, Integer> serveurSize = new HashMap<IServeur, Integer>();
	
	public Gateway() throws RemoteException{
		myRegistry[0] = LocateRegistry.getRegistry("132.227.115.102", 55553);
		myRegistry[1] = LocateRegistry.getRegistry("132.227.115.102", 55555);
		System.out.println("constructeur ok");
		
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
			String confKvStore[] = {"kvstore"+profile%MAX_SERVEUR,"Mini-Lenix","500"+((profile%MAX_SERVEUR)*2)};
			confServeur.put(serveurDest, confKvStore);
		}
		serveurDest.commit("profile"+profile, mapProfile.get("profile"+profile));
		serveurSize.put(serveurDest,serveurSize.get(serveurDest)+10);
		mapProfile.put("profile"+profile,mapProfile.get("profile"+profile)+10);		
		
		IServeur migre = needsMigration(serveurDest);
		if(migre != null){
			migrate("profile"+profile,migre);
		}
		System.out.println("test migration ok");
		return 0;
	}
	
	@Override
	public int delete(int profile) throws RemoteException{
		
		return 0;
	}
	
	@Override
	public void display(String profile)throws RemoteException{
		mapServeur.get(profile).displayTr(profile);
	}
	
	private IServeur needsMigration(IServeur serv){
		int nbObjet = serveurSize.get(serv);
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
		mapServeur.get(profile).migration(profile, confServeur.get(serveurDest), mapProfile.get(profile));
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
		
		
//	public void testComit(){
//		try {
//			IServeur serveur1 = (IServeur) myRegistry.lookup("Serveur1");
//			serveur1.commit("profile1",0);
//			serveur1.displayTr("profile1");
//			
//			System.out.println("bob");
//			
//			myRegistry = LocateRegistry.getRegistry("132.227.115.102", 55555);
//			IServeur serveur2 = (IServeur) myRegistry.lookup("Serveur2");
//			serveur2.commit("profile2",0);
//			serveur2.displayTr("profile2");
//		} catch (RemoteException | NotBoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
//	
//	public void testMigration(){
//		Registry myRegistry;
//		try {
//			myRegistry = LocateRegistry.getRegistry("132.227.115.102", 55553);
//			IServeur serveur1 = (IServeur) myRegistry.lookup("Serveur1");
//			serveur1.commit("profile1",0);
//			serveur1.displayTr("profile1");
//			serveur1.migration("profile1", "kvstore2", "132.227.115.102", "5002",10);
//			
//			System.out.println("bob");
//			myRegistry = LocateRegistry.getRegistry("132.227.115.102", 55555);
//			IServeur serveur2 = (IServeur) myRegistry.lookup("Serveur2");
//			serveur2.displayTr("profile1");
//		} catch (RemoteException | NotBoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	}
}
