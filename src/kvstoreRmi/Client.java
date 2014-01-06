package kvstoreRmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
	
	
	
	static public void main(String[] args){	
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", 49999);
			try {
				IGateway gt = (IGateway) registry.lookup("Gateway");
				for(int i = 0; i < 1000; i++){
					gt.comit(i);
				}
				
			} catch (NotBoundException e) {
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
}
