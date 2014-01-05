package etape2;

import java.rmi.Remote;
import java.rmi.RemoteException;

import oracle.kv.KVStore;

public interface IServeurRmi extends Remote {
	public String getNameServeur() throws RemoteException;
	public int migration(String profile,String[] storeDest) throws RemoteException;
	public int commit(String profile, int lastObjectId) throws RemoteException;
}
