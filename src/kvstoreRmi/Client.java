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
	
	private static void affichernbObjets(IGateway gt,int profile){
		try {
			System.out.println(gt.displayNbObjets("profile"+profile));
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

	private static void addMultyCle(IGateway gt, int[] profiles){
		try {
			gt.comitMultiCle(profiles);
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
		etape1, etape2, peupler, afficherProfil, afficherNbObjets, ajouter,ajouterMultiCle, supprimer, fin;
	}

	public static void main(String[] args){	



		BufferedReader myReader = new BufferedReader( new InputStreamReader( System.in) );
		Action action = null;
		int arguments[] = null;
		int valueArg=0;
		IGateway gt = connectGateway("localhost", 49999);

		while(true){
			try {
				System.out.println("Actions possibles : etape1, peupler, afficherProfil, afficherNbObjets, ajouter, ajouterMultiCle, supprimer, fin ");
				arguments = null;
				valueArg=0;
				String listAction[] = myReader.readLine().split(" ");
				if(listAction[0]!=null){
					action = Action.valueOf(listAction[0]);
				}else{
					System.out.println("veuillez saisir une action à effectuer");
					continue;
				}

				if(listAction.length>1){
//					System.out.println("arguments ok");
					arguments = new int[(listAction.length-1)];
					for(int i=1; i < listAction.length; i++){
						arguments[(i-1)] = Integer.parseInt(listAction[i]);
					}
				}

				switch (action) {

				case peupler:
					if(arguments==null){
						System.out.println("peupler : saisir nombre de profiles à ajouter");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					peupler(gt, valueArg);
					System.out.println("peupler : ok");
					break;

				case afficherProfil:

					if(arguments==null){
						System.out.println("afficher : saisir le profile à afficher");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					afficher(gt,valueArg);
					break;

				case afficherNbObjets:
					if(arguments==null){
						System.out.println("afficher : saisir le profile à afficher");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					affichernbObjets(gt,valueArg);
					break;
					
					
				case etape1:

					if(arguments==null){
						System.out.println("etape1 : saisir le profile à surcharger");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					etape1(gt,valueArg);
					System.out.println("etape1 : ok");
					break;

				case ajouter:

					if(arguments==null){
						System.out.println("ajouter : saisir le profile à augmenter");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					add(gt,valueArg);
					System.out.println("ajouter : le profile à été augmenté");
					break;

				case ajouterMultiCle:
					
					int profiles[];
					if(arguments==null){
						System.out.println("ajouter : saisir les profiles à augmenter");
						String listProfiles[] = myReader.readLine().split(" ");
						profiles = new int[listProfiles.length];
						for(int j=0;j<listProfiles.length;j++){
							profiles[j] = Integer.parseInt(listProfiles[j]);
						}
					}else{
						profiles = arguments;
						
					}

					addMultyCle(gt,profiles);
					System.out.println("ajouter : les profiles ont été augmentés");
					break;

				case supprimer:

					if(arguments==null){
						System.out.println("supprimer : saisir le profile à supprimer");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}

					if(delete(gt, valueArg)==-1){
						System.out.println("le profile"+valueArg+" n'existe pas");
					}else{
						System.out.println("le profile"+valueArg+" a été supprimé");
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
