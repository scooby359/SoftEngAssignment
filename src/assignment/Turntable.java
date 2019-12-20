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
public class Turntable {
    
    public static enum Directions {
        NORTH(0),
        SOUTH(1),
        EAST(2),
        WEST(3);
        
        // From https://stackoverflow.com/questions/8811815
        // /is-it-possible-to-assign-numeric-value-to-an-enum-in-java
        private int numVal;
        Directions(int numVal) {
            this.numVal = numVal;
        }
        public int getNumVal() {
            return numVal;
        }
    }
    
    float TURN_SPEED = 0.5f;
    float MOVE_SPEED = 0.75f;
    
    String id;
    Present present;
    int[] AssociatedDestinations;
    PresentReceiver north;
    PresentReceiver east;
    PresentReceiver south;
    PresentReceiver west;

    public Turntable(String id, PresentReceiver north, PresentReceiver east, PresentReceiver south, PresentReceiver west) {
        this.id = id;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }
    
    void CheckForInput() {
        // TODO
    }
    
    void Turn() {
        // TODO
    }
    
    void isEmpty() {
        // TODO
    }
    
    void move() {
        // TODO
    }
    
    
    
}
