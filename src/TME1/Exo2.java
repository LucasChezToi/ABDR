package TME1;

import oracle.kv.*;
import oracle.kv.stats.*;

/**
 * TME avec KVStore : Init
 */
public class Exo2 extends StoreConfig{

    /**
     * Parses command line args and opens the KVStore.
     */
	    private Exo2(String[] argv) {
	    	super(argv);
    }
    
    void a1() throws Exception {
        for(int i=0;i<1000;i++){
            String key = "produit1";
            Key k = Key.createKey(key);
            Version matchVersion;
            Version version = null;
            ValueVersion valVer;
            int val2;
            
            
            
            do {
                valVer = store.get(k);
                
                String value = new String(valVer.getValue().getValue());
                matchVersion = valVer.getVersion();
                System.out.println("value = "+value);
                val2 = Integer.parseInt(value);
                val2 ++;
                System.out.println("val2= "+val2);
            
                value = Integer.toString(val2);
            
                version = store.putIfVersion(k, Value.createValue(value.getBytes()), matchVersion);
            }while (version == null);
            
        }
    }

    /**
     * Initialisation
     */
    void go() throws Exception {
        System.out.println("Initialisation...");

	     
        a1();
        store.close();
    }
}
