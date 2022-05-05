/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Heras
 */
public class Snack {
    
    private LinkedBlockingQueue<Children> snackQueue = new LinkedBlockingQueue<Children>();
    private LinkedBlockingQueue<String> cleanTrays= new LinkedBlockingQueue<String>();
    private LinkedBlockingQueue<String> dirtyTrays = new LinkedBlockingQueue<String>();
    private CommonArea commonArea;
    private ReentrantLock snackLock;
    private Semaphore snackCapacity;
    private  Instructor snackInstructor1;
    private  Instructor snackInstructor2;
    
    
    
    public Snack(){
        snackCapacity = new Semaphore(20,true);
        snackLock = new ReentrantLock(true);
    }
    
    
    public void setCommonArea(CommonArea newCommonArea){
        this.commonArea = newCommonArea;
    }
    
    
    
    public void enterSnack(Children newChild){
        snackQueue.add(newChild);
        
    }
    
    
    public void useSnack(){
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
