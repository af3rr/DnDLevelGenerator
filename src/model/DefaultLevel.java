package model;

import java.util.ArrayList;
import java.util.HashMap;

public class DefaultLevel {
    private ArrayList<DefaultChamber> chambers;
    private ArrayList<DefaultPassage> passages;
    private HashMap<DefaultChamber, ArrayList<Door>> map;

    public DefaultLevel() {
        chambers = new ArrayList<>();
        passages = new ArrayList<>();
        map = new HashMap<>();
    }
    
     /** Create 5 chambers and connect them. */
     public void create() {
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
    }

    /** Add 5 doors to the map and the chambers list. */
    public void populateMap() {
        DefaultChamber c;

        for (int i = 0; i < 4; i++) {
            c = new DefaultChamber();
            addChamber(c);
        }

        int totalDoors = getTotalDoors();

        do { // Make sure there are an event number of chambers
            c = new DefaultChamber();
        } while ((totalDoors + c.numDoors())%2 != 0);

        addChamber(c);
    }

    public void addChamber(DefaultChamber c) {
        map.put(c, c.getDoors());
        chambers.add(c);

        for (int i = 0; i < c.getLayout().length; i++)
            System.out.println(c.getLayout()[i]);

        System.out.println(c + ", " + c.numDoors());
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
        for (int min = 1; ; min++) {
            for (DefaultChamber c : chambers) {
                if (c.numDoors() == min) {
                    return c;
                }
            }
        }
    }

    /** Get the total amount of Doors between the 5 Chambers.
     * @return An integer representing the total number of Doors in the Level
     */
    public int getTotalDoors() {
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

        System.out.println(c1 + " - " + c2);
        p.setStartDoor(d1);
        d1.setSpaces(c1, p);
        d2.setSpaces(c2, p);
        p.setEndDoor(d2);
        
        map.get(c1).remove(d1);
        map.get(c2).remove(d2);
    }

    private boolean outOfDoors(DefaultChamber c) {
        return map.get(c).isEmpty();
    }

    public ArrayList<DefaultChamber> getChambers() {
        return chambers;
    }

    public ArrayList<DefaultPassage> getPassages() {
        return passages;
    }
}