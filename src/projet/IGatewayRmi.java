package projet;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGatewayRmi extends Remote {
	public String getNameServeur() throws RemoteException;
	public int migration(String profile) throws RemoteException;
	public int commit(String profile, int lastObjectId) throws RemoteException;
	public int initialize() throws RemoteException;
}
