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

    private AgeGroup group;
    private int[] targetSacks;

    public Present(String input) {
        this.group = ConvertAgeStringToEnum(input);
    }

    public AgeGroup getGroup() {
        return group;
    }
    
    public void setTargetSacks(int[] targets) {
        this.targetSacks = targets;
    }
    
    public int[] getTargetSacks() {
        return targetSacks;
    }

    public static AgeGroup ConvertAgeStringToEnum(String input) throws IllegalArgumentException {
        switch (input) {
            case "0-3":
                return AgeGroup.ZEROTOTHREE;
            case "4-6":
                return AgeGroup.FOURTOSIX;
            case "7-10":
                return AgeGroup.SEVENTOTEN;
            case "11-16":
                return AgeGroup.ELEVENTOSIXTEEN;
            default:
                throw new IllegalArgumentException("Age not recognised");
        }
    }
}
