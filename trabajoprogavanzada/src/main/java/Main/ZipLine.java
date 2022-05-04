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
    private Condition waitForInstructor;
    private CyclicBarrier ChildrenDone;
    private Instructor zipInstructor;
    private boolean onBreak = true;

    public ZipLine() {
        zipLock = new ReentrantLock(true);
        waitForInstructor = zipLock.newCondition();
        ChildrenDone = new CyclicBarrier(2);
    }

    public void setCommonArea(CommonArea newCommonArea) {
        this.commonArea = newCommonArea;
    }

    public void enterZipQueue(Children newChild) {
        zipQueue.add(newChild);
        useZipLine();
    }

    public void useZipLine() {
        zipLock.lock();
        Children zipChildren = zipQueue.poll();
        try {
            while (onBreak) {
                System.out.println("kid waiting");
                waitForInstructor.await();
            }
            System.out.println(zipChildren.getIdChild() + " on zipline");
            //Getting ready
            sleep(1000);
            //Jump
            sleep(3000);
            //Get out of activity
            sleep(500);
            //Let the Instructor know we are done
            ChildrenDone.await();
            //
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            zipLock.unlock();
            commonArea.enterChildren(zipChildren);
        }

    }

    public void waitZipLine() {
        while (true) {
            try {
                //Wait until children is done
                ChildrenDone.await();
                if (this.zipInstructor.getBreakCountdown() <= 0) {
                    zipLock.lock();
                    this.onBreak = true;
                    commonArea.instructorBreakBegin(zipInstructor);
                    sleep((int) (1000 + 1000 * Math.random()));
                    commonArea.instructorBreakOver(zipInstructor);
                    zipInstructor.resetBreakCountdown();
                    this.onBreak = false;
                    waitForInstructor.signal();
                    zipLock.unlock();
                } else {
                    this.zipInstructor.lowerBreakCountdown();
                }
            } catch (InterruptedException | BrokenBarrierException ex) {
                Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setZipInstructor(Instructor zipInstructor) {
        System.out.println("Instructor on zipline");
        this.onBreak = false;
        this.zipInstructor = zipInstructor;
        waitZipLine();
    }
}
