/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author cewalton // TODO - parse TIMER, return everything to machine to build
 */
public class ConfigFileReader {

    // String configName = "config_basic.txt";
    String configName = "config.txt";

    /**
     * The belt configuration
     */
    public class BeltConfig {

        int id;
        int length;
        int[] destinations;

        BeltConfig(int id, int length, int[] destinations) {
            this.id = id;
            this.length = length;
            this.destinations = destinations;
        }
    }

    /**
     * The hopper configuration
     */
    public class HopperConfig {

        int id;
        int belt;
        int capacity;
        int speed;

        HopperConfig(int id, int belt, int capacity, int speed) {
            this.id = id;
            this.belt = belt;
            this.capacity = capacity;
            this.speed = speed;
        }
    }

    /**
     * The sack configuration
     */
    public class SackConfig {

        int id;
        int capacity;
        String age;

        SackConfig(int id, int capacity, String age) {
            this.id = id;
            this.capacity = capacity;
            this.age = age;
        }
    }

    /**
     * The turntable configuration
     */
    public class TurntableConfig {

        String id;
        String north;
        String east;
        String south;
        String west;

        TurntableConfig(String id, String north, String east, String south, String west) {
            this.id = id;
            this.north = north;
            this.east = east;
            this.south = south;
            this.west = west;
        }
    }

    /**
     * The present configuration
     */
    public class PresentConfig {

        int id;
        String[] ages;

        PresentConfig(int id, String[] age) {
            this.id = id;
            this.ages = age;
        }
    }

    /**
     * Machine configuration parameters
     */
    public class MachineConfig {

        /**
         * List of belt configurations
         */
        ArrayList<BeltConfig> belts;
        /**
         * List of hopper configurations
         */
        ArrayList<HopperConfig> hoppers;
        /**
         * List of sack configurations
         */
        ArrayList<SackConfig> sacks;
        /**
         * List of turntable configurations
         */
        ArrayList<TurntableConfig> turntables;
        /**
         * List of present configurations
         */
        ArrayList<PresentConfig> presents;
        /**
         * Defines how long the machine should run, in seconds
         */
        int timer;

        MachineConfig(ArrayList<BeltConfig> belts, ArrayList<HopperConfig> hoppers, ArrayList<SackConfig> sacks, ArrayList<TurntableConfig> turntables, ArrayList<PresentConfig> presents, int timer) {
            this.belts = belts;
            this.hoppers = hoppers;
            this.sacks = sacks;
            this.turntables = turntables;
            this.presents = presents;
            this.timer = timer;
        }
    }

    // Holding variables to be filled as config file is parsed
    ArrayList<BeltConfig> belts = new ArrayList<>();
    ArrayList<HopperConfig> hoppers = new ArrayList<>();
    ArrayList<SackConfig> sacks = new ArrayList<>();
    ArrayList<TurntableConfig> turntables = new ArrayList<>();
    ArrayList<PresentConfig> presents = new ArrayList<>();
    int timer;

    /**
     * Reads the predefined configuration file and returns the parsed
     * configuration
     *
     * @return Parsed configuration
     */
    public MachineConfig readFile() {

        String currentPath = System.getProperty("user.dir");
        String configPath = "\\src\\assignment\\";
        String filePath = currentPath + configPath + configName;

        System.out.println("Reading config from: " + filePath);

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                // Skip any blank lines
                if (line.equals("")) {
                    line = br.readLine();
                }

                // Parse belts
                if (line.equals("BELTS")) {
                    // Skip any blank lines
                    do {
                        line = br.readLine();
                    } while (line.equals(""));

                    // Get item count
                    int count = Integer.parseInt(line);

                    for (int i = 0; i < count; i++) {

                        // Skip any blank lines
                        do {
                            line = br.readLine();
                        } while (line.equals(""));

                        // Get ID, length and destinations
                        int id = Integer.parseInt(line.substring(0, 1));
                        int length = Integer.parseInt(line.substring(9, 10));
                        String[] s_destinations = line.substring(24).split(" ");
                        int[] i_destinations = new int[s_destinations.length];
                        for (int j = 0; j < s_destinations.length; j++) {
                            i_destinations[j] = Integer.parseInt(s_destinations[j]);
                        }
                        // Create config object
                        belts.add(new BeltConfig(id, length, i_destinations));
                    }
                }

                if (line.equals("HOPPERS")) {
                    // Skip any blank lines
                    do {
                        line = br.readLine();
                    } while (line.equals(""));

                    // Get item count
                    int count = Integer.parseInt(line);

                    for (int i = 0; i < count; i++) {

                        // Skip any blank lines
                        do {
                            line = br.readLine();
                        } while (line.equals(""));

                        // Get ID, length and destinations
                        int id = Integer.parseInt(line.substring(0, 1));
                        int belt = Integer.parseInt(line.substring(7, 8));
                        int capacity = Integer.parseInt(line.substring(18, 20));
                        int speed = Integer.parseInt(line.substring(27));

                        // Create config object
                        hoppers.add(new HopperConfig(id, belt, capacity, speed));
                    }
                }

                if (line.equals("SACKS")) {
                    // Skip any blank lines
                    do {
                        line = br.readLine();
                    } while (line.equals(""));

                    // Get item count
                    int count = Integer.parseInt(line);

                    for (int i = 0; i < count; i++) {

                        // Skip any blank lines
                        do {
                            line = br.readLine();
                        } while (line.equals(""));

                        // Get ID, length and destinations
                        int id = Integer.parseInt(line.substring(0, 1));
                        int capacity = Integer.parseInt(line.substring(11, 13));
                        String age = line.substring(18);

                        // Create config object
                        sacks.add(new SackConfig(id, capacity, age));
                    }
                }
                if (line.equals("TURNTABLES")) {
                    // Skip any blank lines
                    do {
                        line = br.readLine();
                    } while (line.equals(""));

                    // Get item count
                    int count = Integer.parseInt(line);

                    for (int i = 0; i < count; i++) {

                        // Skip any blank lines
                        do {
                            line = br.readLine();
                        } while (line.equals(""));

                        // Get ID, length and destinations
                        String id = line.substring(0, 1);
                        String north = line.substring(4, 8);
                        String east = line.substring(11, 15);
                        String south = line.substring(18, 22);
                        String west = line.substring(25, 29);

                        // Create config object
                        turntables.add(new TurntableConfig(id, north, east, south, west));
                    }
                }
                if (line.startsWith("PRESENTS")) {

                    int id = Integer.parseInt(line.substring(9));
                    ArrayList<String> ages = new ArrayList<>();

                    do {
                        line = br.readLine();
                    } while (line.equals(""));

                    // Get item count
                    int count = Integer.parseInt(line);

                    for (int i = 0; i < count; i++) {

                        // Skip any blank lines
                        do {
                            line = br.readLine();
                        } while (line.equals(""));

                        // Get params
                        ages.add(line);

                        // Create config object
                    }
                    String[] str_ages = new String[ages.size()];
                    str_ages = ages.toArray(str_ages);
                    presents.add(new PresentConfig(id, str_ages));
                }

                if (line.startsWith("TIMER")) {
                    timer = Integer.parseInt(line.substring(6));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new MachineConfig(belts, hoppers, sacks, turntables, presents, timer);
    }
}
