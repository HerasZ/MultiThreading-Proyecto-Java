/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

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
        AtomicInteger capacity = new AtomicInteger(0);
        ZipLine zip = new ZipLine();
        Rope rope = new Rope();
        Snack snack = new Snack();
        CommonArea common = new CommonArea(zip,rope,snack);
        zip.setCommonArea(common);
        Entrance entrance1 = new Entrance(capacity, common);
        Entrance entrance2 = new Entrance(capacity, common);
        Instructor instructortest = new Instructor(1, entrance1, entrance2);
        Children childtest = new Children(1, entrance1, entrance2);

        childtest.start();
        instructortest.start();
    }

}
