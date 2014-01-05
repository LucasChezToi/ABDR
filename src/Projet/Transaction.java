package Projet;

import java.util.ArrayList;
import java.util.List;
import oracle.kv.Key;
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
		System.out.println(profile);
		for (object = lastObjectId+1; object < Main.MAX_OBJET + lastObjectId + 1; object++)
			createObject(profile, object, operations);
		return 0;
	}

	private void createObject(String profile, int object, List<Operation> operations){
		OperationFactory factory = store.getOperationFactory();
		Operation operation;
		int attribute;
		operations.clear();			
		for(attribute = 0; attribute < Main.MAX_ATTRIBUTE; attribute++){
			operation = factory.createPut(Key.createKey(profile, "Objet"+object),
					Value.createValue(("attrInt"+attribute).getBytes()));
			operations.add(operation);
			operation = factory.createPut(Key.createKey("Objet"+object, "attrInt"+attribute),
					Value.createValue((""+attribute).getBytes()));
			operations.add(operation);
		}	

		for(attribute = 0; attribute < Main.MAX_ATTRIBUTE;attribute++){
			operation = factory.createPut(Key.createKey(profile, "Objet"+object),
					Value.createValue(("attrString"+attribute).getBytes()));
			operations.add(operation);
			operation = factory.createPut(Key.createKey("Objet"+object, "attrString"+attribute),
					Value.createValue((""+attribute).getBytes()));
			operations.add(operation);
		}
		try{
			store.execute(operations);
		}catch (OperationExecutionException e){
			this.createObject(profile, object, operations);
		}
	}
}



//private void createObject(String profile, int object, List<Operation> operations){
//	List<String> majorPath = new ArrayList<String>();
//	OperationFactory factory = store.getOperationFactory();
//	Operation operation;
//	int attribute;
//	operations.clear();			
//	for(attribute = 0; attribute < Main.MAX_ATTRIBUTE; attribute++){
//		majorPath.clear();
//		majorPath.add(profile);
//		majorPath.add("Objet"+object);
//		operation = factory.createPut(Key.createKey(majorPath,"attrInt"+attribute), Value.createValue((""+attribute).getBytes()));
//		operations.add(operation);
//	}	
//
//	for(attribute=1;attribute<6;attribute++){
//		majorPath.clear();
//		majorPath.add(profile);
//		majorPath.add("Objet"+object);
//		operation = factory.createPut(Key.createKey(majorPath,"attrChar"+attribute), Value.createValue(("char"+attribute).getBytes()));
//		operations.add(operation);
//	}
//	try{
//		store.execute(operations);
//	}catch (OperationExecutionException e){
//		this.createObject(profile, object, operations);
//	}
//}
