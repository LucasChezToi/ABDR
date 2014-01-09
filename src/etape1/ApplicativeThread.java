package etape1;

public class ApplicativeThread extends Thread{
	String profile;
	String[] arg;

	ApplicativeThread(String[] arg, String profile) {
		this.profile = profile;
		this.arg = arg;
	}

	public void run() {
		Application application = new Application(arg);
		try {
			application.run(profile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

