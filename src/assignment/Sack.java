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
public class Sack extends PresentReceiver{
    int id;
    int capacity;
    int count = 0;
    Present.AgeGroup ageGroup;
    Present[] presents;

    public Sack(int id, int capacity, Present.AgeGroup ageGroup) {
        this.id = id;
        this.capacity = capacity;
        this.ageGroup = ageGroup;
        this.presents = new Present[capacity];
    }
    
    boolean isFull() {
        // TODO - check if this returns capacity of array, or current contents?
        return presents.length == capacity;
    }
    
    public void addPresent(Present present) {
        // TODO
        // Need to lock presents
        // Add present
        // Release lock
    }
    
    int getCount() {
        return count;
    }
}
