package kvstoreRmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;


public class Serveur extends UnicastRemoteObject implements IServeur {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	static final int MAX_OBJET = 100;

	static final int MAX_ATTRIBUTE = 5;
	private String name;	
	private String arg[];
	private int port;

	/*  MAIN 
	 *
	 * bind le serveur pour l'utilisation de rmi avec les valeurs passées en argument
	 */
	public static void main(String[] argv){
		
		if(argv.length!=5){
			System.out.println("exemple d'appel : Serveur0 55553 kvstore0 Hostname 5000 ");
		}
		System.out.println("serveur : "+argv[0]+" "+argv[1]+" "+argv[2]+" "+argv[3]+" "+argv[4]);
		try {
			IServeur serveur = new Serveur(argv[0],Integer.parseInt(argv[1]),argv[2],argv[3],argv[4]);
			Registry registry = LocateRegistry.createRegistry(serveur.getPort());
			registry.rebind(serveur.getNameServeur(), serveur);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	

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
	/*
	 * (non-Javadoc)
	 * @see kvstoreRmi.IServeur#getMaxObjet()
	 * renvoi le nombre max d'objets par transaction
	 */
	public int getMaxObjet() throws RemoteException{
		return MAX_OBJET;
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see kvstoreRmi.IServeur#getNameServeur()
	 * renvoi le name du serveur
	 */
	public String getNameServeur() throws RemoteException {
		return name;
	}
	


	@Override
	/*
	 * (non-Javadoc)
	 * @see kvstoreRmi.IServeur#getPort()
	 * renvoi le port du serveur
	 */
	public int getPort() throws RemoteException {
		return port;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see kvstoreRmi.IServeur#displayTr(java.lang.String, int)
	 * renvoi un String avec l'affichage des key/value d'un profile
	 */
	public String displayTr(String profile,int nbObjets) throws RemoteException{
		Display affiche = new Display(arg);
		return affiche.aSlave(profile,nbObjets,this.getNameServeur());		
	}
	
	@Override
	/*
	 * (non-Javadoc)
	 * @see kvstoreRmi.IServeur#delete(java.lang.String, int)
	 * appele la fonction delete de la classe Transaction
	 * avec tous les objets du profile
	 */
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
	/*
	 * (non-Javadoc)
	 * @see kvstoreRmi.IServeur#commit(java.lang.String, int)
	 * appele la fonction commit qui lance une transaction
	 * en indiquant quel etait le dernier objet du profile
	 */
	public void commit(String profile,int lastObjectId) throws RemoteException {
		Transaction tr = new Transaction(arg);
		tr.commit(profile, lastObjectId);
	}

	@Override
	/*
	 * (non-Javadoc)
	 * @see kvstoreRmi.IServeur#migration(java.lang.String, java.lang.String[], int)
	 * appele la fonction de migration de la classe Transaction
	 * en chargeant le nouveau Store dans l'appel de fonction.
	 */
	public synchronized void migration(String profile,String[] kvstore, int lastObjetId) throws RemoteException {
		Transaction tr = new Transaction(arg);
		tr.migration(profile, KVStoreFactory.getStore(new KVStoreConfig(kvstore[0], kvstore[1] + ":" + kvstore[2])),lastObjetId);
	}
}
