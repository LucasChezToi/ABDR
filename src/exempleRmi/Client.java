package exempleRmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Client {
	static public void main(String[] args){
		IPersonne p = null;
		try {
			p = new Personne("Do", "John", "Paris", "08 36 65 65 65");
			//UnicastRemoteObject.exportObject(p, 0);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}
		
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 55551);
			try {
				IServeur serveur = (IServeur) registry.lookup("serveur");
				//ex1
				serveur.Enregistrer("Bob", "01 02 03 04 05");
				serveur.Enregistrer("Alice", "01 01 03 04 05");
				System.out.println("EX 1 - numéro de Bob : "+serveur.getNumero("Bob"));
				
				//ex3
				serveur.EnregistrerPersonne(p);
				System.out.println("EX 3 - numéro de John : "+serveur.getNumeroIPersonne("Do"));
				serveur.getNumeroIPersonne("Do");
				System.out.println("EX 3 - John a été recherché "+serveur.getIPersonne("Do").getNb_recherches()+" fois");
				
				// exo 5 readObject
				
				
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
