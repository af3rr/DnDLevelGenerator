package model;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleLevel extends Level {
    private Door startDoor;
    private ArrayList<Chamber> chambers;
    private HashMap<Chamber, ArrayList<Door>> map;

    /** Constructor for the SimpleLevel class. Sets the starting door.
     * @param door The starting door
     */
    public SimpleLevel(Door door) {
        chambers = new ArrayList<>();
        map = new HashMap<>();

        setStartDoor(door);
    }

    /** Set the start door for the chamber.
     * @param door The door to be set as the start door
     */
    @Override
    public void setStartDoor(Door door) {
        startDoor = door;
    }

    /** Create 5 chambers and connect them. */
    public void createFromDoor() {
        Chamber c1;
        Chamber c2;
        ArrayList<Door> l1;
        ArrayList<Door> l2;
        ArrayList<Chamber> chambersCpy;

        populateMap();
        chambersCpy = chambers;

        c1 = null;
        c2 = null;

        setEvenDoors(startDoor);

        while (map.size() > 1) {
            chambers = new ArrayList<>(map.keySet());

            c1 = chambers.get(0);
            chambers.remove(c1);
            l1 = map.get(c1);

            while ((l1.size() > 0) && (map.keySet().size() > 1)) {
                if (chambers.isEmpty()) {
                    chambers = new ArrayList<>(map.keySet());
                    chambers.remove(c1);
                }

                c2 = getConnectedChamber();
                l2 = map.get(c2);

                connectChambers(c1, c2);
                chambers.remove(c2);

                if (l1.isEmpty()) {
                    map.remove(c1);
                }

                if (l2.isEmpty()) {
                    map.remove(c2);
                }
            }
        }

        if (!map.isEmpty()) {
            removeExtraDoors((Chamber) map.keySet().toArray()[0]);
        }

        chambers = chambersCpy;
    }

    /** Print a level from it's start door. */
    @Override
    public void printFromDoor() {
        int chNum;

        printPassage((Passage) startDoor.getSpaceTwo(), 0);
        print(indent2("Door: " + startDoor.getDescription(), 0) + "\n");

        for (Chamber c : chambers) {
            chNum = (chambers.indexOf(c) + 1);

            print(indent2("Chamber (" + chNum + "): ", 1));
            printChamber(c, 1);

            println(indent1(String.format("Doors (%d):", c.numDoors()), 2));

            for (int i = 0; i < c.numDoors(); i++) {
                printDoor(c.getDoors().get(i), i);
            }

            println(indent1("", 2));
        }

        println(indent1("", 1));
        println(indent1("", 0));
    }

    /** Print a description of a Passage from start to end.
     * @param p The Passage to print
     * @param depth Distance from the margin
     */
    @Override
    public void printPassage(Passage p, int depth) {

        for (PassageSection s : p.getSections()) {
            println(indent1(s.getDescription(), depth));
        }

    }

    /** Print a description of a Door to the console.
     * @param d The Door to be described
     * @param i The Doors number in the Chamber it belongs to
     */
    public void printDoor(Door d, int i) {
        Door d1;

        d1 = ((Passage) d.getSpaceTwo()).getLastSection().getDoor();
        println(indent2("Door " + (i + 1) + ": " + d.getDescription(), 2));
        println(indent1("Leads to: Chamber " + (chambers.indexOf(d1.getSpaceOne()) + 1), 3));
    }

    /** Print a description of a Chamber and its contents.
     * @param c The Chamber to print
     * @param depth Distance from the margin
     */
    @Override
    public void printChamber(Chamber c, int depth) {
        printChamberShape(c, depth);
        printChamberContents(c.getContents(), depth);

        if (c.hasTreasure()) {
            printTreasure(c, depth + 1);
            println(indent1("", depth + 2));
        }

        if (c.hasMonsters()) {
            printMonsters(c, depth + 1);
            println(indent1("", depth + 2));
        }

        if (c.hasStairs()) {
            printStairs(c.getStairs(), depth + 1);
            println(indent1("", depth + 2));
        }
    }

    /** Add 5 doors to the map and the chambers list. */
    public void populateMap() {
        Chamber c;

        do {
            map.clear();
            chambers.clear();

            for (int i = 0; i < 5; i++) {
                do {
                    c = new Chamber();
                } while (c.numDoors() == 0);

                map.put(c, c.getDoors());
                chambers.add(c);
            }

        } while (getTotalDoors() < 6);
    }

    /** Get a Chamber with more than 1 Door.
     * @return A Chamber with more than 1 Door
     */
    private Chamber getConnectedChamber() {
        for (Chamber c : chambers) {
            if (map.get(c).size() > 1) {
                return c;
            }
        }

        return getLowestChamber();
    }

    /** Get the Chamber with the fewest Doors.
     * @return The Chamber with the fewest Doors
     */
    private Chamber getLowestChamber() {
        for (int min = 1; ; min++) {
            for (Chamber c : chambers) {
                if (c.numDoors() == min) {
                    return c;
                }
            }
        }
    }

    /** Get the total amount of Doors between the 5 Chambers.
     * @return An integer representing the total number of Doors in the Level
     */
    private int getTotalDoors() {
        int sum = 0;

        for (Chamber c : chambers) {
            sum += c.numDoors();
        }

        return sum;
    }

    /** Ensure there are an even number of Doors and an initial connection to the Level.
     * @param start The start Door for the Level
     */
    private void setEvenDoors(Door start) {
        Passage p = new Passage();
        Chamber c;
        Door d;

        p.createShortPassage(start);

        if ((getTotalDoors() % 2) == 0) {
            c = getConnectedChamber();
            d = c.getDoors().get(0);

        } else {
            c = getLowestChamber();
            d = new Door();
        }

        p.getSection(1).setDoor(c.getStartDoor());
        map.get(c).remove(d);
        c.removeDoor(d);
    }

    private void connectChambers(Chamber c1, Chamber c2) {
        Door d1;
        Door d2;
        Passage p;

        d1 = map.get(c1).get(0);
        d2 = map.get(c2).get(0);
        p = new Passage();

        p.createShortPassage(d1);
        p.getSection(1).setDoor(c2.getStartDoor());
        c2.getStartDoor().setSpaces(p, c2);

        p.createShortPassage(d2);
        p.getSection(1).setDoor(c1.getStartDoor());

        map.get(c1).remove(d1);
        map.get(c2).remove(d2);
    }

    private void removeExtraDoors(Chamber c) {
        for (Door d : c.getDoors()) {
            if (d.getSpaceTwo() == null) {
                c.removeDoor(d);
            }
        }
    }
}
