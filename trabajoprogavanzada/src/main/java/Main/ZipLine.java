/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static java.lang.Thread.sleep;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Heras
 */
public class ZipLine {
    
    private LinkedBlockingQueue<Children> zipQueue = new LinkedBlockingQueue<Children>();
    private CommonArea commonArea;
    private ReentrantLock zipLock;
    private Condition waitTurn;
    
    public void setCommonArea(CommonArea newCommonArea) {
        zipLock = new ReentrantLock(true);
        waitTurn = zipLock.newCondition();
        this.commonArea = newCommonArea;
    }
    
    public void useZipLine() {
        zipLock.lock();
        try {
            Children zipChildren = zipQueue.poll();
            //Getting ready
            sleep(1000);
            //Jump
            sleep(3000);
            //Get out of activity
            sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(ZipLine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
