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

    public CommonArea(ZipLine newLine, Rope newRope, Snack newSnack) {
        this.ziplineActivity = newLine;
        this.ropeActivity = newRope;
        this.snackActivity = newSnack;
    }

    public void enterChildren(Children newChild) {
        commonAreaChildren.add(newChild);
        System.out.println(newChild.getIdChild() + " common area");
        try {
            //Decide on activity
            sleep((int) (2000 + 2000 * Math.random()));
        } catch (InterruptedException ex) {
            Logger.getLogger(CommonArea.class.getName()).log(Level.SEVERE, null, ex);
        }

        childrenNewActivity(newChild);
    }

    public void enterInstructor(Instructor newInstructor) {
        if (newInstructor.getIdInst().equals("M1")) {
            ziplineActivity.setZipInstructor(newInstructor);
        }
    }

    public void childrenNewActivity(Children actChildren) {
        int coinFlip = 0;
        /*
        if (actChildren.getSnackCountdown() <= 0) {
            coinFlip = (int) (0.5 + 2 * Math.random());
        } else {
            coinFlip = (int) (0.5 + Math.random());
        }
         */
        if (coinFlip == 0) {
            //Go to ZipLine
            ziplineActivity.enterZipQueue(actChildren);
        } else if (coinFlip == 1) {
            //Go to Rope

        } else {
            actChildren.resetSnackCountdown();
            //Go to snacks
        }
    }
    
    public void instructorBreakBegin(Instructor onBreak) {
        this.commonAreaInstructors.add(onBreak);
    }
    
    public void instructorBreakOver(Instructor onBreak) {
        this.commonAreaInstructors.remove(onBreak);
    }
}
