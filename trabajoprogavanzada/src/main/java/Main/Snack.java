/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static java.lang.Thread.sleep;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Heras
 */
public class Snack {
    
    private LinkedBlockingQueue<Children> snackQueue = new LinkedBlockingQueue<Children>();
    private CommonArea commonArea;
    private ReentrantLock snackLock;
    private Semaphore snackCapacity;
    private Instructor snackInstructor1;
    private Instructor snackInstructor2;
    private int cleanTrays = 0;
    private int dirtyTrays = 25;
    private boolean empty;
    private Condition pileEmpty;
    private Condition pileFull;
    
    
    
    public Snack(){
        snackCapacity = new Semaphore(20,true);
        snackLock = new ReentrantLock(true);
    }
    
    
    public void setCommonArea(CommonArea newCommonArea){
        this.commonArea = newCommonArea;
        
    }
    
    
    
    
    
    
    public void useSnack(Children newChild) throws InterruptedException{
        snackQueue.add(newChild);
        snackCapacity.acquire();
        Children snackChildren = snackQueue.poll();
        snackLock.lock();
        try{
            while(empty){
            try{
                pileEmpty.await();
            }catch(Exception e){}
                       
        }
        empty = false;
        cleanTrays--;
        System.out.println(newChild.getIdChild() + " on Snack");
        sleep(7000);
        dirtyTrays++;
        pileFull.signalAll();
        }catch(Exception  e){}
        finally{
            snackQueue.remove(newChild);
            snackLock.unlock();
        }
        snackCapacity.release();
            
    }
    
    
    
    public void cleanTrays(Instructor instructor1, Instructor instructor2){
        System.out.println("Instructor1 on zipline");
        System.out.println("Instructor2 on zipline");
    }
    
    
    
    
    
    
    
    
    
    

}
