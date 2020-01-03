/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

/**
 *
 * @author Chris
 */
public abstract class ConnectionInterface {
        
    /**
     * Receive present from calling function
     * @param present
     */
    public abstract void addPresent(Present present);
    
    /**
     * Removes a present from the called object
     * @return Present
     */
    public abstract Present removePresent();
    
    /**
     * Check if present waiting
     * @return True if present waiting
     */
    public Boolean checkPresentAvailable() {
        return false;
    }
    
    /**
     * Gets object id
     * @return int
     */
    public abstract int getId();
    
    /**
     * Returns if object is a sack type
     * @return Boolean
     */
    public Boolean isSack() {
        return false;
    }
    
    /**
     * Returns if receiver is full
     * @return Boolean
     */
    public abstract Boolean isFull();
}
