package etape1;

public class MainDualCharge {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long startTime,endTime;
		String[] arga = {"-store","kvstore","-host","localhost","-port","5000"};
		String[] argb = {"-store","kvstore1","-host","localhost","-port","5002"};
		System.out.println("========== Phase d'Initialisation...");
		Initializer a = new Initializer(arga);
		Initializer b = new Initializer(argb);
		startTime = System.currentTimeMillis();
		a.init();	
		b.init();
		endTime = System.currentTimeMillis();
		System.out.println("========== Fin d'initialisation : "+(endTime-startTime)+" ms");
		
		
		for(int i = 0; i < Defines.MAX_THREADS/2; i++){
			ApplicativeThread appThreadA = new ApplicativeThread(arga,"profile"+i);
			ApplicativeThread appThreadB = new ApplicativeThread(argb,"profile"+(i+5));
			appThreadA.start();
			appThreadB.start();
		}
	}

}
