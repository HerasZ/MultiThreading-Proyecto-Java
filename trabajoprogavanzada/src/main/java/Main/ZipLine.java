/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static java.lang.Thread.sleep;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heras
 */
public class ZipLine {

    private LinkedBlockingQueue<Children> zipQueue = new LinkedBlockingQueue<Children>();
    private CommonArea commonArea;
    private ReentrantLock zipLock;
    private CyclicBarrier childrenReady;
    private CyclicBarrier childrenDone;
    private Instructor zipInstructor;
    private PrinterLogger UIPrinterLogger;

    public ZipLine(PrinterLogger UIPrinterLogger) {
        zipLock = new ReentrantLock(true);
        childrenDone = new CyclicBarrier(2);
        childrenReady = new CyclicBarrier(2);
        this.UIPrinterLogger = UIPrinterLogger;

    }

    public void setCommonArea(CommonArea newCommonArea) {
        this.commonArea = newCommonArea;
    }

    public void useZipLine(Children newChild) {
        zipQueue.add(newChild);
        UIPrinterLogger.setTextTo(this.zipQueue.toString(), "zipQueue");
        zipLock.lock();
        zipQueue.remove(newChild);
        try {
            UIPrinterLogger.setTextTo(this.zipQueue.toString(), "zipQueue");
            childrenReady.await();
            System.out.println(newChild.getIdChild() + " on zipline");
            //Getting ready
            UIPrinterLogger.setTextTo(newChild.getIdChild(), "zipPrepare");
            sleep(1000);
            //Jump
            UIPrinterLogger.setTextTo("", "zipPrepare");
            UIPrinterLogger.setTextTo(newChild.getIdChild(), "zipProgress");
            sleep(3000);
            //Get out of activity
            UIPrinterLogger.setTextTo("", "zipProgress");
            UIPrinterLogger.setTextTo(newChild.getIdChild(), "zipFinishing");
            sleep(500);
            UIPrinterLogger.setTextTo("", "zipFinishing");
            //Let the Instructor know we are done
            childrenDone.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            zipLock.unlock();
            newChild.lowerActivitiesLeft();
        }

    }

    public void waitZipLine() {
        while (true) {
            if (this.zipInstructor.getBreakCountdown() <= 0) {
                //INSTRUCTOR TAKES HIS BREAK
                try {
                    UIPrinterLogger.setTextTo("", "zipInstructor");
                    System.out.println(this.zipInstructor.getIdInst() + " taking break");
                    commonArea.instructorBreakBegin(zipInstructor);
                    sleep((int) (1000 + 1000 * Math.random()));
                    commonArea.instructorBreakOver(zipInstructor);
                    zipInstructor.resetBreakCountdown();
                    System.out.println(this.zipInstructor.getIdInst() + " break over");
                    UIPrinterLogger.setTextTo(this.zipInstructor.getIdInst(), "zipInstructor");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.zipInstructor.lowerBreakCountdown();
            }
            try {
                //Wait for a children to be ready
                childrenReady.await();
                //Wait until children is done
                childrenDone.await();
            } catch (BrokenBarrierException | InterruptedException ex) {
                Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void setZipInstructor(Instructor zipInstructor) {
        System.out.println("Instructor on zipline");
        this.zipInstructor = zipInstructor;
        UIPrinterLogger.setTextTo(this.zipInstructor.getIdInst(), "zipInstructor");
        waitZipLine();
    }
}
