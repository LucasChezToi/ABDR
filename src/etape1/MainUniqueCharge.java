package etape1;

public class MainUniqueCharge {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime,endTime;
		String[] arga = {"-store","kvstore","-host","localhost","-port","5000"};
		System.out.println("========== Phase d'Initialisation...");
		Initializer a = new Initializer(arga);
		startTime = System.currentTimeMillis();
		a.init();
		endTime =  System.currentTimeMillis();
		System.out.println("========== Fin d'initialisation : "+(endTime-startTime)+" ms");
		
		
		for(int i = 0; i < Defines.MAX_THREADS; i++){
			ApplicativeThread appThread = new ApplicativeThread(arga, "profile"+i);
			appThread.start();
		}
	}

}
