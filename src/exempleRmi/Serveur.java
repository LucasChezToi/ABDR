package exempleRmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class Serveur extends UnicastRemoteObject implements IServeur {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String,String> annuaire;
	private HashMap<String,Personne> annuairePersonne;
	private HashMap<String,IPersonne> annuaireIPersonne;
	private String name;


	public Serveur(String nom) throws RemoteException{
		annuaire = new HashMap<String,String>();
		annuairePersonne = new HashMap<String,Personne>();
		annuaireIPersonne = new HashMap<String,IPersonne>();
		name = nom;
	}

	@Override
	public String getNameServeur() {
		return name;
	}


	/*  EXO 1  */
	@Override
	public void Enregistrer(String nom, String numero) {
		annuaire.put(nom, numero);		
	}


	@Override
	public String getNumero(String nom) {
		return annuaire.get(nom);
	}


	/*  EXO 2  */
	@Override
	public void EnregistrerPersonne(Personne p) {
		annuairePersonne.put(p.getNom(), p);
	}

	@Override
	public String getNumeroPersonne(Personne p) {
		annuairePersonne.get(p.getNom()).incremente();
		return annuairePersonne.get(p.getNom()).getNumero();
	}

	@Override
	public Personne getPersonne(String nom) {
		return annuairePersonne.get(nom);
	}


	/*  EXO 3  */
	@Override
	public void EnregistrerPersonne(IPersonne p) throws RemoteException {
		annuaireIPersonne.put(p.getNom(), p);
	}

	public String getNumeroIPersonne(String nom) throws RemoteException {
		IPersonne p = annuaireIPersonne.get(nom);
		int nb = p.getNb_recherches();
		p.setNb_recherches(nb+1);
		return annuaireIPersonne.get(nom).getNumero();
	}
	public IPersonne getIPersonne(String nom) {
		return annuaireIPersonne.get(nom);
	}

	/*  MAIN  */
	static public void main(String[] args){
		try {
			IServeur serveur = new Serveur("serveur");
			//Registry registry = LocateRegistry.getRegistry();
			Registry registry = LocateRegistry.createRegistry(55551);
			System.out.println("\""+serveur.getNameServeur()+"\"");
			registry.rebind(serveur.getNameServeur(), serveur);
			//UnicastRemoteObject.exportObject(serveur, 0);

			//exo 5 writeObject

		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
