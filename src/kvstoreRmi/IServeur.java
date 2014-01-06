package kvstoreRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServeur extends Remote {
	public void commit(String profile,int lastObjectId) throws RemoteException;
	public void migration(String profile,String storeDest[]) throws RemoteException;
	public String getNameServeur() throws RemoteException;
	public int getPort() throws RemoteException;
	public void displayTr(String profile) throws RemoteException;
	
}
