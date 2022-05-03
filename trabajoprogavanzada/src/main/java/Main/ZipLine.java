/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static java.lang.Thread.sleep;
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
    private Condition waitTurn;
    private Instructor zipInstructor;
    private boolean onBreak = true;

    public void setCommonArea(CommonArea newCommonArea) {
        zipLock = new ReentrantLock(true);
        waitTurn = zipLock.newCondition();
        this.commonArea = newCommonArea;
    }

    public void enterZipQueue(Children newChild) {
        zipQueue.add(newChild);
        useZipLine();
    }

    public void useZipLine() {
        zipLock.lock();
        try {
            while (onBreak) {
                waitTurn.await();
            }
            Children zipChildren = zipQueue.poll();
            System.out.println(zipChildren.getIdChild()+ " on zipline");
            //Getting ready
            sleep(1000);
            //Jump
            sleep(3000);
            //Get out of activity
            sleep(500);
            commonArea.enterChildren(zipChildren);
        } catch (InterruptedException ex) {
            Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //If instructor has to take break
            if (zipInstructor.getBreakCountdown() <= 0) {
                try {
                    onBreak = true;
                    commonArea.enterInstructor(zipInstructor);
                    sleep((int) (1000 + 1000 * Math.random()));
                    commonArea.instructorBreakOver(zipInstructor);
                    onBreak = false;
                    zipInstructor.resetBreakCountdown();
                    waitTurn.signal();
                } catch (InterruptedException ex) {
                    Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                zipInstructor.lowerBreakCountdown();
            }
            zipLock.unlock();
        }

    }

    public void setZipInstructor(Instructor zipInstructor) {
        zipLock.lock();
        onBreak = false;
        System.out.println("Instructor on zipline");
        this.zipInstructor = zipInstructor;
        waitTurn.signal();
        zipLock.unlock();
    }
}
