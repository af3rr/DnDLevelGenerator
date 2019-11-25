package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DefaultLevel implements java.io.Serializable {
    private static final long serialversionUID = 173836729L;
    StringBuilder e;
    
    private ArrayList<DefaultChamber> chambers;
    private ArrayList<DefaultPassage> passages;
    private ArrayList<Integer> types;
    private HashMap<DefaultChamber, ArrayList<Door>> map;

    public DefaultLevel() {
        e = new StringBuilder();
        boolean initialized;
        chambers = new ArrayList<>();
        passages = new ArrayList<>();
        types = new ArrayList<>();
        map = new HashMap<>();

        do {
            setTypes();
            map.clear();
            chambers.clear();
            passages.clear();
            initialized = initialize();
        } while (!initialized);
    }

     private boolean initialize() {
        DefaultChamber c1;
        DefaultChamber c2;
        ArrayList<Door> doorList1;
        ArrayList<Door> doorList2;
        ArrayList<DefaultChamber> chambersCpy;

        populateMap();
        chambersCpy = chambers;

        while (!map.isEmpty()) {
            chambers = new ArrayList<>(map.keySet());

            c1 = chambers.get(0);
            doorList1 = map.get(c1);
            chambers.remove(c1);

            while (!doorList1.isEmpty() && !map.keySet().isEmpty()) {
                if (chambers.isEmpty()) {
                    chambers = new ArrayList<>(map.keySet());
                    chambers.remove(c1);
                }

                c2 = getConnectedChamber();

                if (c2 == null) {
                    return false;
                }

                doorList2 = map.get(c2);

                connectChambers(c1, c2);
                chambers.remove(c2);

                if (doorList1.isEmpty()) {
                    map.remove(c1);
                }

                if (doorList2.isEmpty()) {
                    map.remove(c2);
                }
            }
        }

        chambers = chambersCpy;
        return true;
    }

    private void setTypes() {
        for (int t = 1; t <= 10; t++) {
            types.add(t);
        }
    }

    private void populateMap() {
        DefaultChamber c;
        Random r = new Random();
        int t;

        for (int i = 0; i < 4; i++) {
            t = r.nextInt(types.size()); // This is an index in the array
            c = new DefaultChamber(types.get(t));
            types.remove(t);
            addChamber(c);
        }

        int totalDoors = getTotalDoors();

        do { // Make sure there are an event number of chambers
            c = new DefaultChamber();
        } while ((totalDoors + c.numDoors())%2 != 0);

        addChamber(c);
    }

    private void addChamber(DefaultChamber c) {
        map.put(c, c.getDoors());
        chambers.add(c);
    }

    /** Get a Chamber with more than 1 Door.
     * @return A Chamber with more than 1 Door
     */
    private DefaultChamber getConnectedChamber() {
        for (DefaultChamber c : chambers) {
            if (map.get(c).size() > 1) {
                return c;
            }
        }

        return getLowestChamber();
    }

    /** Get the Chamber with the fewest Doors.
     * @return The Chamber with the fewest Doors
     */
    private DefaultChamber getLowestChamber() {
        for (int min = 1; min < 4; min++) {
            for (DefaultChamber c : chambers) {
                if (c.numDoors() == min) {
                    return c;
                }
            }
        }
        
        return null;
    }

    /** Get the total amount of Doors between the 5 Chambers.
     * @return An integer representing the total number of Doors in the Level
     */
    private int getTotalDoors() {
        int sum = 0;

        for (DefaultChamber c : chambers) {
            sum += c.numDoors();
        }

        return sum;
    }

    private void connectChambers(Chamber c1, Chamber c2) {
        Door d1 = map.get(c1).get(0);
        Door d2 = map.get(c2).get(0);
        
        DefaultPassage p = new DefaultPassage();
        passages.add(p);

        e.append(String.format("%s - %s\n", c1.toString(), c2.toString()));
        p.setStartDoor(d1);
        d1.setSpaces(c1, p);
        d2.setSpaces(c2, p);
        p.setEndDoor(d2);
        
        map.get(c1).remove(d1);
        map.get(c2).remove(d2);
    }

    public ArrayList<DefaultChamber> getChambers() {
        return new ArrayList<>(chambers);
    }

    public ArrayList<DefaultPassage> getPassages() {
        return new ArrayList<>(passages);
    }
}