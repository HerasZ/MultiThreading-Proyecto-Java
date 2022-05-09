/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static java.lang.Thread.sleep;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heras
 */
public class Entrance {

    private LinkedBlockingQueue<Children> entranceQueue = new LinkedBlockingQueue<Children>();
    private CommonArea commonArea;
    private boolean open = false;
    private Semaphore campSemaphore;
    private CountDownLatch closedDoors = new CountDownLatch(1);
    private PrinterLogger UIPrinterLogger;
    private String doorID;

    public Entrance(CommonArea newCommonArea, Semaphore newSemaphore, PrinterLogger UIPrinterLogger, String doorID) {
        this.commonArea = newCommonArea;
        this.campSemaphore = newSemaphore;
        this.UIPrinterLogger = UIPrinterLogger;
        this.doorID = doorID;
    }

    public void enterCamp(Children child) {
        entranceQueue.add(child);
        UIPrinterLogger.setTextTo(entranceQueue.toString(), "entrance" + doorID);
        try {
            while (!open) {
                closedDoors.await();
            }
            Children nextChild = entranceQueue.poll();
            campSemaphore.acquire();
            UIPrinterLogger.setTextTo(entranceQueue.toString(), "entrance" + doorID);
            commonArea.enterChildren(nextChild);
        } catch (Exception e) {
        } finally {
            campSemaphore.release();
        }
    }

    public void enterInstructor(Instructor enteringInstructor) {
        commonArea.enterInstructor(enteringInstructor);
    }

    public void openDoors() {
        try {
            sleep((int) (50 + 50 * Math.random()));
            this.open = true;
            closedDoors.countDown();
        } catch (InterruptedException ex) {
            Logger.getLogger(Entrance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getOpen() {
        return open;
    }
}
