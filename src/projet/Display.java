package projet;

import java.util.ArrayList;
import java.util.List;
import oracle.kv.Key;
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

	void a(int profil){
		int i;
		for(i=0; i<Main.MAX_PROFIL; i++)
			this.aSlave(profil);
		store.close();
	}

	void aSlave(int profil){
		int attribute, object;
		String value;
		ValueVersion valVer;
		List<Key> keys = new ArrayList<Key>();
		List<Version> versions = new ArrayList<Version>();
		List<String> majorPath = new ArrayList<String>();

		for(object = 0; object < Main.MAX_OBJET; object++){
			/**
			 * Adding Attributes of type int
			 */
			for(attribute = 0; attribute < Main.MAX_ATTRIBUTE; attribute++){
				majorPath.clear();
				majorPath.add("Profil"+profil);
				majorPath.add("Objets"+object);
				Key k = Key.createKey(majorPath,"attrInt"+attribute);
				keys.add(k);
				
				value = new String(store.get(k).getValue().getValue());
				valVer = store.get(keys.get(attribute-1));
				versions.add(valVer.getVersion());
				
				System.out.println("Profil1->Objets"+object+"->attrInt" + attribute + " = "+value);
			}
			/**
			 * Adding Attribute of type String
			 */
			for(attribute=0; attribute<5; attribute++){
				majorPath.clear();
				majorPath.add("Profil1");
				majorPath.add("Objets"+object);
				Key k = Key.createKey(majorPath,"attrChar"+attribute);
				keys.add(k);
				
				value = new String(store.get(k).getValue().getValue());
				valVer = store.get(keys.get(attribute-1));
				versions.add(valVer.getVersion());
				
				System.out.println("Profil1->Objets"+object+"->attrChar" + attribute + " = "+value);
			}
			System.out.println("");
		}
		
	}
}
