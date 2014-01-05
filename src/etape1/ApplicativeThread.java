package etape1;

public class ApplicativeThread extends Thread{
	String profile;

	ApplicativeThread(String profile) {
		this.profile = profile;
	}

	public void run() {
		Application application = new Application(null);
		try {
			application.run(profile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

