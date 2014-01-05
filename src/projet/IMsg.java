package projet;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMsg extends Remote {
    void printMsg() throws RemoteException;
    public String getAction() throws RemoteException;
    
}
