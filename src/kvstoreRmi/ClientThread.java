package kvstoreRmi;


public class ClientThread extends Thread{

	String address;
	String port;
	boolean migrate;

	public ClientThread(String address, String port, boolean migrate) {
		this.address = address;
		this.port = port;
		this.migrate = migrate;
	}

	public void run() {
		Client client = new Client();
		IGateway gateway = client.connectGateway(address, Integer.parseInt(port));
		client.etape1(gateway, 1000, migrate);
		System.out.println(this.getId());
	}
}
