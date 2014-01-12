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

	private static long peupler(IGateway gt,int nbProfiles){
		long startTime,endTime,total=0;
		startTime = System.currentTimeMillis();
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
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	
	}

	private static long afficher(IGateway gt,int profile){
		long startTime,endTime,total=0;
		startTime = System.currentTimeMillis();
		
		
		
		try {
			System.out.println(gt.display("profile"+profile));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}
	
	private static long affichernbObjets(IGateway gt,int profile){
		long startTime,endTime,total=0;
		startTime = System.currentTimeMillis();
		
		
		
		try {
			System.out.println(gt.displayNbObjets("profile"+profile));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}

	private static long add(IGateway gt, int profile){
		long startTime,endTime,total=0;
		startTime = System.currentTimeMillis();
		
		
		
		try {
			gt.comit(profile);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}

	private static long addMultyCle(IGateway gt, int[] profiles){
		long startTime,endTime,total=0;
		startTime = System.currentTimeMillis();
		
		
		try {
			gt.comitMultiCle(profiles);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}

	private static long delete(IGateway gt, int profile){
		long startTime,endTime,total=0;
		startTime = System.currentTimeMillis();
		
		
		try {
			return (long) gt.delete(profile);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}



	private enum Action {
		etape1, etape2, peupler, afficherProfil, afficherNbObjets, ajouter,ajouterMultiCle, supprimer, fin;
	}

	public static void main(String[] args){	



		BufferedReader myReader = new BufferedReader( new InputStreamReader( System.in) );
		Action action = null;
		int arguments[] = null;
		int valueArg=0;
		long time;
		IGateway gt = connectGateway("132.227.114.37", 49999);

		while(true){
			try {
				System.out.println("Actions possibles : etape1, peupler, afficherProfil, afficherNbObjets, ajouter, ajouterMultiCle, supprimer, fin ");
				arguments = null;
				valueArg=0;
				String listAction[] = myReader.readLine().split(" ");
				if(!listAction[0].equals("")){
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
					time = peupler(gt, valueArg);
					System.out.println("peupler : ok en "+time+" ms");
					break;

				case afficherProfil:

					if(arguments==null){
						System.out.println("afficher : saisir le profile à afficher");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					time = afficher(gt,valueArg);
					System.out.println("affichage fait en en "+time+" ms");
					break;

				case afficherNbObjets:
					if(arguments==null){
						System.out.println("afficher : saisir le profile à afficher");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					time = affichernbObjets(gt,valueArg);
					System.out.println("affichage fait en en "+time+" ms");
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
					time = add(gt,valueArg);
					System.out.println("ajouter : le profile à été augmenté en "+time+" ms");
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

					time = addMultyCle(gt,profiles);
					System.out.println("ajouter : les profiles ont été augmentés en "+time+" ms");
					break;

				case supprimer:

					if(arguments==null){
						System.out.println("supprimer : saisir le profile à supprimer");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					
					time = delete(gt, valueArg);
					if(time ==-1){
						System.out.println("le profile"+valueArg+" n'existe pas");
					}else{
						System.out.println("le profile"+valueArg+" a été supprimé en "+time+" ms");
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
			System.out.println("\n");

		}


		//TODO faire les transactions multiclé !
	}
}
