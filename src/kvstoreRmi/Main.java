package kvstoreRmi;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		if(args.length < 3){
			System.out.println("Error : args : gateway ip + port + migration");
		}
		else {
			int i;
			ArrayList<ClientThread> threads = new ArrayList<ClientThread>();
			for(i = 0; i < 10; i++){
				threads.add(new ClientThread(args[0], args[1], Boolean.getBoolean(args[2])));
				threads.get(i).run();
			}
		}
	}

}
