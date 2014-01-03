package TME1;

public class Main {
	
	public void main(String[] args){
		
	    try {
	        Initializer a = new Initializer(args);
	        a.init1();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    try {
            Exo1 exo1 = new Exo1(args);
            exo1.go();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
