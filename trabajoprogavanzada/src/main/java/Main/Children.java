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
public class Children extends Thread {

    private String idChild;
    private Entrance entrance1, entrance2;
    private int activitiesLeft = 15;
    private int snackCountdown = 3;

    public Children(int startid, Entrance newEntry1, Entrance newEntry2) {
        this.idChild = "N" + String.format("%04d", startid);
        this.entrance1 = newEntry1;
        this.entrance2 = newEntry2;
    }

    @Override
    public void run() {
        int coinFlip = (int) (0.5 + Math.random());
        if (coinFlip == 0) {
            entrance1.enterCamp(this);
        } else {
            entrance2.enterCamp(this);
        }
    }

    public int getSnackCountdown() {
        return snackCountdown;
    }

    public void resetSnackCountdown() {
        this.snackCountdown = 3;
    }

    public void lowerSnackCountdown(int amount) {
        this.snackCountdown -= amount;
        if (this.snackCountdown < 0) {
            this.snackCountdown = 0;
        }
    }

    public int getActivitiesLeft() {
        return activitiesLeft;
    }
    public void lowerActivitiesLeft(int amount) {
        this.activitiesLeft -=amount;
        if (this.activitiesLeft < 0) {
            this.activitiesLeft = 0;
        }
    }

    public String getIdChild() {
        return idChild;
    }
    
    @Override
    public String toString() {
        return this.idChild;
    }
    
}
