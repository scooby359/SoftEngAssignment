/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import assignment.ConfigFileReader.MachineConfig;

/**
 *
 * @author cewalton
 */
public class Assignment {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        ConfigFileReader fileReader = new ConfigFileReader();
        
        MachineConfig config = fileReader.readFile();
        
        Machine machine = new Machine(config);
        
        // TODO - should probbaly just be a machine.start() call, move all the run calls inside Machine class
        for (Hopper hopper : machine.hoppers) {
            hopper.run();
        }
        
        System.out.println("assignment.Assignment.main()");
    }
    
}
