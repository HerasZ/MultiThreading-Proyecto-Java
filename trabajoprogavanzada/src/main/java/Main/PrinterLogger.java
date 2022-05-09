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
            teamB, zipFinishing, zipInstructor, zipPrepare, zipProgress, zipQueue, commonAreaChildren, commonAreaInstructor, entrance1, entrance2;

    public PrinterLogger(JTextArea ropeInstructor, JTextArea ropeQueue, JTextArea snackChildren, JTextArea snackClean, JTextArea snackDirty, JTextArea snackInstructors, JTextArea snackQueue, JTextArea teamA, JTextArea teamB, JTextArea zipFinishing, JTextArea zipInstructor, JTextArea zipPrepare, JTextArea zipProgress, JTextArea zipQueue, JTextArea commonAreaChildren, JTextArea commonAreaInstructor, JTextArea entrance1, JTextArea entrance2) {
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
        this.commonAreaChildren = commonAreaChildren;
        this.commonAreaInstructor = commonAreaInstructor;
        this.entrance1 = entrance1;
        this.entrance2 = entrance2;
    }

    public void setTextTo(String newString, String whereToSet) {
        switch (whereToSet) {
            case "ropeInstructor" ->
                ropeInstructor.setText(newString);
            case "ropeQueue" ->
                ropeQueue.setText(newString);
            case "snackChildren" ->
                snackChildren.setText(newString);
            case "snackClean" ->
                snackClean.setText(newString);
            case "snackDirty" ->
                snackDirty.setText(newString);
            case "snackInstructors" ->
                snackInstructors.setText(newString);
            case "snackQueue" ->
                snackQueue.setText(newString);
            case "teamA" ->
                teamA.setText(newString);
            case "teamB" ->
                teamB.setText(newString);
            case "zipFinishing" ->
                zipFinishing.setText(newString);
            case "zipInstructor" ->
                zipInstructor.setText(newString);
            case "zipPrepare" ->
                zipPrepare.setText(newString);
            case "zipProgress" ->
                zipProgress.setText(newString);
            case "zipQueue" ->
                zipQueue.setText(newString);
            case "commonAreaChildren" ->
                commonAreaChildren.setText(newString);
            case "commonAreaInstructor" ->
                commonAreaInstructor.setText(newString);
            case "entrance1" ->
                entrance1.setText(newString);
            case "entrance2" ->
                entrance2.setText(newString);
        }
    }
}
