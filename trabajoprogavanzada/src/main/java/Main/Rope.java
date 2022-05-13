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
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heras
 */
public class Rope {

    private LinkedBlockingQueue<Children> ropeQueue = new LinkedBlockingQueue<Children>();
    private LinkedBlockingQueue<Children> teamA = new LinkedBlockingQueue<Children>();
    private LinkedBlockingQueue<Children> teamB = new LinkedBlockingQueue<Children>();
    // teamA wins -> 0 teamB wins -> 1
    private int winningTeam;
    private CommonArea commonArea;
    private CyclicBarrier teamsReady, gameDone;
    private Instructor ropeInstructor;
    private Semaphore teamLimit;
    private PrinterLogger UIPrinterLogger;

    public Rope(PrinterLogger UIPrinterLogger) {
        teamsReady = new CyclicBarrier(11);
        gameDone = new CyclicBarrier(11);
        teamLimit = new Semaphore(10, true);
        this.UIPrinterLogger = UIPrinterLogger;
    }

    public void setCommonArea(CommonArea newCommonArea) {
        this.commonArea = newCommonArea;
    }

    public void useRope(Children newChild) {
        ropeQueue.add(newChild);
        UIPrinterLogger.setTextTo(this.ropeQueue.toString(), "ropeQueue");
        try {
            //Go into the pre-match status
            teamLimit.acquire();
            int assignedTeam = assignTeam(newChild);
            //Wait until both teams are formed
            teamsReady.await();
            ropeQueue.remove(newChild);

            //Update UI
            UIPrinterLogger.setTextTo(this.ropeQueue.toString(), "ropeQueue");
            UIPrinterLogger.setTextTo(this.teamA.toString(), "teamA");
            UIPrinterLogger.setTextTo(this.teamB.toString(), "teamB");

            //Rope activity taking place
            sleep(7000);    

            if (assignedTeam == this.winningTeam) {
                newChild.lowerSnackCountdown(2);
                newChild.lowerActivitiesLeft(2);
            } else {
                newChild.lowerSnackCountdown(1);
                newChild.lowerActivitiesLeft(1);
            }
            gameDone.await();
            UIPrinterLogger.setTextTo("", "teamA");
            UIPrinterLogger.setTextTo("", "teamB");
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            teamLimit.release();
        }

    }

    public void waitRope() {
        while (true) {
            if (this.ropeInstructor.getBreakCountdown() <= 0) {
                //INSTRUCTOR TAKES HIS BREAK
                try {
                    UIPrinterLogger.setTextTo("", "ropeInstructor");
                    UIPrinterLogger.log(ropeInstructor.toString() + " takes his break");
                    commonArea.instructorBreakBegin(ropeInstructor);
                    sleep((int) (1000 + 1000 * Math.random()));
                    commonArea.instructorBreakOver(ropeInstructor);
                    ropeInstructor.resetBreakCountdown();
                    UIPrinterLogger.setTextTo(ropeInstructor.getIdInst(), "ropeInstructor");
                    UIPrinterLogger.log(ropeInstructor.toString() + "'s break is over");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.ropeInstructor.lowerBreakCountdown();
            }
            try {
                //Wait for the teams to form
                teamsReady.await();

                //Log the teams
                UIPrinterLogger.log(this.teamA.toString() + " are teamA on rope");
                UIPrinterLogger.log(this.teamB.toString() + " are teamB on rope");

                //Choose one team as a winner randomly
                this.winningTeam = (int) (0.5 + Math.random());

                //Clear the teams after the game ends
                gameDone.await();

                //Log the winners of the game
                if (this.winningTeam == 0) {
                    UIPrinterLogger.log(this.teamA.toString() + " win at rope");
                } else {
                    UIPrinterLogger.log(this.teamB.toString() + " win at rope");
                }
                this.teamA.clear();
                this.teamB.clear();
            } catch (BrokenBarrierException | InterruptedException ex) {
                Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void setRopeInstructor(Instructor ropeInstructor) {
        this.ropeInstructor = ropeInstructor;
        UIPrinterLogger.setTextTo(ropeInstructor.getIdInst(), "ropeInstructor");
        waitRope();
    }

    public int assignTeam(Children child) {
        if (teamA.size() == 5 || teamB.size() == 5) {
            if (teamB.size() == 5) {
                teamA.add(child);
                return 0;
            } else {
                teamB.add(child);
                return 1;
            }
        } else {
            int coinFlip = (int) (0.5 + Math.random());
            if (coinFlip == 0) {
                teamA.add(child);
                return 0;
            } else {
                teamB.add(child);
                return 1;
            }
        }

    }

    public LinkedBlockingQueue<Children> getRopeQueue() {
        return ropeQueue;
    }

}
