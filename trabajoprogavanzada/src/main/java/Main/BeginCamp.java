/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static java.lang.Thread.sleep;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heras
 */
public class BeginCamp implements Runnable {

    private PrinterLogger UIPrinterLogger;

    public BeginCamp(PrinterLogger ui) {
        this.UIPrinterLogger = ui;
    }

    public void startCamp() {
        ZipLine zip = new ZipLine(UIPrinterLogger);
        Rope rope = new Rope(UIPrinterLogger);
        Snack snack = new Snack(UIPrinterLogger);
        CommonArea common = new CommonArea(zip, rope, snack, UIPrinterLogger);
        zip.setCommonArea(common);
        rope.setCommonArea(common);
        snack.setCommonArea(common);
        Semaphore campSemaphore = new Semaphore(50, true);
        Entrance entrance1 = new Entrance(common, campSemaphore, UIPrinterLogger, "1");
        Entrance entrance2 = new Entrance(common, campSemaphore, UIPrinterLogger, "2");
        for(int i = 1; i<5;i++) {
            new Instructor(i,entrance1,entrance2).start();
        }
        for (int i = 1; i < 201; i++) {
            try {
                sleep((int) (1000 + 2000 * Math.random()));
            } catch (InterruptedException ex) {
                Logger.getLogger(BeginCamp.class.getName()).log(Level.SEVERE, null, ex);
            }
            new Children(i, entrance1, entrance2).start();
        }
    }

    public void run() {
        startCamp();
    }
}
