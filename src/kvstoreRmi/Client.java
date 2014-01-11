package kvstoreRmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {


	private static IGateway connectGateway(String ip,int port){
		Registry registry;
		IGateway gt = null;
		try {
			registry = LocateRegistry.getRegistry(ip, port);
			gt = (IGateway) registry.lookup("Gateway");
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		return gt;
	}

	private static void etape1(IGateway gt,int profile){
		//commit en boucle pendant 10seconde sur un profil particulier
		long startTime,endTime,total=0;
		try {
			while(total < 10000){
				startTime = System.currentTimeMillis();
				gt.comit(profile);
				endTime =  System.currentTimeMillis();
				total += endTime-startTime;
				System.out.println("la transaction à pris "+(endTime-startTime)+" ms");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void main(String[] args){	

		IGateway gt = connectGateway("localhost", 49999);
		try {
			for(int i = 0; i < 1000; i++){
				gt.comit(i);				
				if(i%50==0){
					System.out.println("creation de "+i+" profiles");
				}
			}

			System.out.println("fin des insertions");
			System.out.println(gt.display("profile0"));
			etape1(gt,0);
			System.out.println(gt.display("profile0"));


		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}
}
