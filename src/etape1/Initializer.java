package etape1;
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
	void init() {
		int i;
		Transaction tr;
		for (i = 0; i < Defines.MAX_PROFIL; i++){
			tr = new Transaction(null);
			tr.commit("profile"+i, 0);
		}
		store.close();
	}  

}
