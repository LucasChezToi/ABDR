package TME1;

import java.util.ArrayList;
import java.util.List;
import oracle.kv.Key;
import oracle.kv.Operation;
import oracle.kv.OperationExecutionException;
import oracle.kv.OperationFactory;
import oracle.kv.ReturnValueVersion.Choice;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.kv.Version;

/**
 * TME avec KVStore : Init
 */
public class Exo2 extends StoreConfig{

	/**
	 * Parses command line args and opens the KVStore.
	 */
	public Exo2(String[] argv) {
		super(argv);
	}

	private void incrementValue(List<String> profile){
		Version version = null;
		do{
			Key k = Key.createKey(profile);
			ValueVersion valVer = store.get(k);
			Version match = valVer.getVersion();
			String value = new String(valVer.getValue().getValue());
			System.out.println(profile + " = "+value);
			value = Integer.toString(Integer.parseInt(value)+1);
			System.out.println("new value " + profile + " = "+value);
			version = store.putIfVersion(k, Value.createValue(value.getBytes()), match);
		}while(version == null);
	}

	void a1() throws Exception {
		List<String> profile = new ArrayList<String>();
		for(int i=0;i<1000;i++){
			for(int j=1; j<6; j++){
				profile.clear();
				profile.add("C1");
				profile.add("p"+j);
				this.incrementValue(profile);
			}
		}
		store.close();
	}

	void a2() throws Exception {
		int max = 0, valint, i, j;
		String value;
		Key k;
		Version version = null;
		for(i=0;i<1000;i++){
			do{
				for(j=1; j<6; j++){
					k = Key.createKey("C1","p"+j);
					value = new String(store.get(k).getValue().getValue());
					valint = Integer.parseInt(value);
					if(max < valint) max = valint;
				}
				for(j=1; j<6; j++){
					k = Key.createKey("C1","p"+j);
					ValueVersion valVer = store.get(k);
					Version match = valVer.getVersion();
					value = Integer.toString(max+1);
					System.out.println(i+" : new value p" + j + " = "+value);
					version = store.putIfVersion(k, Value.createValue(value.getBytes()), match);
				}
			}while(version == null);
		}
		store.close();
	}

	void a3() throws Exception{
		int i;
		for(i=0; i<1000; i++)
			this.a3Slave();
		store.close();
	}

	void a3Slave() throws Exception {
		int max = 0, valint, j;
		String value;
		ValueVersion valVer;
		Operation operation;
		OperationFactory factory = store.getOperationFactory();
		List<Key> keys = new ArrayList<Key>();
		List<Operation> operations = new ArrayList<Operation>();
		List<Version> versions = new ArrayList<Version>();
		for(j=1; j<6; j++){
			Key k = Key.createKey("C1","p"+j);
			keys.add(k);
			value = new String(store.get(k).getValue().getValue());
			valVer = store.get(keys.get(j-1));
			versions.add(valVer.getVersion());
			valint = Integer.parseInt(value);
			if(max < valint) max = valint;
		}
		max++;
		for(j=1; j<6; j++){
			System.out.println("new value p" + j + " = "+ max);
			operation = factory.createPutIfVersion(keys.get(j-1), Value.createValue(Integer.toString(max).getBytes()), versions.get(j-1), Choice.NONE, true);
			operations.add(operation);
		}
		try{
			store.execute(operations);
		}catch (OperationExecutionException e){
			a3Slave();
		}
	}
}
