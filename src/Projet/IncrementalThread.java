package Projet;

public class IncrementalThread extends Thread{
        String version;
        
        IncrementalThread(String version) {
        	this.version = version;
        }

        public void run() {
        	if(version.equals("E.1")){
        		Display etape1 = new Display(null);
        		try {
        			etape1.a(1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
}

