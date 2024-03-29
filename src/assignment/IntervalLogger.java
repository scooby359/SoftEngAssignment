/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Thread for interval logging throughout machine run time
 * @author Chris
 */
public class IntervalLogger implements Runnable {

    long startTime;
    Hopper[] hoppers;
    Sack[] sacks;
    Boolean isActive = true;
    long INTERVALS = 10000;

    public IntervalLogger(Long startTime, Hopper[] hoppers, Sack[] sacks) {
        this.startTime = startTime;
        this.hoppers = hoppers;
        this.sacks = sacks;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(INTERVALS);
            } catch (InterruptedException ex) {
                ex.printStackTrace();            
            }
            if (isActive)
            {
                logInterval();
            }
            
        } while (isActive);
    }

    public void switchOff() {
        isActive = false;
        // Log final summary immediately
        logInterval();
    }

    void logInterval() {
        // Get current present counts for hoppers and sacks
        int presentsInHoppers = 0;
        for (Hopper hopper : hoppers) {
            presentsInHoppers += hopper.getRemainingPresentCount();
        }

        int presentsInSacks = 0;
        for (Sack sack : sacks) {
            presentsInSacks += sack.getCount();
        }

        logOutput("Presents left in hoppers: " + presentsInHoppers);
        logOutput("Presents in sacks: " + presentsInSacks);
    }

    private void logOutput(String input) {
        // Format elapsed time at interval to required format and log message
        long runTime = System.currentTimeMillis() - startTime;

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;

        long elapsedHours = runTime / hoursInMilli;
        runTime = runTime % hoursInMilli;

        long elapsedMinutes = runTime / minutesInMilli;
        runTime = runTime % minutesInMilli;

        long elapsedSeconds = runTime / secondsInMilli;

        System.out.println("" + elapsedHours + "h:" + elapsedMinutes + "m:" + elapsedSeconds + "s - " + input);
    }

}
