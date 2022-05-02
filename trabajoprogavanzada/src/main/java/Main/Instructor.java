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
public class Instructor extends Thread{
    
    private String id;
    
    public Instructor(int startid) {
        this.id = "M" + String.format("%04d", startid);
    }
    
}
