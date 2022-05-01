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
public class Entrance {

    private LinkedBlockingQueue<Children> entranceQueue = new LinkedBlockingQueue<Children>();
    private CommonArea commonArea;
    private boolean open = false;
    //TODO: Replace with atomic integer 
    private int capacity;
    private ReentrantLock campLock;
    private Condition nextKidIn;

    public Entrance(int capacity,CommonArea newCommonArea) {
        this.commonArea = newCommonArea;
        this.capacity = capacity;
        this.campLock = new ReentrantLock(true);
        nextKidIn = campLock.newCondition();
    }

    public void enterQueue(Children child) {
        entranceQueue.add(child);
    }

    public void enterCamp() {
        if (capacity >= 50 || !open) {
            try {
                nextKidIn.wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Entrance.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Children nextChild = entranceQueue.poll();
            commonArea.enterChildren(nextChild);
            capacity++;
            nextKidIn.signal();
        }
    }

    public void openDoors() {
        try {
            sleep((int) (50 + 50 * Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(Entrance.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.open = true;
    }

}
