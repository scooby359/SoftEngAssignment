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
public class Hopper implements Runnable {

    private int id;
    private Present[] presents;
    private boolean isActive = true;
    private Belt receiverBelt;
    private long speed;
    private long waitingTime = 0;
    private int startingPresentCount;
    private int presentsReleased = 0;

    public Hopper(int id, Present[] presents, long speed, Belt receiverBelt) {
        this.id = id;
        this.presents = presents;
        this.speed = speed * 1000;
        this.startingPresentCount = presents.length;
        this.receiverBelt = receiverBelt;
    }

    void relasePresent() {
        // System.out.println("Hopper " + this.id + " releasing present");
        
        // Capture start time to track how long spent waiting
        long startTime = System.currentTimeMillis();
        
        // Check presents being released in correct order
        // System.out.println("Releasing present: " + presents[presentsReleased].getGroup());
        
        // Try to add present to belt
        receiverBelt.addPresent(presents[presentsReleased]);
        
        // Capture end time to track how long spent waiting
        long endTime = System.currentTimeMillis();
        
        // Update time spent waiting
        waitingTime += (endTime - startTime);
                
        // Keep track of how many presents released
        presentsReleased++;
        
        // Switch hopper off if all presents released
        if (presentsReleased == startingPresentCount) {
            isActive = false;
        }
    }

    public void switchOff() {
        this.isActive = false;
    }

    int getRemainingPresentCount() {
        return startingPresentCount - presentsReleased;
    }

    int getStartingPresentCount() {
        return startingPresentCount;
    }

    @Override
    public void run() {
        // System.out.println("Hopper run");
        do {
            try {
                Thread.sleep(speed);
            } catch (InterruptedException ex) {
                // Handle early shutdown by machine
                // Make sure we don't loop again
                isActive = false;
                // Return with releasing present
                return;
            }
            this.relasePresent();
        } while (isActive);
    }

    public String getFinalSummary() {
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long different = this.waitingTime;
        
        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        
        String waitTime = "" + elapsedHours + "h:" + elapsedMinutes + "m:" + elapsedSeconds + "s";
        
        String summary = "Hopper ID: " + this.id + ". Total presents released: " + this.presentsReleased + ". Total wait time: " + waitTime;
        return summary;
    }
}
