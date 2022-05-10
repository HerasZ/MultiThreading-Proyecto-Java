/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Heras
 */
public class Server {

    private BeginCamp campAccess;

    public Server(BeginCamp campAcc) {
        this.campAccess = campAcc;
    }

    public void server() {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            Naming.rebind("//localhost/camp", campAccess);
            System.out.println("Camp has been registered");
        } catch (Exception e) {
            System.out.println(" Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
