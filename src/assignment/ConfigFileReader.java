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
 * @author cewalton
 * // TODO - parse TIMER, return everything to machine to build
 */
public class ConfigFileReader {

    class BeltConfig {

        int id;
        int length;
        int[] destinations;

        public BeltConfig(int id, int length, int[] destinations) {
            this.id = id;
            this.length = length;
            this.destinations = destinations;
        }
    }

    class HopperConfig {

        int id;
        int belt;
        int capacity;
        int speed;

        public HopperConfig(int id, int belt, int capacity, int speed) {
            this.id = id;
            this.capacity = capacity;
            this.speed = speed;
        }
    }

    class SackConfig {

        int id;
        int capacity;
        String age;

        public SackConfig(int id, int capacity, String age) {
            this.id = id;
            this.capacity = capacity;
            this.age = age;
        }
    }

    class TurntableConfig {

        String id;
        String north;
        String east;
        String south;
        String west;

        public TurntableConfig(String id, String north, String east, String south, String west) {
            this.id = id;
            this.north = north;
            this.east = east;
            this.south = south;
            this.west = west;
        }
    }

    class PresentConfig {
        int id;
        String[] ages;

        public PresentConfig(int id, String[] age) {
            this.id = id;
            this.ages = age;
        }
    }

    ArrayList<BeltConfig> belts = new ArrayList<BeltConfig>();
    ArrayList<HopperConfig> hoppers = new ArrayList<HopperConfig>();
    ArrayList<SackConfig> sacks = new ArrayList<SackConfig>();
    ArrayList<TurntableConfig> turntables = new ArrayList<TurntableConfig>();
    ArrayList<PresentConfig> presents = new ArrayList<PresentConfig>();
    int timer;

    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("N:\\Modules\\SoftwareEngineering\\Assignment\\src\\assignment\\config.txt"))) {
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
                    // Skip any blank lines

                    int id = Integer.parseInt(line.substring(9));
                    ArrayList<String> ages = new ArrayList<String>();
                    
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
                    presents.add(new PresentConfig(id, (String[]) ages.toArray()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
