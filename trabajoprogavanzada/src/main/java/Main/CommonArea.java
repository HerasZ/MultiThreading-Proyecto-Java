/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static java.lang.Thread.sleep;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heras
 */
public class CommonArea {

    private LinkedBlockingQueue<Children> commonAreaChildren = new LinkedBlockingQueue<Children>();
    private LinkedBlockingQueue<Instructor> commonAreaInstructors = new LinkedBlockingQueue<Instructor>();
    private ReentrantLock instructorBreakLock = new ReentrantLock(true);
    private ZipLine ziplineActivity;
    private Rope ropeActivity;
    private Snack snackActivity;

    public void enterChildren(Children newChild) {
        commonAreaChildren.add(newChild);
        try {
            //Decide on activity
            sleep((int) (2000 + 2000 * Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(CommonArea.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Child common area");
    }

    public void enterInstructor(Instructor newInstructor) {
        commonAreaInstructors.add(newInstructor);
        System.out.println("Instructor common area");
    }

    public void childrenNewActivity(Children actChildren) {
        int coinFlip;
        if (actChildren.getSnackCountdown() <= 0) {
            coinFlip = (int) (0.5 + 2 * Math.random());
        } else {
            coinFlip = (int) (0.5 + Math.random());
        }
        if (coinFlip == 0) {
            //Go to ZipLine

        } else if (coinFlip == 1) {
            //Go to Rope

        } else {
            actChildren.resetSnackCountdown();
            //Go to snacks
        }
    }
    
    public void instructorBreakOver(Instructor onBreak) {
        this.commonAreaInstructors.remove(onBreak);
    }
}
