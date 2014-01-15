package kvstoreRmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGateway extends Remote {

	public int comitMultiCle(int profiles[]) throws RemoteException;
	public int delete(int profile) throws RemoteException;
	public String display(int profile) throws RemoteException;
	public String displayNbObjets(int profile) throws RemoteException;
	void setRegistry(int idRegistre, String ip, int port)throws RemoteException;
	public int comit(int profile, boolean migrate) throws RemoteException;

}
