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
        
        // Read config file in
        ConfigFileReader fileReader = new ConfigFileReader();
        MachineConfig config = fileReader.readFile();
        
        // Create machine and start running
        Machine machine = new Machine(config);
        machine.start();
     
        
        System.out.println("assignment.Assignment.main()");
    }
    
}
