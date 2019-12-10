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
public class Conveyor {
    
    Present[] presents;
    int beltLength;
    int presentCount;

    public Conveyor(int beltLength) {
        this.beltLength = beltLength;
        presents = new Present[this.beltLength];
    }
    
    void addPresent() {
        // TODO
    }
    
    boolean isFull() {
        return presentCount == beltLength;
    }
    
    boolean isEmpty() {
        return presentCount == 0;
    }
    
    int getCount() {
        return presentCount;
    }
    
    boolean isWaiting() {
        return presentCount > 0;
    }
    
    
}
