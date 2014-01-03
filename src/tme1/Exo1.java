package tme1;

import oracle.kv.*;
//import oracle.kv.stats.*;

/**
 * TME avec KVStore : Init
 */
public class Exo1{

    private final KVStore store;

    /**
     * Runs Init
     */
    public static void main(String args[]) {
        try {
            Exo1 a = new Exo1(args);
            a.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses command line args and opens the KVStore.
     */
    private Exo1(String[] argv) {

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
        store = KVStoreFactory.getStore(new KVStoreConfig(storeName, hostName + ":" + hostPort));
    }

    private void usage(String message) {
        System.out.println("\n" + message + "\n");
        System.out.println("usage: " + getClass().getName());
        System.out.println("\t-store <instance name> (default: kvstore) " +
                           "-host <host name> (default: localhost) " +
                           "-port <port number> (default: 5000)");
        System.exit(1);
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
