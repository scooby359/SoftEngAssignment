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
import java.util.ArrayList;

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

        // Temp arrays to track sack target groups
        ArrayList<Integer> zero_three = new ArrayList<>();
        ArrayList<Integer> four_six = new ArrayList<>();
        ArrayList<Integer> seven_ten = new ArrayList<>();
        ArrayList<Integer> eleven_sixteen = new ArrayList<>();

        // Setup Sacks
        for (int i = 0; i < config.sacks.size(); i++) {

            // Get local ref for ease
            SackConfig configSack = config.sacks.get(i);

            // Create each sack instance
            AgeGroup ageGroup = Present.ConvertAgeStringToEnum(configSack.age);
            sacks[i] = new Sack(configSack.id, configSack.capacity, ageGroup);

            // Add sack to target list
            switch (ageGroup) {
                case ZEROTOTHREE:
                    zero_three.add(configSack.id);
                    break;
                case FOURTOSIX:
                    four_six.add(configSack.id);
                    break;
                case SEVENTOTEN:
                    seven_ten.add(configSack.id);
                    break;
                case ELEVENTOSIXTEEN:
                    eleven_sixteen.add(configSack.id);
                    break;
                default:
                    throw new IllegalArgumentException("Age group not recognised");
            }
        }

        // Setup presents for hoppers
        for (int i = 0; i < config.presents.size(); i++) {

            // Get local ref for ease
            PresentConfig configPresent = config.presents.get(i);

            // Create list of presents and populate
            Present[] presents = new Present[configPresent.ages.length];
            for (int j = 0; j < configPresent.ages.length; j++) {
                Present newPresent = new Present(configPresent.ages[j]);
                
                // Set present target list
                switch (newPresent.getGroup()) {
                    case ZEROTOTHREE:
                        newPresent.setTargetSacks(convertIntegers(zero_three));
                        break;
                    case FOURTOSIX:
                        newPresent.setTargetSacks(convertIntegers(four_six));
                        break;
                    case SEVENTOTEN:
                        newPresent.setTargetSacks(convertIntegers(seven_ten));
                        break;
                    case ELEVENTOSIXTEEN:
                        newPresent.setTargetSacks(convertIntegers(eleven_sixteen));
                        break;
                    default:
                        throw new IllegalArgumentException("Age group not recognised");
                }

                presents[j] = newPresent;
            }
            // Create hopper input
            hopperInputs[i] = new HopperInput(configPresent.id, presents);
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
            for (Belt belt : belts) {
                if (belt.id == configHopper.belt) {
                    receivingBelt = belt;
                }
            }

            hoppers[i] = new Hopper(configHopper.id, hopperInput.getPresents(), configHopper.speed, receivingBelt);
        }

        // Setup turntables
        for (int i = 0; i < config.turntables.size(); i++) {

            TurntableConfig configTurntable = config.turntables.get(i);
            
            // Check each turntable direction
            TurntableConnector north = setupPort(configTurntable.north, belts, sacks);
            TurntableConnector east = setupPort(configTurntable.east, belts, sacks);
            TurntableConnector south = setupPort(configTurntable.south, belts, sacks);
            TurntableConnector west = setupPort(configTurntable.west, belts, sacks);

            turntables[i] = new Turntable(configTurntable.id, north, east, south, west);
        }
    }

    private TurntableConnector setupPort(String config, Belt[] belts, Sack[] sacks) {
        // Early return for empty port
        if (config.equals("null")) {
            return null;
        }
        
        int targetId = Integer.parseInt(config.substring(3));
        ConnectionInterface port = null;
        Boolean input = false;
        
        
        // Find input belt
        if (config.startsWith("ib")) {
            input = true;
            for (Belt belt: belts) {
                if (belt.id == targetId) {
                    port = belt;
                    break;
                }
            }
        }
        
        // Find output belt
        if (config.startsWith("ob")) {
            for (Belt belt: belts) {
                if (belt.id == targetId) {
                    port = belt;
                    break;
                }
            }
        }
        
        // Find output sack
        if (config.startsWith("os")) {
            for (Sack sack: sacks) {
                if (sack.id == targetId) {
                    port = sack;
                    break;
                }
            }
        }
        
        // Safety check to ensure all set
        if (port  == null) {
            throw new IllegalStateException("Port not recognised");
        }
        
        return new TurntableConnector(port, input);
    }
    
    private int[] convertIntegers(ArrayList<Integer> input) {
        int[] ret = new int[input.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = input.get(i);
        }
        return ret;
    }

    public void start() {
        // Call from main function to start machine running
        // Should call all threaded objects to run

        for (Hopper hopper : hoppers) {
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
