package projet;

public class ApplicativeThread extends Thread{
	String profile;
	String action;
	int lastObjectId;
	
	
	ApplicativeThread(String profile) {
		this.profile = profile;
	}
	
	ApplicativeThread(String action,String profile,int lastObjectId){
		this.action = action;
		this.profile = profile;
		this.lastObjectId = lastObjectId;
	}
	

	public void run() {
		Application application = new Application(null);
		
		try {
			if(action != null && action.equals("commit")){
				application.commit(profile, lastObjectId);
			}else{
				application.run(profile);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

