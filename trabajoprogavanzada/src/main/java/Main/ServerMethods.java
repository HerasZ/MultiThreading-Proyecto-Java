package Main;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerMethods extends Remote {

    public int getZipQueue() throws RemoteException;

    public int getRopeQueue() throws RemoteException;

    public int getSnackQueue() throws RemoteException;
    
    public int getZipUses() throws RemoteException;
    
    public int getDirtyTrays() throws RemoteException;
    
    public int getCleanTrays() throws RemoteException;
    
    public int getActivitiesChild(String ChildID) throws RemoteException;

}
