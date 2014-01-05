package exempleRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPersonne extends Remote {
	public String getNom() throws RemoteException;
	public String getPrenom() throws RemoteException;
	public String getAdresse() throws RemoteException;
	public void setAdresse(String adresse) throws RemoteException;
	public String getNumero() throws RemoteException;
	public int incremente() throws RemoteException;
	public void setNb_recherches(int nb) throws RemoteException;
	public int getNb_recherches() throws RemoteException;
}
