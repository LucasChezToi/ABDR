package Projet;

public class Main {

	public static void main(String[] args){


		try {
			Initializer a = new Initializer("kvstore","localhost","5000");
			a.initE1();
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
			IncrementalThread p1 = new IncrementalThread("E.1");
			//	    	IncrementalThread p2 = new IncrementalThread("E.1");
			p1.start();
			//	        p2.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
