package kvstoreRmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGateway extends Remote {

	public int comit(int profile) throws RemoteException;
	public int comitMultiCle(int profiles[]) throws RemoteException;
	public int delete(int profile) throws RemoteException;
	public String display(String profile) throws RemoteException;
	public String displayNbObjets(String profile) throws RemoteException;
	void setRegistry(int idRegistre, String ip, int port)throws RemoteException;

}
