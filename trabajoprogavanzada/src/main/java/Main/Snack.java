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
    private CommonArea commonArea;
    private ReentrantLock snackLock;
    private Semaphore snackCapacity;
    private Instructor snackInstructor1;
    private Instructor snackInstructor2;
    private AtomicInteger cleanTrays;
    private AtomicInteger dirtyTrays;
    private boolean empty;
    private Condition pileEmpty;
    private Condition pileFull;

    public Snack() {
        snackCapacity = new Semaphore(20, true);
        snackLock = new ReentrantLock(true);
        pileEmpty = snackLock.newCondition();
        pileFull = snackLock.newCondition();
        dirtyTrays.set(25);
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
            snackLock.unlock();
            cleanTrays.getAndDecrement();
            System.out.println(newChild.getIdChild() + " on Snack");
            sleep(7000);
            dirtyTrays.getAndIncrement();
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
                    System.out.println(cleaningInstructor.getIdInst() + " taking break");
                    commonArea.instructorBreakBegin(cleaningInstructor);
                    sleep((int) (1000 + 1000 * Math.random()));
                    commonArea.instructorBreakOver(cleaningInstructor);
                    cleaningInstructor.resetBreakCountdown();
                    System.out.println(cleaningInstructor.getIdInst() + " break over");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                cleaningInstructor.lowerBreakCountdown();
            }
        }
    }

    public void setSnackInstructor1(Instructor snackInstructor1) {
        this.snackInstructor1 = snackInstructor1;
        cleanTrays(this.snackInstructor1);
    }

    public void setSnackInstructor2(Instructor snackInstructor2) {
        this.snackInstructor2 = snackInstructor2;
        cleanTrays(this.snackInstructor2);
    }

}
