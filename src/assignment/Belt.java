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

    private int id;
    private Present[] presents;
    private int[] destinations;
    private int beltLength = 0;
    private int presentCount = 0;

    private Semaphore mutex = new Semaphore(1);
    private Semaphore numAvail = new Semaphore(0);
    private Semaphore numFree;

    public Belt(int id, int beltLength, int[] destinations) {
        this.id = id;
        this.beltLength = beltLength;
        this.destinations = destinations;
        presents = new Present[this.beltLength];
        numFree = new Semaphore(beltLength);

        // System.out.println("Belt id " + id + " length " + beltLength);
    }

    @Override
    public void addPresent(Present present) {
        try {
            // Wait till free slot
            numFree.acquire();

            // Wait till mutex available
            mutex.acquire();
        } catch (InterruptedException e) {
            System.out.println("belt addPresent interrupt caught");
            return;
        }

        // CRITICAL CODE
        // Safety check - Ensure space available in buffer
        if (isFull()) {
            throw new IllegalStateException("No space to insert to belt array. ID: " + id);
        }

        // Add present to end of array and increment counter
        presents[presentCount] = present;
        presentCount++;

        // System.out.println("Belt " + this.id + " received present");

        // Release number of available slots
        numAvail.release();

        // Release mutex
        mutex.release();
    }

    @Override
    public Present removePresent() {
        try {
            // check something available to remove
            numAvail.acquire();

            // Get mutex
            mutex.acquire();

        } catch (InterruptedException e) {
            return null;
        }

        // CRITICAL CODE
        // Safety check - make sure a present exists
        if (isEmpty()) {
            throw new IllegalStateException("No presents in belt array to remove. ID: " + id);
        }

        // Get item at front of array
        Present removedPresent = presents[0];

        // Shuffle everything else down if neccessary
        if (presentCount > 1) {
            for (int i = 0; i < beltLength - 1; i++) {
                presents[i] = presents[i + 1];
            }

            // Can't shuffle anything to last place, so just set to null
            presents[beltLength - 1] = null;
        }

        // Decrement present counter
        presentCount--;
        // Signal free space
        numFree.release();

        // Release mutex
        mutex.release();

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

    @Override
    public Boolean checkPresentAvailable() {
        return !isEmpty();
    }

    @Override
    public int getId() {
        return this.id;
    }
    
     public int getPresentCount() {
        return presentCount;
    }
     
     @Override
     public int[] getDestinations() {
         return this.destinations;
     }
}
