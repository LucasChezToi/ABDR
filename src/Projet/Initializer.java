package Projet;
/**
 * TME avec KVStore : Init
 */
public class Initializer extends StoreConfig{

	public Initializer(String[] argv) {
		super(argv);
	}
	
	

	public Initializer(String storeName, String hostName, String hostPort) {
		super(storeName,hostName,hostPort);
	}



	/**
	 * Initialisation
	 */
	void initE1() {
		int i;
		Transaction tr;

		for (i = 0; i < Main.MAX_PROFIL; i++){
			tr = new Transaction(null);
			tr.commit("profile"+i, 0);
		}
		store.close();
	}  

}
