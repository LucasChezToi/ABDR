package projet;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MessageImpl extends UnicastRemoteObject implements IMsg {
	
	String action;
	String store[];
	
	    
	
	
	public String getAction(){
		return this.action;
	}



	@Override
	public void printMsg() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
    

    
}
