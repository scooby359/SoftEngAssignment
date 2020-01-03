/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import assignment.Present.AgeGroup;
import java.util.concurrent.Semaphore;

/**
 *
 * @author cewalton
 */
public class Sack extends ConnectionInterface {

    private int id;
    private int capacity;
    private int count = 0;
    private AgeGroup ageGroup;
    private Present[] presents;

    public Sack(int id, int capacity, AgeGroup ageGroup) {
        this.id = id;
        this.capacity = capacity;
        this.ageGroup = ageGroup;
        this.presents = new Present[capacity];
    }

    public Boolean isFull() {
        return count == capacity;
    }

    public AgeGroup getAgeGroup() {
        return this.ageGroup;
    }

    public int getId() {
        return this.id;
    }

    @Override
    public void addPresent(Present present) {
        presents[count] = present;
        count++;
    }

    int getCount() {
        return count;
    }

    @Override
    public Boolean isSack() {
        return true;
    }

    @Override
    public Present removePresent() {
        // Not used by this class
        return null;
    }

}
