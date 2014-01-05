package etape2;

public class Application extends StoreConfig {

	public Application(String[] argv) {
		super(argv);
		// TODO Auto-generated constructor stub
	}

	public void commit(String profile,int lastObjectId){
		Transaction transaction = new Transaction(null);
		transaction.commit(profile, lastObjectId);
	}

	public void run(String profile){
		Transaction transaction = new Transaction(null);
		int iterator = 0, lastObjectId = Main.MAX_OBJET;
		long startTransaction, endTransaction, average = 0, start = System.currentTimeMillis();
		while(System.currentTimeMillis() < start + 10000){
			//System.out.println(profile+", objet"+lastObjectId);
			startTransaction = System.currentTimeMillis();
			transaction.commit(profile, lastObjectId);
			endTransaction = System.currentTimeMillis();
			average += (endTransaction - startTransaction);
			iterator++;
			lastObjectId += Main.MAX_OBJET;
		}
		average /= iterator;
		System.out.println("Application running "+profile+" ended with an average time of "+average+" ms");
	}
}
