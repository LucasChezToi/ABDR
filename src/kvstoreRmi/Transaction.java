package kvstoreRmi;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oracle.kv.Direction;
import oracle.kv.KVStore;
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

	/*
	 * ajoute Serveur.MAX_OBJET objets dans le profile a partir de lastObjectId
	 * pour tous les objets appele la fonction createObject qui ajoute les objets avec les attributs
	 */
	public int commit(String profile, int lastObjectId){
		List<Operation> operations = new ArrayList<Operation>();
		int object;
		for (object = lastObjectId; object < Serveur.MAX_OBJET + lastObjectId; object++){
			createObject(profile, object, operations);
		}return 0;
	}
	
	/*
	 * supprime toutes les clef possedant le majorPath
	 */
	public void delete(List<String> majorPath){
		store.multiDelete(Key.createKey(majorPath), null,null );		
	}

	/*
	 * creer un objet pour l'ajouter à un profil
	 * ajoute tous les attributs de l'objet grace a l'OperationFactory
	 */
	private void createObject(String profile, int object, List<Operation> operations){
		List<String> majorPath = new ArrayList<String>();
		OperationFactory factory = store.getOperationFactory();
		Operation operation;
		int attribute;
		operations.clear();			
		for(attribute = 0; attribute < Serveur.MAX_ATTRIBUTE; attribute++){
			majorPath.clear();
			majorPath.add(profile);
			majorPath.add("Objet"+object);
			operation = factory.createPut(Key.createKey(majorPath,"attrInt"+attribute), Value.createValue((""+attribute).getBytes()));
			operations.add(operation);
		}	

		for(attribute = 0; attribute < Serveur.MAX_ATTRIBUTE; attribute++){
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
	
	/*
	 * appele migrateObject sur tous les objets du profile
	 */
	public void migration(String profile,KVStore storeDest, int lastObjectId){
		System.out.println("migration");
		for(int i = 0; i < lastObjectId; i++){
			migrateObject(profile, i, storeDest);
		}		
	}
	/*
	 * ajoute toutes les key/valeurs du profile dans le nouveau Serveur a l'aide d'un OperationFactory
	 */
	private void migrateObject(String profile, int object, KVStore storeDest){
		List<String> majorPath = new ArrayList<String>();
		majorPath.add(profile); majorPath.add("Objet"+object);
		Iterator<KeyValueVersion> profileSrc = store.multiGetIterator(Direction.REVERSE, 1, Key.createKey(majorPath), null, null);
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
			this.migrateObject(profile, object, storeDest);
		}
				
	}
}