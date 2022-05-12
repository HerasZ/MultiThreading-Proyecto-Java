/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static java.lang.Thread.sleep;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heras
 */
public class BeginCamp extends UnicastRemoteObject implements Runnable, ServerMethods {

    private PrinterLogger UIPrinterLogger;
    private Entrance entranceCampInsight;
    private ArrayList<Children> threads = new ArrayList<Children>();

    public BeginCamp(PrinterLogger ui) throws RemoteException {
        this.UIPrinterLogger = ui;
    }

    public void startCamp() {
        ZipLine zip = new ZipLine(UIPrinterLogger);
        Rope rope = new Rope(UIPrinterLogger);
        Snack snack = new Snack(UIPrinterLogger);
        CommonArea common = new CommonArea(zip, rope, snack, UIPrinterLogger);
        zip.setCommonArea(common);
        rope.setCommonArea(common);
        snack.setCommonArea(common);
        Semaphore campSemaphore = new Semaphore(50, true);
        Entrance entrance1 = new Entrance(common, campSemaphore, UIPrinterLogger, "1");
        Entrance entrance2 = new Entrance(common, campSemaphore, UIPrinterLogger, "2");
        this.entranceCampInsight = entrance1;
        for (int i = 1; i < 5; i++) {
            new Instructor(i, entrance1, entrance2).start();
        }
        for (int i = 1; i < 201; i++) {
            try {
                sleep((int) (1000 + 2000 * Math.random()));
            } catch (InterruptedException ex) {
                Logger.getLogger(BeginCamp.class.getName()).log(Level.SEVERE, null, ex);
            }
            Children newKid = new Children(i, entrance1, entrance2);
            threads.add(newKid);
            newKid.start();
        }
    }

    @Override
    public void run() {
        startCamp();
    }

    @Override
    public int getZipQueue() {
        return entranceCampInsight.getCommonArea().getZiplineActivity().getZipQueue().size();
    }

    public int getRopeQueue() {
        return entranceCampInsight.getCommonArea().getRopeActivity().getRopeQueue().size();
    }

    public int getSnackQueue() {
        return entranceCampInsight.getCommonArea().getSnackActivity().getEatingZone().size();
    }

    @Override
    public int getZipUses() throws RemoteException {
        return entranceCampInsight.getCommonArea().getZiplineActivity().getTimesUsed();
    }

    @Override
    public int getDirtyTrays() throws RemoteException {
        return entranceCampInsight.getCommonArea().getSnackActivity().getDirtyTrays().get();
    }

    @Override
    public int getCleanTrays() throws RemoteException {
        return entranceCampInsight.getCommonArea().getSnackActivity().getCleanTrays().get();
    }

    @Override
    public int getActivitiesChild(String ChildID) throws RemoteException {
        for (int i = 0; i<threads.size();i++) {
            if (ChildID.equals(threads.get(i).getIdChild())) {
                return 15-threads.get(i).getActivitiesLeft();
            }
        }
        return -1;
    }
}
