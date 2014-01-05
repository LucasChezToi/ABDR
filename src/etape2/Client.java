package etape2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import exempleRmi.Msg;

public class Client {
	public static void main(String[] args) {
		if(args.length==0) {
			System.out.println("ajouter le nom de la machine en parametre");
			System.exit(0);
		}
		String servername=args[0];

		Client c = new Client();	
		c.doTest(servername);
	}

	private void doTest(String servername){
		try {
			// fire to server host on port 1099
			Registry myRegistry = LocateRegistry.getRegistry(servername, 1099);

			// search for myMessage service
			Msg impl = (Msg) myRegistry.lookup("myMessage");




			// call server's method			
			impl.sayHello("edwin");

			System.out.println("Message Sent");
		} catch (Exception e) {
			e.printStackTrace();
		}        
	}
}
