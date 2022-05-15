package Main;

import static java.lang.Thread.sleep;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Entrance {

    private LinkedBlockingQueue<Children> entranceQueue = new LinkedBlockingQueue<Children>();
    private CommonArea commonArea;
    private boolean open = false;
    private Semaphore campSemaphore;
    private CountDownLatch closedDoors = new CountDownLatch(1);
    private PrinterLogger UIPrinterLogger;
    private String doorID;
    private ReentrantLock doorOpenLock;

    public Entrance(CommonArea newCommonArea, Semaphore newSemaphore, PrinterLogger UIPrinterLogger, String doorID) {
        this.commonArea = newCommonArea;
        this.campSemaphore = newSemaphore;
        this.UIPrinterLogger = UIPrinterLogger;
        this.doorID = doorID;
        this.doorOpenLock = new ReentrantLock();
    }

    public void enterCamp(Children child) {
        entranceQueue.add(child);
        UIPrinterLogger.setTextTo(entranceQueue.toString(), "entrance" + doorID);
        try {
            while (!open) {
                closedDoors.await();
            }
            campSemaphore.acquire();
            Children nextChild = entranceQueue.poll();
            UIPrinterLogger.setTextTo(entranceQueue.toString(), "entrance" + doorID);
            UIPrinterLogger.log(child.toString() + " enters through entrance " + doorID);
            commonArea.enterChildren(nextChild);
            UIPrinterLogger.log(child.toString() + " leaves camp");
        } catch (Exception e) {
        } finally {
            campSemaphore.release();
        }
    }

    public void enterInstructor(Instructor enteringInstructor) {
        doorOpenLock.lock();
        if (!this.open) {
            openDoors();
            UIPrinterLogger.log(enteringInstructor.toString() + " opens entrance " + doorID);
        }
        doorOpenLock.unlock();
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

    public CommonArea getCommonArea() {
        return commonArea;
    }
}
