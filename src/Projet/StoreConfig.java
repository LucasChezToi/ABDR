package Projet;

import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.KVStoreFactory;

public class StoreConfig {
    protected final KVStore store;

    /**
     * Parses command line args and opens the KVStore.
     */
	public StoreConfig(String[] argv){
        String storeName = "kvstore";
        String hostName = "localhost";
        String hostPort = "5000";

        if(argv != null){
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
        }
        store = KVStoreFactory.getStore
            (new KVStoreConfig(storeName, hostName + ":" + hostPort));
	}

//	public StoreConfig(){
//        String storeName = "kvstore";
//        String hostName = "localhost";
//        String hostPort = "5000";
//        store = KVStoreFactory.getStore
//            (new KVStoreConfig(storeName, hostName + ":" + hostPort));
//	}
//	
//	public StoreConfig(String storeName,String hostName,String hostPort){
//        store = KVStoreFactory.getStore
//            (new KVStoreConfig(storeName, hostName + ":" + hostPort));
//	}
	
	private void usage(String message) {
        System.out.println("\n" + message + "\n");
        System.out.println("usage: " + getClass().getName());
        System.out.println("\t-store <instance name> (default: kvstore) " +
                           "-host <host name> (default: localhost) " +
                           "-port <port number> (default: 5000)");
        System.exit(1);
    }
}
