package kvstoreRmi;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGateway extends Remote {

	public int comit(int profile) throws RemoteException;
	public int delete(int profile) throws RemoteException;
	public void display(String profile) throws RemoteException;

}
