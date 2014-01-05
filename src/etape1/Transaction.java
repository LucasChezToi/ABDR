package etape1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oracle.kv.Direction;
import oracle.kv.KVStore;
import oracle.kv.KVStoreConfig;
import oracle.kv.Key;
import oracle.kv.KeyValueVersion;
import oracle.kv.Operation;
import oracle.kv.OperationExecutionException;
import oracle.kv.OperationFactory;
import oracle.kv.Value;


public class Transaction extends StoreConfig{

	public Transaction(String[] argv) {
		super(argv);
	}


	public int commit(String profile, int lastObjectId){
		List<Operation> operations = new ArrayList<Operation>();
		int object;
		//faire putifversion
		for (object = lastObjectId; object < Main.MAX_OBJET + lastObjectId; object++){
			createObject(profile, object, operations);
		}return 0;
	}

	private void createObject(String profile, int object, List<Operation> operations){
		List<String> majorPath = new ArrayList<String>();
		OperationFactory factory = store.getOperationFactory();
		Operation operation;
		int attribute;
		operations.clear();			
		for(attribute = 0; attribute < Main.MAX_ATTRIBUTE; attribute++){
			majorPath.clear();
			majorPath.add(profile);
			majorPath.add("Objet"+object);
			operation = factory.createPut(Key.createKey(majorPath,"attrInt"+attribute), Value.createValue((""+attribute).getBytes()));
			operations.add(operation);
		}	

		for(attribute = 0; attribute < Main.MAX_ATTRIBUTE; attribute++){
			majorPath.clear();
			majorPath.add(profile);
			majorPath.add("Objet"+object);
			operation = factory.createPut(Key.createKey(majorPath,"attrChar"+attribute), Value.createValue(("char"+attribute).getBytes()));
			operations.add(operation);
		}
		try{
			store.execute(operations);
		}catch (OperationExecutionException e){
			this.createObject(profile, object, operations);
		}
	}
	
	public void migration(String profile,KVStore storeDest){
		System.out.println("migration");
		Iterator<KeyValueVersion> profileSrc = store.storeIterator(Direction.UNORDERED, 1, Key.createKey(profile), null, null);
		KeyValueVersion tmp;
		OperationFactory factory = storeDest.getOperationFactory();
		List<Operation> operations = new ArrayList<Operation>();
		
		while(profileSrc.hasNext()){
			tmp = (KeyValueVersion) profileSrc.next();
			operations.add(factory.createPut(tmp.getKey(), tmp.getValue()));
		}
		try{
			storeDest.execute(operations);
		}catch (OperationExecutionException e){
			this.migration(profile, storeDest);
		}
				
	}
}