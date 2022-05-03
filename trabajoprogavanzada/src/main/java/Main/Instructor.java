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

    private String id;
    private Entrance entrance1, entrance2;
    private int breakCountdown = 10;

    public Instructor(int startid, Entrance newEntry1, Entrance newEntry2) {
        this.id = "M" + String.format("%04d", startid);
        this.entrance1 = newEntry1;
        this.entrance2 = newEntry2;
    }

    public void run() {
        int coinFlip = (int) (0.5 + Math.random());
        if (coinFlip == 0) {
            if (!entrance1.getOpen()) {
                entrance1.openDoors();
                System.out.println("Door1 open");
            }
            entrance1.enterInstructor(this);
        } else {
            if (!entrance2.getOpen()) {
                entrance2.openDoors();
                System.out.println("Door2 open");
            }
            entrance2.enterInstructor(this);
        }
    }

    public int getBreakCountdown() {
        return breakCountdown;
    }

    public void resetBreakCountdown() {
        this.breakCountdown = 10;
    }
    
    public void lowerBreakCountdown() {
        this.breakCountdown--;
    }
}
