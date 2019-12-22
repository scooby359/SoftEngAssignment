/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import assignment.ConfigFileReader.BeltConfig;
import assignment.ConfigFileReader.HopperConfig;
import assignment.ConfigFileReader.MachineConfig;
import assignment.ConfigFileReader.PresentConfig;
import assignment.ConfigFileReader.SackConfig;
import assignment.ConfigFileReader.TurntableConfig;
import assignment.Present.AgeGroup;

/**
 *
 * @author cewalton
 */
public class Machine {

    Hopper[] hoppers;
    Belt[] belts;
    Turntable[] turntables;
    Sack[] sacks;

    int sessionLength;
    long startTime;
    long endTime;
    long finishTime;

    public Machine(MachineConfig config) {

        HopperInput[] hopperInputs;

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
            for (int j = 0; j < configPresent.ages.length; j++) {
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
            
            Belt receivingBelt = null;
            for(Belt belt: belts) {
                if(belt.id == configHopper.belt) {
                    receivingBelt = belt;
                }
            }
            
            hoppers[i] = new Hopper(configHopper.id, hopperInput.getPresents(), configHopper.speed, receivingBelt);
        }

        // Setup turntables
        for (int i = 0; i < config.turntables.size(); i++) {

            TurntableConfig configTurntable = config.turntables.get(i);

            ConnectionInterface north = null;
            ConnectionInterface east = null;
            ConnectionInterface south = null;
            ConnectionInterface west = null;

            int outputId;

            // Check each turntable direction
            if (configTurntable.north.startsWith("os")
                    || configTurntable.north.startsWith("ob")) {
                outputId = Integer.parseInt(configTurntable.north.substring(3));
                if (configTurntable.north.startsWith("os")) {
                    for (Sack sack : sacks) {
                        if (sack.id == outputId) {
                            north = sack;
                            break;
                        }
                    }
                }
                if (configTurntable.north.startsWith("ob")) {
                    for (Belt belt : belts) {
                        if (belt.id == outputId) {
                            north = belt;
                            break;
                        }
                    }
                }
            }

            if (configTurntable.east.startsWith("os")
                    || configTurntable.east.startsWith("ob")) {
                outputId = Integer.parseInt(configTurntable.east.substring(3));
                if (configTurntable.east.startsWith("os")) {
                    for (Sack sack : sacks) {
                        if (sack.id == outputId) {
                            east = sack;
                            break;
                        }
                    }
                }
                if (configTurntable.east.startsWith("ob")) {
                    for (Belt belt : belts) {
                        if (belt.id == outputId) {
                            east = belt;
                            break;
                        }
                    }
                }
            }

            if (configTurntable.south.startsWith("os")
                    || configTurntable.south.startsWith("ob")) {
                outputId = Integer.parseInt(configTurntable.south.substring(3));
                if (configTurntable.south.startsWith("os")) {
                    for (Sack sack : sacks) {
                        if (sack.id == outputId) {
                            south = sack;
                            break;
                        }
                    }
                }
                if (configTurntable.south.startsWith("ob")) {
                    for (Belt belt : belts) {
                        if (belt.id == outputId) {
                            south = belt;
                            break;
                        }
                    }
                }
            }

            if (configTurntable.west.startsWith("os")
                    || configTurntable.west.startsWith("ob")) {
                outputId = Integer.parseInt(configTurntable.west.substring(3));
                if (configTurntable.west.startsWith("os")) {
                    for (Sack sack : sacks) {
                        if (sack.id == outputId) {
                            west = sack;
                            break;
                        }
                    }
                }
                if (configTurntable.west.startsWith("ob")) {
                    for (Belt belt : belts) {
                        if (belt.id == outputId) {
                            west = belt;
                            break;
                        }
                    }
                }
            }

            turntables[i] = new Turntable(configTurntable.id, north, east, south, west);
        }
    }

    public void start() {
        // Call from main function to start machine running
        // Should call all threaded objects to run
        
        for (Hopper hopper: hoppers) {
            hopper.run();
        }
        
        // TODO - start all other threaded objects
    }
    
    void logStart() {
        // TODO
    }

    void logInterval() {
        // TODO
    }

    void logEnd() {
        // TODO
    }
}
