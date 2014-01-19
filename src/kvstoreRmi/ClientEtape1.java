package kvstoreRmi;


public class ClientEtape1 extends Thread{

	String address;
	String port;
	boolean migrate;
	int startProfil;

	public ClientEtape1(String address, String port,int startProfil, boolean migrate) {
		this.address = address;
		this.port = port;
		this.migrate = migrate;
		this.startProfil = startProfil;
	}

	public void run() {
		Client client = new Client();
		IGateway gateway = client.connectGateway(address, Integer.parseInt(port));
		long mid = client.etape1(gateway, 1000,startProfil,migrate);
		TimeStamp.add(mid);
	}
}
