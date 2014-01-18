package kvstoreRmi;

public final class TimeStamp {
	
	private static long count = 0;
	private static int cpt = 0;
	
	public static void add (long mid){
			count += mid;
			cpt++;
	}
	
	public static long get(){
		long total = count/cpt;
		count = 0;
		cpt = 0;
		return total;
	}

}
