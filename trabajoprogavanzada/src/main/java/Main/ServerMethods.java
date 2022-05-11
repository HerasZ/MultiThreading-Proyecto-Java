/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.rmi.RemoteException;

/**
 *
 * @author Heras
 */
public interface ServerMethods {

    public int getZipQueue() throws RemoteException;

    public int getRopeQueue() throws RemoteException;

    public int getSnackQueue() throws RemoteException;
    
    public int getZipUses() throws RemoteException;
    
    public int getDirtyTrays() throws RemoteException;
    
    public int getCleanTrays() throws RemoteException;
    
    public int getActivitiesChild(String ChildID) throws RemoteException;

}
