package kvstoreRmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;

import etape1.Transaction;

public class Serveur extends UnicastRemoteObject implements IServeur {

	static final int MAX_PROFIL = 10;
	static final int MAX_OBJET = 10;
	static final int MAX_ATTRIBUTE = 5;
	private static final int MAX_THREADS = 10;

	private String name;
	private String arg[];
	private int port;



	public Serveur(String name,int port, String storeName,String hostName, String hostPort) throws RemoteException{
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
	public String getNameServeur() {
		return name;
	}
	


	@Override
	public int getPort() {
		return port;
	}

	/*  MAIN  */
	static public void main(String[] argv){
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
	public void displayTr(String profile){
		Display affiche = new Display(arg);
		affiche.a(profile);		
	}

	@Override
	public void commit(String profile,int lastObjectId) throws RemoteException {
		Transaction tr = new Transaction(arg);
		tr.commit(profile, lastObjectId);
	}

	@Override
	public void migration(String profile,String storeDest[]) throws RemoteException {
		Transaction tr = new Transaction(arg);
		tr.migration(profile, new StoreConfig(storeDest).getStore());
	}
}
