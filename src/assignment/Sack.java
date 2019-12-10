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
public class Sack {
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
        return presents.length == capacity;
    }
    
    void addPresent(Present present) {
        // TODO
    }
    
    int getCount() {
        return count;
    }
}
