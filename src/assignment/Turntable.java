/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import assignment.Present.AgeGroup;

/**
 *
 * @author cewalton
 */
public class Turntable implements Runnable {

    // Set up constants
    final int NORTH = 0;
    final int EAST = 1;
    final int SOUTH = 2;
    final int WEST = 3;

    final long TURN_SPEED = 500;
    final long MOVE_SPEED = 750;
    final String NORTH_SOUTH = "ns";
    final String EAST_WEST = "es";

    // Variables for configuration
    private String id;
    private Boolean isActive = true;    // For overall control of thread
    private Present present;
    private String currentAlignment = EAST_WEST;
    private TurntableConnector[] inputBelts = new TurntableConnector[4];
    private TurntableConnector[] outputBelts = new TurntableConnector[4];
    private TurntableConnector[] outputSacks = new TurntableConnector[4];
    private TurntableConnector[] allConnections;

    public Turntable(String id, TurntableConnector north,
            TurntableConnector east, TurntableConnector south,
            TurntableConnector west) {

        this.id = id;

        // Setup turntable connections
        allConnections = new TurntableConnector[]{north, east, south, west};
        for (int i = 0; i < allConnections.length; i++) {
            // Skip any null ports
            if (allConnections[i] != null) {
                // Check if sack
                if (allConnections[i].port.isSack()) {
                    outputSacks[i] = allConnections[i];
                } // Check if input belt
                else if (allConnections[i].input) {
                    inputBelts[i] = allConnections[i];
                } else {
                    outputBelts[i] = allConnections[i];
                }
            }
        }
    }

    @Override
    public void run() {
        // System.out.println("Turntable run");
        do {
            checkForInput();
        } while (isActive);
    }

    private void checkForInput() {

        // Indicate if belt is empty
        Boolean beltLoopEmpty = true;

        // Loop through input connections
        for (TurntableConnector connector : inputBelts) {

            // Declare where present will go
            TurntableConnector destination = null;

            // Check if input available
            if (connector != null && connector.port.checkPresentAvailable()) {

                // Update belt flag
                beltLoopEmpty = false;

                // Check if in alignment
                alignTurntable(connector);

                // Delay to get present
                movePresentDelay();

                // Move present onto turntable (remove from belt)
                present = connector.port.removePresent();

                // Get list of destination sack IDs
                int[] destinationSacks = present.getTargetSacks();

                // --------------
                // Figure out where to put present
                // --------------
                // Loop through output sacks to check if found
                for (TurntableConnector outputSack : outputSacks) {

                    // Make sure sack is connected and 
                    // we haven't already found a suitable sack
                    if (outputSack != null && destination == null) {

                        // Check if present can go to that sack
                        for (int targetSack : destinationSacks) {
                            if (targetSack == outputSack.port.getId()) {

                                // Check if space available
                                if (!outputSack.port.isFull()) {
                                    // Found suitable destination
                                    destination = outputSack;
                                }
                            }
                        }
                    }
                }

                // Suitable sack not found - look at belts instead
                if (destination == null) {

                    // For each output belt
                    for (TurntableConnector outputBelt : outputBelts) {

                        // Check belt is connected and
                        // we haven't already found a destination
                        if (outputBelt != null && destination == null) {

                            // Get belt destinations
                            int[] beltDestinations = outputBelt.port.getDestinations();

                            // Check if any of targetDestinations 
                            // exist in beltDestinations
                            for (int destinationSack : destinationSacks) {
                                for (int beltDestination : beltDestinations) {
                                    if (destinationSack == beltDestination) {
                                        destination = outputBelt;
                                    }
                                }
                            }
                        }
                    }
                }

                // Safety check that a destination has been found
                if (destination == null) {
                    System.out.println("***TURNTABLE ID: " + this.id + "***");
                    present = null;
                    System.out.println("Suitable output not found - disposing present");
                    return;
                    // throw new IllegalStateException("Suitable output not found - disposing present");
                }

                // Check if aligned to destination
                alignTurntable(destination);

                // Move present to receiver
                destination.port.addPresent(present);
                movePresentDelay();

                // Present has gone so clear local holder
                present = null;

            }
        }
    }

    public Boolean isFull() {
        return present != null;
    }

    private void movePresentDelay() {
        try {
            Thread.sleep(MOVE_SPEED);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void alignTurntable(TurntableConnector connection) {
        Boolean isAligned;
        
        // Checks current alignment to given connection
        if (connection == allConnections[NORTH] || connection == allConnections[SOUTH]) {
            isAligned = currentAlignment.equals(NORTH_SOUTH);
        } else {
            isAligned = currentAlignment.equals(EAST_WEST);
        }

        if (!isAligned) {
            // System.out.println("Turntable " + id + " turning..");
            try {
                Thread.sleep(TURN_SPEED);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            // Update new alignment
            currentAlignment = currentAlignment.equals(EAST_WEST) ? NORTH_SOUTH : EAST_WEST;
        }
    }

    public void switchOff() {
        this.isActive = false;
    }

    public String getId() {
        return id;
    }
}
