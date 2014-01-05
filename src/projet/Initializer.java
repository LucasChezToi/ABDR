package projet;
/**
 * TME avec KVStore : Init
 */
public class Initializer extends StoreConfig{

	public Initializer(String[] argv) {
		super(argv);
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
