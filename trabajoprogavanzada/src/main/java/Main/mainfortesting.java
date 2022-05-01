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
public class mainfortesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CommonArea common = new CommonArea();
        Entrance entrance1 = new Entrance(0,common);
        Entrance entrance2 = new Entrance(0,common);
        Children childtest = new Children(0,entrance1,entrance2);
        
        childtest.run();
    }
    
}
