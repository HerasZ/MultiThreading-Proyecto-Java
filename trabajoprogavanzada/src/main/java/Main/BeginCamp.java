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
public class BeginCamp {
    
    public static void startCamp(PrinterLogger UIPrinterLogger) {
        ZipLine zip = new ZipLine(UIPrinterLogger);
        Rope rope = new Rope(UIPrinterLogger);
        Snack snack = new Snack(UIPrinterLogger);
        CommonArea common = new CommonArea(zip, rope, snack,UIPrinterLogger);
        zip.setCommonArea(common);
        rope.setCommonArea(common);
        snack.setCommonArea(common);
        Semaphore campSemaphore = new Semaphore(50, true);
        Entrance entrance1 = new Entrance(common, campSemaphore,UIPrinterLogger);
        Entrance entrance2 = new Entrance(common, campSemaphore,UIPrinterLogger);
        Instructor instructortest = new Instructor(1, entrance1, entrance2);
        Instructor instructortest1 = new Instructor(2, entrance1, entrance2);
        for (int i = 1; i < 21; i++) {
            new Children(i, entrance1, entrance2).start();
        }

        instructortest.start();
        instructortest1.start();
    }

}
