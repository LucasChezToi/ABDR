package Projet;

public class IncrementalThread extends Thread{
        String version;
        
        IncrementalThread(String version) {
        	this.version = version;
        }

        public void run() {
        	if(version.equals("E.1")){
        		Etape1 etape1 = new Etape1(null);
        		try {
        			etape1.a3();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
}

