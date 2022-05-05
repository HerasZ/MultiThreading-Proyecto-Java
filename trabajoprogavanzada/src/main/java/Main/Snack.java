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
    
    
    
    public void enterSnack(Children newChild) throws InterruptedException{
        snackQueue.add(newChild);
        useSnack();
        
    }
    
    
    public void useSnack() throws InterruptedException{
        snackCapacity.acquire();
        Children snackChildren = snackQueue.poll();
        snackLock.lock();
        while(empty){
            try{
                pileEmpty.await();
            }catch(Exception e){}
                       
        }
        empty = false;
        cleanTrays--;
        dirtyTrays++;
        System.out.println(newChild.getIdChild() + " on Snack");
        sleep(7000);
        pileFull.signalAll();
            
            
            
            
            
            
        
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
