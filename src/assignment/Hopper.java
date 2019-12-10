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
public class Hopper {
    Present[] presents;
    boolean isActive;
    // TargetConveyor
    float speed;
    long waitingTime;
    int startingPresentCount;    
    int presentsReleased = 0;

    public Hopper(Present[] presents, float speed, int startingPresentCount) {
        this.presents = presents;
        this.speed = speed;
        this.startingPresentCount = startingPresentCount;
        // Init target conveyor
    }
    
    
    
    Present relasePresent() {
        // TODO
        // Check if conveyor free
        // Try and call conveyor
        // Remove present from hopper array
        return null;
    }
    
    int getRemainingPresentCount() {
        return startingPresentCount - presentsReleased;
    }
    
    int getStartingPresentCount() {
        return startingPresentCount;
    }
}
