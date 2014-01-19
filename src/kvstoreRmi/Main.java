package kvstoreRmi;

import java.util.ArrayList;

public class Main {
	private static int CLIENT = 10;

	public static void chargeStore(String[] args,boolean etape){
		int i, j;
		for(j = 1; j < CLIENT + 1; j++){
			ArrayList<ClientEtape1> clients = new ArrayList<ClientEtape1>();
			for(i = 0; i < j; i++){
				if(etape){
					clients.add(new ClientEtape1(args[0], args[1], i%2, Boolean.getBoolean(args[3])));
				}else{
					clients.add(new ClientEtape1(args[0], args[1], 0, Boolean.getBoolean(args[3])));
				}
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

	


	public static void main(String[] args) {
		if(args.length < 4){
			System.out.println("Error : args : gateway ip + port + action + migration");
		} else {

			Client client = new Client();
			IGateway gateway = client.connectGateway(args[0], Integer.parseInt(args[1]));
			
			client.peupler(gateway, 1000);
			
			if(args[2].equals("etape1a")){
				chargeStore(args,false);
			}else if(args[2].equals("etape1b")){
				chargeStore(args,true);				
			}
		}

	}
}