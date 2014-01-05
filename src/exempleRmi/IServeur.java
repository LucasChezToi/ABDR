package exempleRmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServeur extends Remote {
	public String getNameServeur() throws RemoteException;
	// exo 1
	void Enregistrer(String nom, String numero) throws RemoteException;
	String getNumero(String nom) throws RemoteException;

	// exo 2
	void EnregistrerPersonne(Personne p) throws RemoteException;
	public String getNumeroPersonne(Personne p) throws RemoteException;
	public Personne getPersonne(String nom) throws RemoteException;

	// exo 3
	void EnregistrerPersonne(IPersonne p) throws RemoteException;
	public String getNumeroIPersonne(String nom) throws RemoteException;
	public IPersonne getIPersonne(String nom) throws RemoteException;
}
