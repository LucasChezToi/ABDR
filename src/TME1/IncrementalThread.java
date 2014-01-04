package TME1;

public class IncrementalThread extends Thread{
        String version;
        
        IncrementalThread(String version) {
        	this.version = version;
        }

        public void run() {
        	if(version.equals("1.1")){
        		Exo1 exo1 = new Exo1(null);
        		try {
					exo1.a1();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	if(version.equals("1.2")){
        		Exo1 exo1 = new Exo1(null);
        		try {
					exo1.a2();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	if(version.equals("2.1")){
        		Exo2 exo2 = new Exo2(null);
        		try {
					exo2.a1();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	if(version.equals("2.2")){
        		Exo2 exo2 = new Exo2(null);
        		try {
					exo2.a2();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        	if(version.equals("2.3")){
        		Exo2 exo2 = new Exo2(null);
        		try {
					exo2.a3();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
}

