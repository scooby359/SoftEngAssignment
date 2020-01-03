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
 * Parent machine - mostly just construction of objects, also threads for run time and logging
 * @author cewalton
 */
public class Machine {

    String configFileName;
    Hopper[] hoppers;
    Belt[] belts;
    Turntable[] turntables;
    Sack[] sacks;
    Thread[] threads;

    long sessionLength;
    long startTime;
    long endTime;
    long finishTime;

    public Machine(MachineConfig config, String configFile) {
        HopperInput[] hopperInputs;

        // Needed for final summary
        this.configFileName = configFile;
        
        // Initialise arrays to required sizes
        hoppers = new Hopper[config.hoppers.size()];
        hopperInputs = new HopperInput[config.hoppers.size()];
        belts = new Belt[config.belts.size()];
        turntables = new Turntable[config.turntables.size()];
        sacks = new Sack[config.sacks.size()];

        // Declare machine run time - convert seconds to milliseconds
        sessionLength = config.timer * 1000;

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
            belts[i] = new Belt(configBelt.id, configBelt.length, configBelt.destinations);
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
                if (belt.getId() == configHopper.belt) {
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

        // Setup threads
        int totalThreads = hoppers.length + turntables.length;
        int currentThread = 0;
        threads = new Thread[totalThreads];

        for (Hopper hopper : hoppers) {
            threads[currentThread] = new Thread(hopper);
            currentThread++;
        }

        for (Turntable turntable : turntables) {
            threads[currentThread] = new Thread(turntable);
            currentThread++;
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
            for (Belt belt : belts) {
                if (belt.getId() == targetId) {
                    port = belt;
                    break;
                }
            }
        }

        // Find output belt
        if (config.startsWith("ob")) {
            for (Belt belt : belts) {
                if (belt.getId() == targetId) {
                    port = belt;
                    break;
                }
            }
        }

        // Find output sack
        if (config.startsWith("os")) {
            for (Sack sack : sacks) {
                if (sack.getId() == targetId) {
                    port = sack;
                    break;
                }
            }
        }

        // Safety check to ensure all set
        if (port == null) {
            throw new IllegalStateException("Port not recognised");
        }

        return new TurntableConnector(port, input);
    }

    private int[] convertIntegers(ArrayList<Integer> input) {
        // Config uses Integer object, need to convert to int primitive
        int[] ret = new int[input.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = input.get(i);
        }
        return ret;
    }

    public void runMachine() {

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Set machine start time
        startTime = System.currentTimeMillis();
        logOutput("Machine Started");

        // Create and run interval logger
        IntervalLogger intervalLogger = 
                new IntervalLogger(startTime, hoppers, sacks);
        Thread intervalLoggerThread = new Thread(intervalLogger);
        intervalLoggerThread.start();
        
        // Let session length run
        try {
            Thread.sleep(sessionLength);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        // Stop all input hoppers after session length complete
        for (Hopper hopper : hoppers) {
            hopper.switchOff();
        }
        logOutput("Input stopped");

        // Send stop indicator to all turntables 
        // - will continue to run until inputs clear
        for (Turntable turntable : turntables) {
            turntable.switchOff();
        }

        // Wait till everything complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
        // Stop interval logger
        intervalLogger.switchOff();

        // All threads complete - machine shutdown
        endTime = System.currentTimeMillis();
        logOutput("Machine shutdown");
        printSummary();
    }

    private void logOutput(String input) {

        // Convenience function - outputs time in required format 
        
        long different = System.currentTimeMillis() - startTime;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        
        System.out.println("" + elapsedHours + "h:" + elapsedMinutes + "m:" + elapsedSeconds + "s - " + input);
    }

    private void printSummary() {
        
        // Initialise counters
        int onBeltCount = 0;
        int onTurntableCount = 0;
        int totalInput = 0;
        int totalInSacks = 0;
        int totalInHoppers = 0;
        int difference = 0;
        
        System.out.println("");
        System.out.println("*** Summary Report ***");
        System.out.println("Config file: " + this.configFileName);
        logOutput("Total run time");
        
        
        // Get counts of presents left on machine
        for (Belt belt : belts) {
            onBeltCount += belt.getPresentCount();
            // System.out.println("- Belt ID: " + belt.getId() + ". Presents on belt: " + belt.getPresentCount());
        }
        
        for (Turntable turntable : turntables) {
            if (turntable.isFull()) {
                onTurntableCount++;
            }
            // System.out.println("- Turntable ID: " + turntable.getId() + ". Present on turntable: " + (turntable.isFull() ? "1" : "0"));
        }
        
        System.out.println("Total presents left on machine (Belts and turntables): " + (onBeltCount + onTurntableCount));
        
        // Get hopper values
        for (Hopper hopper : hoppers) {
            totalInput += hopper.getStartingPresentCount();
            totalInHoppers += hopper.getRemainingPresentCount();
            System.out.println(hopper.getFinalSummary());
        }
        
        for (Sack sack : sacks) {
            totalInSacks += sack.getCount();
        }
        System.out.println("Total in sacks: " + totalInSacks);
        
        difference = totalInput - (totalInSacks + onBeltCount + onTurntableCount + totalInHoppers);
        System.out.println("Total of inputs - (total in sacks , belts, turntables and hoppers) = " + difference);
    }
}
