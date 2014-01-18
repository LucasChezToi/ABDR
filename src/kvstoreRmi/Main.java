package kvstoreRmi;

import java.util.ArrayList;


public class Main {
	private static int CLIENT = 10;

	public static void main(String[] args) {
		if(args.length < 3){
			System.out.println("Error : args : gateway ip + port + migration");
		}
		else {
			int i, j;
			Client client = new Client();
			IGateway gateway = client.connectGateway(args[0], Integer.parseInt(args[1]));
			client.peupler(gateway, 1000);
			for(j = 1; j < CLIENT + 1; j++){
				ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
				for(i = 0; i < j; i++){
					clients.add(new ClientThread(args[0], args[1], Boolean.getBoolean(args[2])));
					clients.get(i).start();
				}
				for (i = 0; i < j ; i++){
					try {
						clients.get(i).join();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(j+" "+TimeStamp.get());
			}
		}
	}

}
