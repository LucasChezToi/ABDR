package kvstoreRmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;


public class Serveur extends UnicastRemoteObject implements IServeur {


	static final int MAX_OBJET = 100;

	static final int MAX_ATTRIBUTE = 5;
	private static final int MAX_THREADS = 10;

	private String name;
	private String arg[];
	private int port;



	public Serveur(String name, int port, String storeName, String hostName, String hostPort) throws RemoteException{
		this.name = name;
		this.port = port;
		this.arg = new String[6];
		this.arg[0] = "-store";
		this.arg[1] = storeName;
		this.arg[2] = "-host";
		this.arg[3] = hostName;
		this.arg[4] = "-port";
		this.arg[5] = hostPort;
	}

	@Override
	public int getMaxObjet() throws RemoteException{
		return MAX_OBJET;
	}
	
	@Override
	public String getNameServeur() throws RemoteException {
		return name;
	}
	


	@Override
	public int getPort() throws RemoteException {
		return port;
	}

	/*  MAIN  */
	public static void main(String[] argv){
		//argv1 = name | argv2 = port | argv3 = storename | argv4 = hostname | argv5 = hostport
		System.out.println("serveur : "+argv[0]+" "+argv[1]+" "+argv[2]+" "+argv[3]+" "+argv[4]);
		try {
			IServeur serveur = new Serveur(argv[0],Integer.parseInt(argv[1]),argv[2],argv[3],argv[4]);
			Registry registry = LocateRegistry.createRegistry(serveur.getPort());
			registry.rebind(serveur.getNameServeur(), serveur);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String displayTr(String profile,int nbObjets) throws RemoteException{
		Display affiche = new Display(arg);
		return affiche.aSlave(profile,nbObjets,this.getNameServeur());		
	}
	
	@Override
	public void delete(String profile,int nbObject) throws RemoteException{
		Transaction tr = new Transaction(arg);
		List<String> majorPath = new ArrayList<String>();
		for(int i=0;i<nbObject;i++){
			majorPath.clear();
			majorPath.add(profile);
			majorPath.add("Objet"+i);
			tr.delete(majorPath);
		}
	}

	@Override
	public void commit(String profile,int lastObjectId) throws RemoteException {
		Transaction tr = new Transaction(arg);
		tr.commit(profile, lastObjectId);
//		System.out.println(profile+" a été ajouté à la base");
	}

	@Override
	public void migration(String profile,String[] kvstore, int lastObjetId) throws RemoteException {
		Transaction tr = new Transaction(arg);
		tr.migration(profile, KVStoreFactory.getStore(new KVStoreConfig(kvstore[0], kvstore[1] + ":" + kvstore[2])),lastObjetId);
	}
}
