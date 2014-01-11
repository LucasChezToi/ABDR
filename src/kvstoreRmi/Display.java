package kvstoreRmi;

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

	void a(String profile){
		int i;
		for(i=0; i<Serveur.MAX_PROFIL; i++){
			this.aSlave(profile,10,"bob");
		}
		store.close();
	}

	String aSlave(String profile,int nbObjets,String serv){
		int attribute, object;
		String value;
		ValueVersion valVer;
		List<Key> keys = new ArrayList<Key>();
		List<Version> versions = new ArrayList<Version>();
		List<String> majorPath = new ArrayList<String>();
		String ret="Serveur = "+serv;

		for(object = 0; object < nbObjets; object++){
			/**
			 * Adding Attributes of type int
			 */
			for(attribute = 0; attribute < Serveur.MAX_ATTRIBUTE; attribute++){
				majorPath.clear();
				majorPath.add(profile);
				majorPath.add("Objet"+object);
				Key k = Key.createKey(majorPath,"attrInt"+attribute);
				keys.add(k);
//				System.out.println(k.getFullPath());
				value = new String(store.get(k).getValue().getValue());
				valVer = store.get(keys.get(attribute));
				versions.add(valVer.getVersion());
				
//				System.out.println("Profil1->Objets"+object+"->attrInt" + attribute + " = "+value);
				ret+= "\n"+profile+"->Objets"+object+"->attrInt" + attribute + " = "+value;
			}
			/**
			 * Adding Attribute of type String
			 */
			for(attribute = 0; attribute < Serveur.MAX_ATTRIBUTE; attribute++){
				majorPath.clear();
				majorPath.add(profile);
				majorPath.add("Objet"+object);
				Key k = Key.createKey(majorPath,"attrChar"+attribute);
				keys.add(k);
//				System.out.println(k.getFullPath());
				value = new String(store.get(k).getValue().getValue());
				valVer = store.get(keys.get(attribute));
				versions.add(valVer.getVersion());
				
//				System.out.println("Profil1->Objets"+object+"->attrChar" + attribute + " = "+value);
				ret+= "\n"+profile+"->Objets"+object+"->attrInt" + attribute + " = "+value;
			}
			ret+="\n";
//			System.out.println("");
		}
		return ret;
		
	}
}
