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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
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

    public Entrance(CommonArea newCommonArea) {
        this.commonArea = newCommonArea;

        this.campSemaphore = new Semaphore(50, true);
    }

    public void enterQueue(Children child) {
        entranceQueue.add(child);
        enterCamp();
    }

    public void enterCamp() {
        try {
            while (!open) {
                closedDoors.await();
            }
            campSemaphore.acquire();
            Children nextChild = entranceQueue.poll();
            commonArea.enterChildren(nextChild);
            campSemaphore.release();
        } catch (Exception e) {
        } finally {

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
