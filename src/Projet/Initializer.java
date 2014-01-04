package Projet;


import java.util.ArrayList;
import java.util.List;

import oracle.kv.Key;
import oracle.kv.Operation;
import oracle.kv.OperationExecutionException;
import oracle.kv.OperationFactory;
import oracle.kv.Value;
import oracle.kv.ReturnValueVersion.Choice;


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
		List<String> majorPath = new ArrayList<String>();
		List<Operation> operations = new ArrayList<Operation>();
		OperationFactory factory = store.getOperationFactory();
		Operation operation;
		for (pi= 1; pi<100; pi++){

			for (oi = 1; oi< 21; oi++){
				operations.clear();
				for(atti=1; atti<6; atti++){
					majorPath.clear();
					majorPath.add("Profil"+pi);
					majorPath.add("Objets"+oi);
					//minorPath.add("attrInt"+atti);
					operation = factory.createPut(Key.createKey(majorPath,"attrInt"+atti), Value.createValue((""+atti).getBytes()));
					operations.add(operation);
				}	
				
				for(atti=1;atti<6;atti++){
					majorPath.clear();
					majorPath.add("Profil"+pi);
					majorPath.add("Objets"+oi);
					//majorPath.add("attrChar"+atti);
					operation = factory.createPut(Key.createKey(majorPath,"attrChar"+atti), Value.createValue(("char"+atti).getBytes()));
					operations.add(operation);
				}
				try{
					store.execute(operations);
				}catch (OperationExecutionException e){
					initE1();
				}
			}
		}
		System.out.println("Fin d'initialisation");
		store.close();
	}  
}
