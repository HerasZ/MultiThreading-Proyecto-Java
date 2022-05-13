/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;
/**
 *
 * @author Heras
 */
public class Instructor extends Thread {

    private String idInst;
    private Entrance entrance1, entrance2;
    private int breakCountdown;

    public Instructor(int startid, Entrance newEntry1, Entrance newEntry2) {
        this.idInst = "M" + Integer.toString(startid);
        this.entrance1 = newEntry1;
        this.entrance2 = newEntry2;
        this.breakCountdown = 10;
    }

    public void run() {

        int coinFlip = (int) (0.5 + Math.random());
        Entrance designatedEntrance;
        if (coinFlip == 0) {
            designatedEntrance = entrance1;
        } else {
            designatedEntrance = entrance2;
        }
        designatedEntrance.enterInstructor(this);

    }

    public String getIdInst() {
        return idInst;
    }

    public int getBreakCountdown() {
        return breakCountdown;
    }

    public void resetBreakCountdown() {
        this.breakCountdown = 10;
    }

    public void lowerBreakCountdown() {
        this.breakCountdown -= 1;
    }

    @Override
    public String toString() {
        return this.idInst;
    }
}
