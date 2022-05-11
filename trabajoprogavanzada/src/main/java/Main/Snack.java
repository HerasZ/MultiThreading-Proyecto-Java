/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static java.lang.Thread.sleep;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heras
 */
public class Snack {

    private LinkedBlockingQueue<Children> snackQueue = new LinkedBlockingQueue<Children>();
    private LinkedBlockingQueue<Instructor> instructors = new LinkedBlockingQueue<Instructor>();
    private LinkedBlockingQueue<Children> eatingZone = new LinkedBlockingQueue<Children>();
    private CommonArea commonArea;
    private ReentrantLock snackLock;
    private Semaphore snackCapacity;
    private AtomicInteger cleanTrays = new AtomicInteger(0);
    private AtomicInteger dirtyTrays = new AtomicInteger(25);
    private Condition pileEmpty;
    private Condition pileFull;
    private PrinterLogger UIPrinterLogger;

    public Snack(PrinterLogger UIPrinterLogger) {
        snackCapacity = new Semaphore(20, true);
        snackLock = new ReentrantLock(true);
        pileEmpty = snackLock.newCondition();
        pileFull = snackLock.newCondition();
        this.UIPrinterLogger = UIPrinterLogger;
        UIPrinterLogger.setTextTo(Integer.toString(this.cleanTrays.get()), "snackClean");
        UIPrinterLogger.setTextTo(Integer.toString(this.dirtyTrays.get()), "snackDirty");
    }

    public void setCommonArea(CommonArea newCommonArea) {
        this.commonArea = newCommonArea;
    }

    public void useSnack(Children newChild) {
        snackQueue.add(newChild);
        UIPrinterLogger.setTextTo(this.snackQueue.toString(), "snackQueue");
        try {
            snackCapacity.acquire();
            snackQueue.remove(newChild);
            UIPrinterLogger.setTextTo(this.snackQueue.toString(), "snackQueue");
            eatingZone.add(newChild);
            UIPrinterLogger.setTextTo(this.eatingZone.toString(), "snackChildren");
            snackLock.lock();
            while (cleanTrays.get() == 0) {
                pileEmpty.await();
            }
            snackLock.unlock();
            cleanTrays.getAndDecrement();
            UIPrinterLogger.setTextTo(Integer.toString(this.cleanTrays.get()), "snackClean");
            sleep(7000);
            dirtyTrays.getAndIncrement();
            UIPrinterLogger.setTextTo(Integer.toString(this.dirtyTrays.get()), "snackDirty");
            snackLock.lock();
            pileFull.signal();
            snackLock.unlock();
        } catch (InterruptedException e) {
        } finally {
            eatingZone.remove(newChild);
            UIPrinterLogger.setTextTo(this.eatingZone.toString(), "snackChildren");
            snackCapacity.release();
        }
    }

    public void cleanTrays(Instructor cleaningInstructor) {
        while (true) {
            if (cleaningInstructor.getBreakCountdown() <= 0) {
                //INSTRUCTOR TAKES HIS BREAK
                try {
                    this.instructors.remove(cleaningInstructor);
                    UIPrinterLogger.setTextTo(this.instructors.toString(), "snackInstructors");
                    commonArea.instructorBreakBegin(cleaningInstructor);
                    sleep((int) (1000 + 1000 * Math.random()));
                    commonArea.instructorBreakOver(cleaningInstructor);
                    cleaningInstructor.resetBreakCountdown();
                    this.instructors.add(cleaningInstructor);
                    UIPrinterLogger.setTextTo(this.instructors.toString(), "snackInstructors");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                cleaningInstructor.lowerBreakCountdown();
            }
            try {
                snackLock.lock();
                while (dirtyTrays.get() == 0) {
                    pileFull.await();
                }
                snackLock.unlock();
                dirtyTrays.getAndDecrement();
                UIPrinterLogger.setTextTo(Integer.toString(this.dirtyTrays.get()), "snackDirty");
                sleep((int) (3000 + 2000 * Math.random()));
                cleanTrays.getAndIncrement();
                UIPrinterLogger.setTextTo(Integer.toString(this.cleanTrays.get()), "snackClean");
                snackLock.lock();
                pileEmpty.signal();
                snackLock.unlock();
            } catch (InterruptedException ex) {
                Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void addInstructor(Instructor newInstructor) {
        this.instructors.add(newInstructor);
        UIPrinterLogger.setTextTo(this.instructors.toString(), "snackInstructors");
        cleanTrays(newInstructor);
    }

    public LinkedBlockingQueue<Children> getSnackQueue() {
        return snackQueue;
    }

    public AtomicInteger getCleanTrays() {
        return cleanTrays;
    }

    public AtomicInteger getDirtyTrays() {
        return dirtyTrays;
    }

}
