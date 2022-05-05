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
import java.util.concurrent.locks.ReentrantLock;
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
    private boolean onBreak = true;

    public Rope() {
        teamsReady = new CyclicBarrier(11);
        gameDone = new CyclicBarrier(11);
        teamLimit = new Semaphore(10, true);

    }

    public void setCommonArea(CommonArea newCommonArea) {
        this.commonArea = newCommonArea;
    }
    
    public void useRope(Children newChild) {
        ropeQueue.add(newChild);
        try {
            //Go into the pre-match status
            teamLimit.acquire();
            int assignedTeam = assignTeam(newChild);
            //Wait until both teams are formed
            teamsReady.await();
            System.out.println(newChild.getIdChild() + " on rope");
            //Rope activity taking place
            sleep(7000);
            if(assignedTeam == this.winningTeam) {
                newChild.lowerSnackCountdown(2);
            } else {
                newChild.lowerSnackCountdown(1);
            }
            gameDone.await();
        } catch (InterruptedException | BrokenBarrierException ex) {
            Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ropeQueue.remove(newChild);
            teamLimit.release();
        }

    }

    public void waitRope() {
        while (true) {
            if (this.ropeInstructor.getBreakCountdown() <= 0) {
                //INSTRUCTOR TAKES HIS BREAK
                try {
                    System.out.println(this.ropeInstructor.getIdInst() + " taking break");
                    commonArea.instructorBreakBegin(ropeInstructor);
                    sleep((int) (1000 + 1000 * Math.random()));
                    commonArea.instructorBreakOver(ropeInstructor);
                    ropeInstructor.resetBreakCountdown();
                    System.out.println(this.ropeInstructor.getIdInst() + " break over");
                } catch (InterruptedException ex) {
                    Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.ropeInstructor.lowerBreakCountdown();
            }
            try {
                //Wait for the teams to form
                teamsReady.await();
                //Choose one team as a winner randomly
                this.winningTeam = (int) (0.5 + Math.random());
                //Clear the teams after the game ends
                gameDone.await();
                this.teamA.clear();
                this.teamB.clear();
            } catch (BrokenBarrierException | InterruptedException ex) {
                Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void setRopeInstructor(Instructor ropeInstructor) {
        System.out.println("Instructor on rope");
        this.onBreak = false;
        this.ropeInstructor = ropeInstructor;
        waitRope();
    }

    public int assignTeam(Children child) {
        if (teamA.size() == 5 || teamB.size() == 5) {
            if (teamB.size() == 5) {
                teamB.add(child);
                return 1;
            } else {
                teamA.add(child);
                return 0;
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

}
