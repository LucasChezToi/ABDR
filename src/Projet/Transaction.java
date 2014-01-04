package Projet;

import java.util.ArrayList;
import java.util.List;

import oracle.kv.KVStore;
import oracle.kv.Key;
import oracle.kv.Operation;
import oracle.kv.OperationExecutionException;
import oracle.kv.OperationFactory;
import oracle.kv.Value;

public class Transaction {
	KVStore store;
	String profil;
	int nbObjets;

	public Transaction(KVStore store, String profil, int nbObjets) {
		this.store = store;
		this.profil = profil;
		this.nbObjets = nbObjets;
	}
	
	
	public int commit(int lastOi){
		List<String> majorPath = new ArrayList<String>();
		List<Operation> operations = new ArrayList<Operation>();
		OperationFactory factory = store.getOperationFactory();
		Operation operation;
		Key k;
		int atti,oi;
		System.out.println(profil);
		for (oi = lastOi+1; oi < nbObjets+lastOi+1; oi++){
			operations.clear();			
			for(atti=1; atti<6; atti++){
				majorPath.clear();
				majorPath.add(profil);
				majorPath.add("Objets"+oi);
				operation = factory.createPut(Key.createKey(majorPath,"attrInt"+atti), Value.createValue((""+atti).getBytes()));
				operations.add(operation);
			}	
			
			for(atti=1;atti<6;atti++){
				majorPath.clear();
				majorPath.add(profil);
				majorPath.add("Objets"+oi);
				operation = factory.createPut(Key.createKey(majorPath,"attrChar"+atti), Value.createValue(("char"+atti).getBytes()));
				operations.add(operation);
			}
			try{
				store.execute(operations);
			}catch (OperationExecutionException e){
				this.commit(lastOi);
			}
		}
		
		return 0;
	}

}
