package kvstoreRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServeur extends Remote {
	public void commit(String profile,int lastObjectId) throws RemoteException;
	public String getNameServeur() throws RemoteException;
	public int getPort() throws RemoteException;
	public String displayTr(String profile,int nbObjets) throws RemoteException;
	public void migration(String profile, String[] kvstore, int lastObjetId) throws RemoteException;
	public void delete(String profile, int nbObject) throws RemoteException;
	int getMaxObjet() throws RemoteException;

	
}
