package kvstoreRmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;

public class Gateway {
	
	public static Map<Integer, IServeur> mapServeur;

		
	static public void main(String[] argv){
		// argv
		
		Registry myRegistry;
		try {
			myRegistry = LocateRegistry.getRegistry("132.227.115.102", 55553);
			IServeur serveur1 = (IServeur) myRegistry.lookup("Serveur1");
			serveur1.commit("profile1",0);
			serveur1.displayTr("profile1");
			
			System.out.println("bob");
			
			myRegistry = LocateRegistry.getRegistry("132.227.115.102", 55555);
			IServeur serveur2 = (IServeur) myRegistry.lookup("Serveur2");
			serveur2.commit("profile2",0);
			serveur2.displayTr("profile2");
			
		} catch (RemoteException | NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// search for myMessage service


		
		

	
	}
}
