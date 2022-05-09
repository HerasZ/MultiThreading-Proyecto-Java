/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.concurrent.LinkedBlockingQueue;
import javax.swing.JTextArea;

/**
 *
 * @author Heras
 */
public class PrinterLogger {

    private JTextArea ropeInstructor, ropeQueue, snackChildren, snackClean, snackDirty, snackInstructors, snackQueue, teamA,
            teamB, zipFinishing, zipInstructor, zipPrepare, zipProgress, zipQueue;

    public PrinterLogger(JTextArea ropeInstructor, JTextArea ropeQueue, JTextArea snackChildren, JTextArea snackClean, JTextArea snackDirty, JTextArea snackInstructors, JTextArea snackQueue, JTextArea teamA, JTextArea teamB, JTextArea zipFinishing, JTextArea zipInstructor, JTextArea zipPrepare, JTextArea zipProgress, JTextArea zipQueue) {
        this.ropeInstructor = ropeInstructor;
        this.ropeQueue = ropeQueue;
        this.snackChildren = snackChildren;
        this.snackClean = snackClean;
        this.snackDirty = snackDirty;
        this.snackInstructors = snackInstructors;
        this.snackQueue = snackQueue;
        this.teamA = teamA;
        this.teamB = teamB;
        this.zipFinishing = zipFinishing;
        this.zipInstructor = zipInstructor;
        this.zipPrepare = zipPrepare;
        this.zipProgress = zipProgress;
        this.zipQueue = zipQueue;
    }

    public void setTextTo(String newString, String whereToSet) {
        switch (whereToSet) {
            case "ropeInstructor" -> ropeInstructor.setText(newString);
            case "ropeQueue" -> ropeQueue.setText(newString);
            case "snackChildren" -> snackChildren.setText(newString);
            case "snackClean" -> snackClean.setText(newString);
            case "snackDirty" -> snackDirty.setText(newString);
            case "snackInstructors" -> snackInstructors.setText(newString);
            case "snackQueue" -> snackQueue.setText(newString);
            case "teamA" -> teamA.setText(newString);
            case "teamB" -> teamB.setText(newString);
            case "zipFinishing" -> zipFinishing.setText(newString);
            case "zipInstructor" -> zipInstructor.setText(newString);
            case "zipPrepare" -> zipPrepare.setText(newString);
            case "zipProgress" -> zipProgress.setText(newString);
            case "zipQueue" -> zipQueue.setText(newString);
        }
    }
}
