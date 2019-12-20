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
    int id;
    Present[] presents;
    boolean isActive;
    // TargetConveyor
    float speed;
    long waitingTime;
    int startingPresentCount;    
    int presentsReleased = 0;

    public Hopper(int id, Present[] presents, float speed) {
        this.id = id;
        this.presents = presents;
        this.speed = speed;
        this.startingPresentCount = presents.length;
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
