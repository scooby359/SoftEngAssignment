/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import assignment.ConfigFileReader.BeltConfig;
import assignment.ConfigFileReader.HopperConfig;
import assignment.ConfigFileReader.PresentConfig;
import assignment.ConfigFileReader.SackConfig;
import assignment.Present.AgeGroup;
import java.util.ArrayList;

/**
 *
 * @author cewalton
 */
public class Machine {
    Hopper[] hoppers;
    HopperInput[] hopperInputs;
    Belt[] belts;
    Turntable[] turntables;
    Sack[] sacks;
    
    int sessionLength;
    long startTime;
    long endTime;
    long finishTime;

    public Machine(ConfigFileReader.MachineConfig config) {
        
        // Initialise arrays to required sizes
        hoppers = new Hopper[config.hoppers.size()];
        hopperInputs = new HopperInput[config.hoppers.size()];
        belts = new Belt[config.belts.size()];
        turntables = new Turntable[config.turntables.size()];
        sacks = new Sack[config.sacks.size()];
        
        // Declare machine run time
        sessionLength = config.timer;
        
        
        // Setup presents for hoppers
        for (int i = 0; i < config.presents.size(); i++) {
            
            // Get local ref for ease
            PresentConfig configPresent = config.presents.get(i);
            
            // Create list of presents and populate
            Present[] presents = new Present[configPresent.ages.length];
            for (int j = 0; j < configPresent.ages.length; i++) {
                presents[j] = new Present(configPresent.ages[j]);
            }
            
            // Create hopper input
            hopperInputs[i] = new HopperInput(configPresent.id, presents);
        }
        
        // Setup Sacks
        for (int i = 0; i < config.sacks.size(); i++) {
            
            // Get local ref for ease
            SackConfig configSack = config.sacks.get(i);
            
            // Create each sack instance
            AgeGroup ageGroup = Present.ConvertAgeStringToEnum(configSack.age);
            sacks[i] = new Sack(configSack.id, configSack.capacity, ageGroup);
        }
        
        // Setup belts
        for (int i = 0; i < config.belts.size(); i++) {
            
            // Get local copy
            BeltConfig configBelt = config.belts.get(i);
            
            // TODO - Need to do something with the destinations
            belts[i] = new Belt(configBelt.id, configBelt.length);
        }
        
        // Setup hoppers
        for (int i = 0; i < config.hoppers.size(); i++) {
            
            // Get a local copy
            HopperConfig configHopper = config.hoppers.get(i);
            
            // Find corresponding input sack for present list
            HopperInput hopperInput = null;
            for (HopperInput hopperInput1 : hopperInputs) {
                if (hopperInput1.getHopperId() == configHopper.id) {
                    hopperInput = hopperInput1;
                    break;
                }
            }
            if (hopperInput == null) {
                throw new NullPointerException("Hopper input not found");
            }
            hoppers[i] = new Hopper(configHopper.id, hopperInput.getPresents(), configHopper.speed);
        }
        
        // Setup turntables
        
        
    }
    
    void logStart(){
        // TODO
    }
    
    void logInterval(){
        // TODO
    }
    
    void logEnd(){
        // TODO
    }
}
