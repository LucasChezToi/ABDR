package TME1;


import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;
import oracle.kv.Key;
import oracle.kv.Value;


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
    void init1() throws Exception {
        System.out.println("Initialisation...");
        int i;
        for(i=1; i<101; i++)
        	store.put(Key.createKey("p"+i), Value.createValue("0".getBytes()));
        System.out.println("Fin d'initialisation");
        store.close();
    }
    

    void init2() throws Exception {
        System.out.println("Initialisation...");
        int i, j;
	    for (j= 1; j<11; j++){
	        for (i = 1; i< 21; i++)
	            store.put(Key.createKey("C"+j, "p"+i*j), Value.createValue("0".getBytes()));
        }
        System.out.println("Fin d'initialisation");


        store.close();
    }
}
