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

public class Present {
    
    public static enum AgeGroup {
        ZEROTOTHREE,
        FOURTOSIX,
        SEVENTOTEN,
        ELEVENTOSIXTEEN
    }
    
    AgeGroup group;

    public Present(String input) {
        
        switch(input) {
            case "0-3":
                group = AgeGroup.ZEROTOTHREE;
                break;
            case "4-6":
                group = AgeGroup.FOURTOSIX;
                break;
            case "7-10":
                group = AgeGroup.SEVENTOTEN;
                break;
            case "11-16":
                group = AgeGroup.ELEVENTOSIXTEEN;
                break;
            default:
                throw new IllegalArgumentException("Age not recognised");
        }
    }
    
    AgeGroup getGroup() {
        return group;
    };
    
}
