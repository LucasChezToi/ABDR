package Projet;


import java.util.ArrayList;
import java.util.List;

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

	void initE1() throws Exception {
		System.out.println("Initialisation...");
		int oi, pi,atti;
		List<Key> key = new ArrayList<Key>();
		List<String> minorPath = new ArrayList<String>();

		for (pi= 1; pi<100; pi++){

			for (oi = 1; oi< 21; oi++){

				for(atti=1;atti<6;atti++){
					minorPath.clear();
					minorPath.add("Objets"+oi);
					minorPath.add("attrInt"+atti);
					store.put(Key.createKey("Profil"+pi,minorPath), Value.createValue("0".getBytes()));
				}
				for(atti=1;atti<6;atti++){
					minorPath.clear();
					minorPath.add("Objets"+oi);
					minorPath.add("attrChar"+atti);
					store.put(Key.createKey("Profil"+pi,minorPath), Value.createValue("texte0".getBytes()));
				}
			}
		}
		System.out.println("Fin d'initialisation");
		store.close();
	}  
}
