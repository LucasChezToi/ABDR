package Projet;

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
public class Display extends StoreConfig{

	/**
	 * Parses command line args and opens the KVStore.
	 */
	public Display(String[] argv) {
		super(argv);
	}

	void a() throws Exception{
		int i;
		for(i=0; i<1000; i++)
			this.aSlave(0);
		store.close();
	}

	void aSlave(int objeti) throws Exception {
		int max = 0, valint, atti,oi=objeti;
		String value;
		ValueVersion valVer;
		Operation operation;
		OperationFactory factory = store.getOperationFactory();
		List<Key> keys = new ArrayList<Key>();
		List<Operation> operations = new ArrayList<Operation>();
		List<Version> versions = new ArrayList<Version>();
		List<String> majorPath = new ArrayList<String>();

		for(oi=1;oi<21;oi++){
			for(atti=1; atti<6; atti++){
				majorPath.clear();
				majorPath.add("Profil1");
				majorPath.add("Objets"+oi);
				Key k = Key.createKey(majorPath,"attrInt"+atti);
				keys.add(k);
				
				value = new String(store.get(k).getValue().getValue());
				valVer = store.get(keys.get(atti-1));
				versions.add(valVer.getVersion());
				
				System.out.println("Profil1->Objets"+oi+"->attrInt" + atti + " = "+value);
			}
			for(atti=1; atti<6; atti++){
				majorPath.clear();
				majorPath.add("Profil1");
				majorPath.add("Objets"+oi);
				Key k = Key.createKey(majorPath,"attrChar"+atti);
				keys.add(k);
				
				value = new String(store.get(k).getValue().getValue());
				valVer = store.get(keys.get(atti-1));
				versions.add(valVer.getVersion());
				
				System.out.println("Profil1->Objets"+oi+"->attrChar" + atti + " = "+value);
			}
			System.out.println("");
		}
		
	}
}
