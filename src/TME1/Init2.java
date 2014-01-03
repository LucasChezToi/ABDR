package TME1;


import oracle.kv.*;
import oracle.kv.stats.*;

/**
 * TME avec KVStore : Init
 */
public class Init2{

    private final KVStore store;

    /**
     * Runs Init
     */
    public static void main(String args[]) {
        try {
            Init2 a = new Init2(args);
            a.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses command line args and opens the KVStore.
     */
    private Init2(String[] argv) {

        String storeName = "kvstore";
        String hostName = "localhost";
        String hostPort = "5000";

        final int nArgs = argv.length;
        int argc = 0;

        while (argc < nArgs) {
            final String thisArg = argv[argc++];

            if (thisArg.equals("-store")) {
                if (argc < nArgs) {
                    storeName = argv[argc++];
                } else {
                    usage("-store requires an argument");
                }
            } else if (thisArg.equals("-host")) {
                if (argc < nArgs) {
                    hostName = argv[argc++];
                } else {
                    usage("-host requires an argument");
                }
            } else if (thisArg.equals("-port")) {
                if (argc < nArgs) {
                    hostPort = argv[argc++];
                } else {
                    usage("-port requires an argument");
                }
            } else {
                usage("Unknown argument: " + thisArg);
            }
        }

        store = KVStoreFactory.getStore
            (new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    private void usage(String message) {
        System.out.println("\n" + message + "\n");
        System.out.println("usage: " + getClass().getName());
        System.out.println("\t-store <instance name> (default: kvstore) " +
                           "-host <host name> (default: localhost) " +
                           "-port <port number> (default: 5000)");
        System.exit(1);
    }

    /**
     * Initialisation
     */
    void go() throws Exception {
        System.out.println("Initialisation...");

	    String key = "produit";
	    String value = "0";
	    String cat = "C";
	    for (int j= 1; j<201; j++){
	        for (int i = 1; i< 11; i++){
	        
        	    Key k1 = Key.createKey(cat+i, key+j);

	            String q = "0";
	            
	            store.put(k1, Value.createValue(q.getBytes()));
            }
        }
        System.out.println("Fin d'initialisation");


        store.close();
    }
}
