package projet;

import java.util.ArrayList;
import java.util.List;

import oracle.kv.KVStore;
import oracle.kv.Key;
import oracle.kv.Operation;
import oracle.kv.OperationExecutionException;
import oracle.kv.OperationFactory;
import oracle.kv.ReturnValueVersion.Choice;
import oracle.kv.Value;
import oracle.kv.ValueVersion;
import oracle.kv.Version;

public class Transaction {

  private final KVStore store;
  
  public Transaction(KVStore store) {
    this.store = store;
  }

  public void commit(List<Key> keyList) {
    OperationFactory factory = store.getOperationFactory();
    List<Operation> operations = new ArrayList<>();
    List<Version> versions = new ArrayList<>();
    Value val = getMax(keyList, versions);
    for (int i = 0; i < 5; i++) {
      Operation operation = factory.createPutIfVersion(keyList.get(i), val, versions.get(i), Choice.NONE, true);
      operations.add(operation);
    }
    try {
      store.execute(operations);
    } catch (OperationExecutionException e) {
      commit(keyList);
    }
  }
  
  private Value getMax(List<Key> keys, List<Version> versions) {
    int max = 0;
    for(int i = 0 ; i < 5 ; i++) {
      ValueVersion valueVersion = store.get(keys.get(i));
      int subMax = Integer.parseInt(new String(valueVersion.getValue().getValue()));
      versions.add(valueVersion.getVersion());
      if(subMax >= max) {
        max = subMax;
      }
    }
    max++;
    return Value.createValue(Integer.toString(max).getBytes());
  }
}
