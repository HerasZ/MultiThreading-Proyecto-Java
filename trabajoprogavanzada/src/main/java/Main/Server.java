package Main;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public void bootServer(BeginCamp campAccess) {
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
