/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

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
        
        ConfigFileReader.MachineConfig config = fileReader.readFile();
        
        System.out.println("assignment.Assignment.main()");
    }
    
}
