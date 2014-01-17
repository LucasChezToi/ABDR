package kvstoreRmi;


public class ClientThread {

	String address;
	String port;
	boolean migrate;

	ClientThread(String address, String port, boolean migrate) {
		this.address = address;
		this.port = port;
		this.migrate = migrate;
	}

	public void run() {
		IGateway gateway = Client.connectGateway(address, Integer.parseInt(port));
		Client.etape1(gateway, 1000, migrate);
		
	}
}
