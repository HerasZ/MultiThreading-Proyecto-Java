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
    private CommonArea commonArea;
    private ReentrantLock snackLock;
    private Semaphore snackCapacity;
    private AtomicInteger cleanTrays = new AtomicInteger();
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
    }

    public void setCommonArea(CommonArea newCommonArea) {
        this.commonArea = newCommonArea;
    }

    public void useSnack(Children newChild) {
        snackQueue.add(newChild);
        try {
            snackCapacity.acquire();
            snackLock.lock();
            while (cleanTrays.get() == 0) {
                pileEmpty.await();
            }

            cleanTrays.getAndDecrement();
            System.out.println(newChild.getIdChild() + " on Snack");
            sleep(7000);
            dirtyTrays.getAndIncrement();
            pileFull.signal();
            snackLock.unlock();
        } catch (InterruptedException e) {
        } finally {
            snackQueue.remove(newChild);
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
                    System.out.println(cleaningInstructor.getIdInst() + " break over");
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
                dirtyTrays.getAndDecrement();
                sleep((int) (3000 + 2000 * Math.random()));
                cleanTrays.getAndIncrement();
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

}
