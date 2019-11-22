package model;

import java.util.ArrayList;

public class ComplexLevel extends Level {
    private Door startDoor;
    private ArrayList<Chamber> chambers;
    private ArrayList<Passage> passages;

    /** Constructor for the ComplexLevel class. Sets the starting door.
     * @param door The starting door
     */
    public ComplexLevel(Door door) {
        chambers = new ArrayList<>();
        passages = new ArrayList<>();

        setStartDoor(door);
    }

    /** Set the start door for the chamber.
     * @param door The door to be set as the start door
     */
    @Override
    public void setStartDoor(Door door) {
        startDoor = door;
    }

     /** A recursive method to create a Level from a starting door.
     * @param door The door to start creating from
     * @param depth The number of times a new door has been entered
     */
    public void createFromDoor(Door door, int depth) {
        Chamber chamber;
        Passage passage = new Passage();
        Space nextSpace = door.getSpaceTwo();

        if (chambers.size() == 5 || depth == 3) {
            passage.createPassage(door);
            passage.deadEndPassage();

            if (nextSpace instanceof Chamber) {
                Door cDoor = ((Chamber) nextSpace).getStartDoor();
                removeChamberFromDoor(cDoor, passage);
                return;
            }
        }

        if (nextSpace == null) {
            passage.createPassage(door);
            passages.add(passage);

            for (PassageSection section : passage.getSections()) {
                if (section.hasDoor() && !section.getDoor().equals(startDoor)) {
                    createFromDoor(section.getDoor(), depth);
                }
            }

            if (depth == 0 && chambers.size() < 5) {
                PassageSection lastSection = passage.getLastSection();
                lastSection.setDescription(5);
                createFromDoor(lastSection.getDoor(), depth + 1);
            }

        } else if (nextSpace instanceof Chamber) {
            chamber = (Chamber) nextSpace;
            chambers.add(chamber);

            for (Door chDoor : chamber.getDoors()) {
                createFromDoor(chDoor, depth + 1);
            }

            if (depth == 0 && chambers.size() < 5) {
                Door forcedDoor = new Door();
                chamber.setDoor(forcedDoor);
                createFromDoor(forcedDoor, depth + 1);
            }
        }
    }

    public ArrayList<Chamber> getChambers() {
        return chambers;
    }

    public ArrayList<Passage> getPassages() {
        return passages;
    }

    /** Print a level from it's start door. */
    @Override
    public void printFromDoor() {
        printPassage((Passage) startDoor.getSpaceTwo(), 0);
    }

    /** Print a description of a Passage from start to end.
     * @param p The Passage to println
     * @param depth Distance from the margin
     */
    @Override
    public void printPassage(Passage p, int depth) {
        PassageSection s;

        for (int i = 0; i < p.length(); i++) {
            s = p.getSection(i);

            if (s.contains("Door to passage")) {
                depth += 1;
            }

            println(indent1(s.getDescription(), depth));

            if (s.hasDoor() && i != 0) {
                printDoor(s.getDoor(), depth);
            }
        }
    }

    /** Print a description of a Chamber and its contents.
     * @param c The Chamber to println
     * @param depth Distance from the margin
     */
    @Override
    public void printChamber(Chamber c, int depth) {
        print(indent2("Chamber: ", depth));
        printChamberShape(c, depth);
        printChamberContents(c.getContents(), depth);

        if (c.hasTreasure()) {
            printTreasure(c, depth + 1);
        }

        if (c.hasMonsters()) {
            printMonsters(c, depth + 1);
        }

        if (c.hasStairs()) {
            printStairs(c.getStairs(), depth + 1);
        }

        println(indent1(String.format("Doors (%d):", c.numDoors()), depth + 1));
        printChamberDoors(c, depth + 1);
    }

    private void printChamberDoors(Chamber c, int depth) {
        for (int i = 0; i < c.numDoors(); i++) {
            Door door = c.getDoors().get(i);
            println(indent2(String.format("Door %d: %s", i + 1, door.getDescription()), depth));
            printPassage((Passage) door.getSpaceTwo(), depth + 1);
        }
    }

    private void printDoor(Door door, int depth) {
        Space nextSpace = door.getSpaceTwo();
        println(indent2("Door: "  + door.getDescription(), depth));

        if (nextSpace instanceof Chamber) {
            printChamber((Chamber) nextSpace, depth + 1);
        } else {
            printPassage((Passage) nextSpace, depth + 1);
        }

        for (int j = 0; j < 3; j++) {
            println(indent1("", depth + (2 - j)));
        }
    }

    private void removeChamberFromDoor(Door door, Passage deadEndPass) {
        Passage origin = (Passage) door.getSpaceOne();
        origin.getLastSection().setDescription("passage ends in Door to Passage");
        door.setSpaceTwo(deadEndPass);
    }
}
