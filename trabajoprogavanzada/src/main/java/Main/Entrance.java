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
    private boolean open = false;
    private int capacity;
    private ReentrantLock campLock;
    private Condition nextKidIn;

    public Entrance(int capacity) {
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

            //Need to add method here for the kid to enter common areas
            
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
