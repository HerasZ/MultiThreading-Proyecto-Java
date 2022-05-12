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
    private PrinterLogger UIPrinterLogger;

    public CommonArea(ZipLine newLine, Rope newRope, Snack newSnack, PrinterLogger UIPrinterLogger) {
        this.ziplineActivity = newLine;
        this.ropeActivity = newRope;
        this.snackActivity = newSnack;
        this.UIPrinterLogger = UIPrinterLogger;
    }

    public void enterChildren(Children newChild) {
        while (newChild.getActivitiesLeft() != 0) {
            commonAreaChildren.add(newChild);
            UIPrinterLogger.setTextTo(commonAreaChildren.toString(), "commonAreaChildren");
            UIPrinterLogger.log(newChild.toString() + " on the common area");
            try {
                //Decide on activity
                sleep((int) (2000 + 2000 * Math.random()));
            } catch (InterruptedException ex) {
                Logger.getLogger(CommonArea.class.getName()).log(Level.SEVERE, null, ex);
            }
            commonAreaChildren.remove(newChild);
            UIPrinterLogger.setTextTo(commonAreaChildren.toString(), "commonAreaChildren");
            childrenNewActivity(newChild);
        }
    }

    public void enterInstructor(Instructor newInstructor) {
        if (newInstructor.getIdInst().equals("M1")) {
            ziplineActivity.setZipInstructor(newInstructor);
        } else if (newInstructor.getIdInst().equals("M2")) {
            ropeActivity.setRopeInstructor(newInstructor);
        } else {
            snackActivity.addInstructor(newInstructor);
        }

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
            ziplineActivity.useZipLine(actChildren);
        } else if (coinFlip == 1) {
            //Go to Rope
            ropeActivity.useRope(actChildren);
        } else {
            actChildren.resetSnackCountdown();
            //Go to snacks
            snackActivity.useSnack(actChildren);
        }
    }

    public void instructorBreakBegin(Instructor onBreak) {
        this.commonAreaInstructors.add(onBreak);
        UIPrinterLogger.setTextTo(commonAreaInstructors.toString(), "commonAreaInstructor");
    }

    public void instructorBreakOver(Instructor onBreak) {
        this.commonAreaInstructors.remove(onBreak);
        UIPrinterLogger.setTextTo(commonAreaInstructors.toString(), "commonAreaInstructor");
    }

    public ZipLine getZiplineActivity() {
        return ziplineActivity;
    }

    public Rope getRopeActivity() {
        return ropeActivity;
    }

    public Snack getSnackActivity() {
        return snackActivity;
    }

}
