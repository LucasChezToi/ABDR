package TME1;

public class Main {
	
	public static void main(String[] args){
		
	    try {
	        Initializer a = new Initializer(args);
	        a.init2();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    try {
	    	IncrementalThread p1 = new IncrementalThread("2.3");
	    	IncrementalThread p2 = new IncrementalThread("2.3");
	        p1.start();
	        p2.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
