package TME1;

import oracle.kv.*;

/**
 * TME avec KVStore : Exercice1
 */
public class Exo1 extends StoreConfig{

    /**
     * Parses command line args and opens the KVStore.
     */
    public Exo1(String[] argv) {
    	super(argv);
    }
    
    void a1() throws Exception {
        for(int i=0;i<1000;i++){
            Key k = Key.createKey("p1");
            Version matchVersion;
            Version version = null;
            ValueVersion valVer;
            int val2;
            String value;
            do {
                valVer = store.get(k);
                value = new String(valVer.getValue().getValue());
                matchVersion = valVer.getVersion();
                System.out.println("value = "+value);
                val2 = Integer.parseInt(value);
                value = Integer.toString(++val2);
                System.out.println("new value= "+value);
            
                version = store.putIfVersion(k, Value.createValue(value.getBytes()), matchVersion);
            }while (version == null);
        }
    }

    void a2() throws Exception {
        for(int i=0;i<1000;i++){
            Key k = Key.createKey("p1");
            Version matchVersion;
            Version version = null;
            ValueVersion valVer;
            int val2;
            String value;
            do {
                valVer = store.get(k);
                value = new String(valVer.getValue().getValue());
                matchVersion = valVer.getVersion();
                System.out.println("value = "+value);
                val2 = Integer.parseInt(value);
                value = Integer.toString(++val2);
                System.out.println("new value= "+value);
            
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
