package projet;

public class Main {
	
	public static void main(String[] args){
		
	    try {
	        Initializer a = new Initializer(args);
	        a.initE1();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
//	    try {
//            Exo1 exo1 = new Exo1(args);
//            exo1.go();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
	}
}
