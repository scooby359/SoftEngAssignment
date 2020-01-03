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
        String configFile = "config_basic_1.txt";
        ConfigFileReader fileReader = new ConfigFileReader(configFile);

        // Build config object from input
        MachineConfig config = fileReader.readFile();

        // Create machine and runMachine running
        Machine machine = new Machine(config, configFile);
        machine.runMachine();

    }

}
