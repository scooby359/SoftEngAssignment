/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.concurrent.Semaphore;

/**
 *
 * @author cewalton
 */
public class Belt extends ConnectionInterface {
    
    int id;
    Present[] presents;
    int beltLength = 0;
    int presentCount = 0;
    Semaphore mutex = new Semaphore(1);
    Semaphore numAvail = new Semaphore(0);
    Semaphore numFree;

    public Belt(int id, int beltLength) {
        this.id = id;
        this.beltLength = beltLength;
        presents = new Present[this.beltLength];
        numFree = new Semaphore(beltLength);
        
        System.out.println("Belt id " + id + " length " + beltLength);
    }
    
    @Override
    public void addPresent(Present present) {
        System.out.println("Belt: called addPresent");
        try {
            // Wait till free slot
            numFree.acquire();
            System.out.println("Belt: addPresent numFree acquired");
            // Wait till mutex available
            mutex.acquire();
            System.out.println("Belt: addPresent mutex acquired");
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }   
        
        // CRITICAL CODE
        insertPresentToArray(present);
        System.out.println("Belt " + this.id + " received present");
        
        // Release number of available slots
        numAvail.release();
        System.out.println("Belt: addPresent numAvail released");
        
        // Release mutex
        mutex.release();
        System.out.println("Belt: addPresent mutex released");
    }
    
    @Override
    public Present removePresent() {
        System.out.println("Belt: removePresent called");
        try {
            // check something available to remove
            numAvail.acquire();
            System.out.println("Belt: removePresent numAvail acquired");
            // Get mutex
            mutex.acquire();
            System.out.println("Belt: removePresent mutex acquired");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // CRITICAL CODE
        Present removedPresent = removePresentFromArray();
        
        // Signal free space
        numFree.release();
        System.out.println("Belt: removePresent numFree released");
        
        // Release mutex
        mutex.release();
        System.out.println("Belt: removePresent mutex released");
        
        return removedPresent;
    }
    
    public Boolean isFull() {
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
    
    
    private void insertPresentToArray(Present present) {
        // Ensure space available in buffer
        if (isFull()) {
            throw new IllegalStateException("No space to insert to belt array. ID: " + id);
        }
        
        System.out.println("Adding to belt.. current size: " + presentCount);
    
        // Add present to end of array and increment counter
        presents[presentCount] = present;
        presentCount++;
    }
    
    private Present removePresentFromArray() {
        if (isEmpty()) {
            throw new IllegalStateException("No presents in belt array to remove. ID: " + id);
        }
        
        System.out.println("Removing from belt.. current size: " + presentCount);
        
        // Get item at front of array
        Present returnPresent = presents[0];
        
        // Shuffle everything else down if neccessary
        if (presentCount > 1) {
            for (int i = 0; i < beltLength-1; i++) {
                presents[i] = presents[i+1];
            }
            
            // Can't shuffle anything to last place, so just set to null
            presents[beltLength-1] = null;
        }
        
        // Decrement present counter
        presentCount--;
        
        // Return first present
        return returnPresent;
    }

    @Override
    public Boolean checkPresentAvailable() {
        return !isEmpty();
    }

    @Override
    public int getId() {
        return this.id;
    }
}
