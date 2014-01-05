package exempleRmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Personne extends UnicastRemoteObject implements IPersonne {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nom;
	private String prenom;
	private String adresse;
	private int nb_recherches;
	private String numero;
	
	public Personne(String nom, String prenom, String adresse, String numero) throws RemoteException{
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.numero = numero;
		nb_recherches = 0;
	}
	
	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}
	
	public String getAdresse() {
		return adresse;
	}
	
	public void setNb_recherches(int nb) {
		nb_recherches = nb;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getNb_recherches() {
		return nb_recherches;
	}
	
	public String getNumero() {
		return numero;
	}
	
	public int incremente() {
		return nb_recherches++;
	}
	
}
