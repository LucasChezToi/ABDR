package Projet;

import java.sql.Time;

/**
 * TME avec KVStore : Init
 */
public class Initializer extends StoreConfig{

	/**
	 * Parses command line args and opens the KVStore.
	 */
	public Initializer(String[] argv) {
		super(argv);
	}
	
	

	public Initializer(String storeName, String hostName, String hostPort) {
		super(storeName,hostName,hostPort);
	}



	/**
	 * Initialisation
	 */
	void initE1() throws Exception {
		System.out.println("Initialisation...");
		int pi;
		Transaction tr;
		long startTime,endTime;
		
		for (pi= 1; pi<=100; pi++){
			tr = new Transaction(store,"Profil"+pi,100);
			startTime = System.currentTimeMillis();
			tr.commit(0);
			endTime =  System.currentTimeMillis();
			System.out.println("transaction effectuée en "+(endTime-startTime)+" ms");
		}
		
		System.out.println("Fin d'initialisation");
		store.close();
	}  
	
}
