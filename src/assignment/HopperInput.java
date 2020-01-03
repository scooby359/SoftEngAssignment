/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

/**
 * Used for setting up machine config to match present sack to hopper
 * @author Chris
 */
public class HopperInput {
    private int hopperId;
    private Present[] presents;

    public HopperInput(int hopperId, Present[] presents) {
        this.hopperId = hopperId;
        this.presents = presents;
    }

    public int getHopperId() {
        return hopperId;
    }

    public void setHopperId(int hopperId) {
        this.hopperId = hopperId;
    }

    public Present[] getPresents() {
        return presents;
    }

    public void setPresents(Present[] presents) {
        this.presents = presents;
    }
    
    
}
