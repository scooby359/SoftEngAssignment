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
    
    Present present;
    float turnSpeed;
    float moveSpeed;
    int[] AssociatedDestinations;

    public Turntable(float turnSpeed, float moveSpeed) {
        this.turnSpeed = turnSpeed;
        this.moveSpeed = moveSpeed;
        // TODO
        // need to hook up connecting conveyors or sacks
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
