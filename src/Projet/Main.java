package Projet;

public class Main {

	static final int MAX_PROFIL = 1000;
	static final int MAX_OBJET = 100;
	static final int MAX_ATTRIBUTE = 5;
	
	public static void main(String[] args){

		/**
		 * Initialisation de la base de donnée
		 */
		long startTime,endTime;
		System.out.println("========== Phase d'Initialisation...");
		Initializer a = new Initializer(null);
		startTime = System.currentTimeMillis();
		a.initE1();
		endTime =  System.currentTimeMillis();
		System.out.println("========== Fin d'initialisation : "+(endTime-startTime)+" ms");

		/**
		 * 
		 */
		IncrementalThread p1 = new IncrementalThread("E.1");
		p1.start();
	}
}
