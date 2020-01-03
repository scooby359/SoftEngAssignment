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

    /*
                =============================================
                CHANGE STRUCTURE - 
                - On Turntable loop, check array of sacks, if exist in gifts potential targets, check if !full, then try and push
                - Else, try next sack.. continue
                - Else, check array of belts if target, push
                -- if belt full, should check next and continue to loop round belts until succeed?
         
                =============================================
     */
    // Delay for moving present
    // Pass to receiver
    final int NORTH = 0;
    final int EAST = 1;
    final int SOUTH = 2;
    final int WEST = 3;

    long TURN_SPEED = 500;
    long MOVE_SPEED = 750;
    String NORTH_SOUTH = "ns";
    String EAST_WEST = "es";

    String id;
    Boolean isActive = true;
    Present present;
    String currentAlignment = EAST_WEST;
    TurntableConnector[] inputBelts = new TurntableConnector[4];
    TurntableConnector[] outputBelts = new TurntableConnector[4];
    TurntableConnector[] outputSacks = new TurntableConnector[4];
    TurntableConnector[] allConnections;

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
                }
                // Check if input belt
                if (allConnections[i].input) {
                    inputBelts[i] = allConnections[i];
                } else {
                    outputBelts[i] = allConnections[i];
                }
            }
        }
    }

    @Override
    public void run() {
        System.out.println("Turntable run");
        do {
            checkForInput();
        } while (isActive);
    }

    private void checkForInput() {
        // Loop through input connections
        for (TurntableConnector connector : inputBelts) {

            // Check if input available
            if (connector != null && connector.port.checkPresentAvailable()) {

                // Check if in alignment
                alignTurntable(connector);

                // Delay to get present
                movePresentDelay();

                // Move present onto turntable (remove from belt)
                present = connector.port.removePresent();

                // Get list of destination sack IDs
                int[] destinationSacks = present.getTargetSacks();
                TurntableConnector destination;

                // Loop through output sacks to check if found
                for (TurntableConnector outputSack : outputSacks) {
                    if (outputSack != null) {

                        // Get target sack ID
                        int outputSackId = outputSack.port.getId();

                        // Check if present can go to that sack
                        for (int targetSack : destinationSacks) {
                            if (targetSack == outputSack.port.getId()) {

                                // Check if space available
                                if (!outputSack.port.isFull()) {
                                    // Found suitable destination
                                    destination = outputSack;

                                    // Check if aligned
                                    alignTurntable(destination);

                                    // Move present to receiver
                                    destination.port.addPresent(present);
                                    movePresentDelay();

                                    // TODO - should return a boolean so we know if it worked
                                    
                                    // Assume present has moved, so clear out holder
                                    present = null;
                                }
                            }
                        }
                    }
                }

            }

            // Else find destination belt
            // Check if in alignment
            // Turn turntable
            // Move present
            // Add to destination connection
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
        
        if (connection == allConnections[NORTH] || connection == allConnections[SOUTH]) {
            isAligned = currentAlignment.equals(NORTH_SOUTH);
        } else {
            isAligned = currentAlignment.equals(EAST_WEST);
        }
        
        if (!isAligned) {
            System.out.println("Turntable " + id + "turning..");
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
}
