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

    long TURN_SPEED = 500;
    long MOVE_SPEED = 750;
    String NORTH_SOUTH = "ns";
    String EAST_WEST = "es";

    String id;
    Boolean isActive = true;
    Present present;
    int[] AssociatedDestinations;
    ConnectionInterface north;
    ConnectionInterface east;
    ConnectionInterface south;
    ConnectionInterface west;
    String currentAlignment = EAST_WEST;
    ConnectionInterface[] connections;

    public Turntable(String id, ConnectionInterface north, ConnectionInterface east, ConnectionInterface south, ConnectionInterface west) {
        this.id = id;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;

        // Create array for easy looping
        connections = new ConnectionInterface[]{north, south, east, west};
    }

    @Override
    public void run() {
        do {
            checkForInput();
        } while (isActive);
    }

    private void checkForInput() {
        // Loop through each connection
        for (ConnectionInterface connection : connections) {

            // Check if present waiting
            if (connection.checkPresentAvailable()) {

                // Check if need to change direction to receive present
                if (!isAligned(connection)) {
                    turnTurntable();
                }
                
                // Get present from belt
                present = connection.removePresent();
                
                // Delay for moving present
                movePresent();
                ConnectionInterface receivingPort;
                
                // Check if need to turn to receiver
                for (ConnectionInterface receiver : connections){ 
                    
                    // Find appropriate place for present to go
                    // Check for available connected sack
                    
                    if (receiver.isSack() && !receiver.isFull()) {
                        // Check if sack can take present age group
                        AgeGroup sackAge = ((Sack) receiver).getAgeGroup();
                        if (present.getGroup() == sackAge) {
                            receivingPort = receiver;
                        }
                    }
                }
                
                
                /*
                =============================================
                CHANGE STRUCTURE - 
                - On machine initialisation, each gift should have an array of valid end sacks, by matching age group to config of each sack
                - Turntables should have two arrays - sacks / belts (can use consts for ease (NORTH = 0, EAST = 1...))
                - On Turntable loop, check array of sacks, if exist in gifts potential targets, check if !full, then try and push
                - Else, check array of belts and push to available belt with corresponding target
                - Potential for backup in complex config... e.g.
                        O
                        |
                        O-O-4/5 >> Might pass here, find sack full, no way to pass back to try alternative sack. Not much can be done except keep an eye on Nicks config file
                        |
                        O
                        |
                        4/5
                =============================================
                */
                
                // Delay for moving present
                
                // Pass to receiver
                
            }
        }
    }

    public Boolean isFull() {
        return present != null;
    }

    private void movePresent() {
        try {
            Thread.sleep(MOVE_SPEED);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void turnTurntable() {
        // Sleep for required time
        System.out.println("Turntable " + id + "turning..");
        try {
            Thread.sleep(TURN_SPEED);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        // Update new alignment
        currentAlignment = currentAlignment.equals(EAST_WEST) ? NORTH_SOUTH : EAST_WEST;
    }

    private boolean isAligned(ConnectionInterface connection) {

        if (connection == north || connection == south) {
            return currentAlignment.equals(NORTH_SOUTH);
        } else {
            return currentAlignment.equals(EAST_WEST);
        }
    }
}
