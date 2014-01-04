package Projet;

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

	/**
	 * Initialisation
	 */
	void initE1() throws Exception {
		System.out.println("Initialisation...");
		int pi;
		Transaction tr;
		
		for (pi= 1; pi<100; pi++){
			tr = new Transaction(store,"Profil"+pi,100);
			tr.commit();
		}
		
		System.out.println("Fin d'initialisation");
		store.close();
	}  
	
}
