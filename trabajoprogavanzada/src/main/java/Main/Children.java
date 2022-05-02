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

    private String id;
    private Entrance entrance1, entrance2;
    private int activitiesLeft = 15;

    public Children(int startid, Entrance newEntry1, Entrance newEntry2) {
        this.id = "N" + String.format("%04d", startid);
        this.entrance1 = newEntry1;
        this.entrance2 = newEntry2;
    }

    @Override
    public void run() {
        int coinFlip = (int) (0.5 + Math.random());
        if (coinFlip == 0) {
            entrance1.enterQueue(this);
        } else {
            entrance2.enterQueue(this);
        }
    }
}
