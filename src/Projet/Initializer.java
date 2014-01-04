package Projet;


import java.util.ArrayList;
import java.util.List;

import oracle.kv.Key;
import oracle.kv.Value;
import projet.Transaction;


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
    
    void initE1() throws Exception {
        System.out.println("Initialisation...");
        int i, j;
        List<Key> key = new ArrayList<Key>();
		List<String> majorComponent = new ArrayList<String>();
		
	    for (j= 1; j<11; j++){
	    	
	        for (i = 1; i< 21; i++)
	        	majorComponent.clear();
	        	majorComponent.add("Profil"+j);
	        	majorComponent.add("Objets"+i);
	        
	            store.put(Key.createKey(majorComponent), Value.createValue("0".getBytes()));
        }
        System.out.println("Fin d'initialisation");
        store.close();
    }
    
   
    
}
