/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Heras
 */
public class mainfortesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        startCamp();
    }
    
    public static void startCamp() {
        ZipLine zip = new ZipLine();
        Rope rope = new Rope();
        Snack snack = new Snack();
        CommonArea common = new CommonArea(zip, rope, snack);
        zip.setCommonArea(common);
        rope.setCommonArea(common);
        snack.setCommonArea(common);
        Semaphore campSemaphore = new Semaphore(50, true);
        Entrance entrance1 = new Entrance(common, campSemaphore);
        Entrance entrance2 = new Entrance(common, campSemaphore);
        Instructor instructortest = new Instructor(1, entrance1, entrance2);
        Instructor instructortest1 = new Instructor(2, entrance1, entrance2);
        for (int i = 1; i < 21; i++) {
            new Children(i, entrance1, entrance2).start();
        }

        instructortest.start();
        instructortest1.start();
    }

}
