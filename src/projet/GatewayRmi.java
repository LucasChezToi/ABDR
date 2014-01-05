package projet;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;


public class GatewayRmi  extends UnicastRemoteObject implements IGatewayRmi {

	String nameServeur;
	int MAX_PROFIL;
	int MAX_OBJET;
	int MAX_ATTRIBUTE;
	private  int MAX_THREADS;
	Initializer a;

	public GatewayRmi(String nameServeur, int mAX_PROFIL, int mAX_OBJET,
			int mAX_ATTRIBUTE, int mAX_THREADS) throws RemoteException {
		this.nameServeur = nameServeur;
		MAX_PROFIL = mAX_PROFIL;
		MAX_OBJET = mAX_OBJET;
		MAX_ATTRIBUTE = mAX_ATTRIBUTE;
		MAX_THREADS = mAX_THREADS;
		a = new Initializer(null);
	}
	


	@Override
	public String getNameServeur() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int migration(String profile) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int commit(String profile, int lastObjectId) throws RemoteException {
	
		ApplicativeThread appThread = new ApplicativeThread("commit",profile,lastObjectId);
		appThread.start();
		
		
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int initialize() throws RemoteException {
		a.initE1();
		return 0;
	}
}