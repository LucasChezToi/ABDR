package projet;

public class Main {

	static final int MAX_PROFIL = 10;
	static final int MAX_OBJET = 100;
	static final int MAX_ATTRIBUTE = 5;
	private static final int MAX_THREADS = 10;
	
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
		
		
		for(int i = 0; i < MAX_THREADS; i++){
			ApplicativeThread appThread = new ApplicativeThread("profile"+i);
			appThread.start();
		}
	}
}
