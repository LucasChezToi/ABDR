package kvstoreRmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	/*
	 * Connecte le client au Gateway
	 */
	public IGateway connectGateway(String ip,int port){
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
	
	/*
	 * execute en boucle des transactions pendant 10sec
	 * sur un profile particulier
	 */

	public long etape1(IGateway gt,int nbProfils,int startProfil, boolean migrate){
		long startTime,endTime = 0,total=0;
		int t=0;
		try {
			while(total < 10000){
				startTime = System.currentTimeMillis();
				gt.comit((t*2)%nbProfils, migrate);
				endTime = System.currentTimeMillis();
				total += endTime - startTime;
				t++;
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(t == 0) t=1;
		return (total/t);
	}

	/*
	 * peuple la base de maniere homogene
	 */
	public long peupler(IGateway gt,int nbProfiles){
		long startTime,endTime;
		startTime = System.currentTimeMillis();
		try {
			for(int i = 0; i < nbProfiles; i++){
				gt.comit(i,false);				
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

	/*
	 * affiche tous les objets/attributs d'un profile
	 */
	private long afficher(IGateway gt,int profile){
		long startTime,endTime;
		startTime = System.currentTimeMillis();
		try {
			System.out.println(gt.display(profile));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}

	/*
	 * affiche le nombre d'objets d'un profile
	 */
	private long affichernbObjets(IGateway gt,int profile){
		long startTime,endTime;
		startTime = System.currentTimeMillis();
		try {
			System.out.println(gt.displayNbObjets(profile));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}

	/*
	 * fait une transaction sur le profile
	 */
	private long add(IGateway gt, int profile){
		long startTime,endTime;
		startTime = System.currentTimeMillis();
		try {
			gt.comit(profile,true);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}

	/*
	 * fait une transaction sur plusieurs profiles
	 */
	public long addMultyCle(IGateway gt, int[] profiles){
		long startTime,endTime;
		startTime = System.currentTimeMillis();
		try {
			gt.comitMultiCle(profiles);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}

	/*
	 * supprime un profile et tous ses objets
	 */
	private long delete(IGateway gt, int profile){
		long startTime,endTime;
		startTime = System.currentTimeMillis();

		try {
			return (long) gt.delete(profile);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		endTime =  System.currentTimeMillis();
		return endTime-startTime;
	}

	/*
	 * produit en boucle des transaction sur une period de temps time
	 * et sur un nombre de profiles nbProfils
	 */
	private long producteur(int time,IGateway gt,int nbProfils) {
		//commit en boucle pendant 10seconde sur un profil particulier
		long startTime,endTime,total=0;
		int t=0;
		try {
			while(total < time){
				startTime = System.currentTimeMillis();
				for(int i=0;i<nbProfils;i++){
					gt.comit(i,false);
				}
				endTime =  System.currentTimeMillis();
				total += endTime-startTime;
				System.out.println("la production de "+nbProfils+" profils à pris "+(endTime-startTime)+" ms");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (total/t);
	}
	
	/*
	 * consomme en boucle des profiles sur une period de temps time
	 * et sur un nombre de profiles nbProfils
	 */
	private long consommateur(int time,IGateway gt,int nbProfils) {
		//commit en boucle pendant 10seconde sur un profil particulier
		long startTime,endTime,total=0;
		int t=0;
		try {
			while(total < time){
				t++;
				startTime = System.currentTimeMillis();
				for(int i=0;i<nbProfils;i++){
					gt.delete(i);
				}
				endTime =  System.currentTimeMillis();
				total += endTime-startTime;
				System.out.println("la consomation de "+nbProfils+" profils à pris "+(endTime-startTime)+" ms");
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (total/t);
	}

	private enum Action {
		etape1, peupler, afficherProfil, afficherNbObjets, ajouter,ajouterMultiCle, supprimer, producteur, consomateur, fin;
	}

	public static void main(String[] argv){	
		if(argv.length!=2){
			System.out.println("exemple d'appel : ipGateway portGateway");
			System.out.println("exemple d'appel : 192.168.1.31 49999");
			return;
		}
		System.out.println("Client : ipGateway="+argv[0]+" portGateway="+argv[1]);
		Client client = new Client();
		BufferedReader myReader = new BufferedReader( new InputStreamReader( System.in) );
		Action action = null;
		int arguments[] = null;
		int valueArg=0;
		long time;
		IGateway gt = client.connectGateway(argv[0], Integer.parseInt(argv[1]));

		while(true){
			try {
				System.out.println("Actions possibles : etape1, peupler, afficherProfil, afficherNbObjets, ajouter,ajouterMultiCle, supprimer, producteur, consomateur, fin");
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
						if(!listAction[i].equals("")){
							arguments[(i-1)] = Integer.parseInt(listAction[i]);
						}
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
					time = client.peupler(gt, valueArg);
					System.out.println("peupler : ok en "+time+" ms");
					break;

				case afficherProfil:
					if(arguments==null){
						System.out.println("afficher : saisir le profile à afficher");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					time = client.afficher(gt,valueArg);
					System.out.println("affichage fait en en "+time+" ms");
					break;

				case producteur:
					time = client.producteur(60000,gt,200);
					System.out.println("le producteur à tourné "+time+" ms");
					break;

				case consomateur:
					time = client.consommateur(60000,gt,200);
					System.out.println("le consomateur à tourné "+time+" ms");
					break;

				case afficherNbObjets:
					if(arguments==null){
						System.out.println("afficher : saisir le profile à afficher");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					time = client.affichernbObjets(gt,valueArg);
					System.out.println("affichage fait en en "+time+" ms");
					break;


				case etape1:
					int fprofil;
					if(arguments.length<2){
						System.out.println("etape1 : saisir le nombre de profils des KVStore");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
						System.out.println("etape1 : saisir le 1er profils à surcharger");
						fprofil= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
						fprofil = arguments[1];						
					}
					client.etape1(gt,valueArg,fprofil, false);
					System.out.println("etape1 : ok");
					break;

				case ajouter:

					if(arguments==null){
						System.out.println("ajouter : saisir le profile à augmenter");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}
					time = client.add(gt,valueArg);
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

					time = client.addMultyCle(gt,profiles);
					System.out.println("ajouter : les profiles ont été augmentés en "+time+" ms");
					break;

				case supprimer:

					if(arguments==null){
						System.out.println("supprimer : saisir le profile à supprimer");
						valueArg= Integer.parseInt(myReader.readLine().split(" ")[0]);
					}else{
						valueArg = arguments[0];
					}

					time = client.delete(gt, valueArg);
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
	}
}
