package kvstoreRmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	private static void peupler(IGateway gt,int nbProfiles){
		try {
			for(int i = 0; i < nbProfiles; i++){
				gt.comit(i);				
				if(i%50==0){
					System.out.println("creation de "+i+" profiles");
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private static void afficher(IGateway gt,int profile){
		try {
			System.out.println(gt.display("profile"+profile));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private static void add(IGateway gt, int profile){
		try {
			gt.comit(profile);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private static int delete(IGateway gt, int profile){
		try {
			return gt.delete(profile);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return 0;
	}



	private enum Action {
		etape1, etape2, peupler, afficher, ajouter, supprimer, fin;
	}

	public static void main(String[] args){	



		BufferedReader myReader = new BufferedReader( new InputStreamReader( System.in) );
		Action action = null;
		int argument=0;
		IGateway gt = connectGateway("localhost", 49999);

		while(true){
			try {
				System.out.println("Actions possibles : etape1, peupler, afficher, ajouter, supprimer, fin ");
				action = Action.valueOf(myReader.readLine());


				switch (action) {

				case peupler:
					System.out.println("peupler : saisir nombre de profiles à ajouter");
					argument = Integer.parseInt(myReader.readLine());
					peupler(gt, argument);
					break;

				case afficher:
					System.out.println("afficher : saisir le profile à afficher");
					argument = Integer.parseInt(myReader.readLine());
					afficher(gt,argument);
					break;

				case etape1:
					System.out.println("etape1 : saisir le profile à surcharger");
					argument = Integer.parseInt(myReader.readLine());
					etape1(gt,argument);
					break;

				case ajouter:
					System.out.println("ajouter : saisir le profile à augmenter");
					argument = Integer.parseInt(myReader.readLine());
					add(gt,argument);
					break;

				case supprimer:
					System.out.println("supprimer : saisir le profile à supprimer");
					argument = Integer.parseInt(myReader.readLine());
					if(delete(gt, argument)==-1){
						System.out.println("le profile"+argument+" n'existe pas");
					}else{
						System.out.println("le profile"+argument+" a été supprimé");
					}
					break;

				case fin:
					System.out.println("fin de la simulation");
					return;

				default:
					
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}


		//TODO faire les transactions multiclé !
	}
}
