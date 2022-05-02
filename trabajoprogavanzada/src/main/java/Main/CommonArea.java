/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Heras
 */
public class CommonArea {

    private LinkedBlockingQueue<Children> commonAreaChildren = new LinkedBlockingQueue<Children>();
    private LinkedBlockingQueue<Instructor> commonAreaInstructors = new LinkedBlockingQueue<Instructor>();
    private ZipLine ziplineActivity;
    private Rope ropeActivity;
    private Snack snackActivity;

    public void enterChildren(Children newChild) {
        commonAreaChildren.add(newChild);
        System.out.println("Child common area");
    }

    public void enterInstructor(Instructor newInstructor) {
        commonAreaInstructors.add(newInstructor);
        System.out.println("Instructor common area");
    }
}
