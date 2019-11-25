package model;

import dnd.die.Die;
import dnd.models.Treasure;
import control.Layouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DefaultPassage extends Passage {
    private ArrayList<PassageSection> sectionList;
    private HashMap<Integer, Treasure> treasureMap;
    private ArrayList<Door> doors;
    private String[] layout;
    private Door startDoor;
    private Door endDoor;
    private int type;
    private Random rand;

    public DefaultPassage() {
        this(new Door(), DefaultPassage.randomType());
    }

    private DefaultPassage(Door door, int t) {
        sectionList = new ArrayList<>();
        treasureMap = new HashMap<>();
        doors = new ArrayList<>();
        rand = new Random();
        startDoor = door;

        doors.add(null);
        doors.add(null);

        type = t;
        setLayout(type);
        parseLayout();
    }

    private static int randomType() {
        Random rand = new Random();
        return (rand.nextInt(5) + 1);
    }

    private String cell(int r, int c) {
        return String.format("%c%c", layout[r].charAt(c * 2), layout[r].charAt((c * 2)+1));
    }

    private void parseLayout() {
        int[] sectionSeq = Layouts.sectionSequence(type);

        for (int i : sectionSeq) {
            addPassageSection(new PassageSection(true, i));
        }
    }

    public int addToLayout(String code, int... givenType) {
        int validCell;
        int r;
        int c;

        do {
            r = rand.nextInt(8);
            c = rand.nextInt(9);
        } while (!validPlacement(r, c));

        validCell = (r * 8) + c;

        if (code.equals("TT")) {
            Treasure treasure = new Treasure();
            int type = (givenType.length > 0) ? givenType[0] : Die.percentile();
            treasure.chooseTreasure(type);
            treasureMap.put(validCell, treasure);
        }

        setCell(r, c, code);
        return validCell;
    }

    private boolean validPlacement(int r, int c) {
        if (cell(r, c).equals("##")) {
            return (r > 0) && !cell(r-1, c).contains("D");
        }
        return false;
    }

    private void setCell(int row, int col, String item) {
        String[] updated = new String[layout.length];
        StringBuilder currRow;

        col *= 2;

        for (int r = 0; r < layout.length; r++) {
            currRow = new StringBuilder(layout[r]);

            if (r == row) {
                currRow.setCharAt(col + 0, item.charAt(0));
                currRow.setCharAt(col + 1, item.charAt(1));
            }

            updated[r] = currRow.toString();
            currRow.setLength(0);
        }

        layout = updated;
    }


    public String[] getLayout() {
        return layout;
    }

    private void setLayout(int i) {
        layout = Layouts.passage(i);
    }

    public void setLayout(String[] newLayout) {
        layout = newLayout;
    }

    public int getLength() {
        return sectionList.size();
    }

    @Override
    public ArrayList<Door> getDoors(){
        return new ArrayList<>(doors);
    }

    private int getArea() {
        return Layouts.getArea(layout);
    }

    public void addPassageSection(PassageSection newSection) {
        sectionList.add(newSection);
        newSection.setPassage(this);
    }

    public void setStartDoor(Door door) {
        startDoor = door;
        doors.set(0, startDoor);
    }

    public void setEndDoor(Door door) {
        endDoor = door;
        doors.set(1, endDoor);
    }
    
    public HashMap<Integer, Treasure> getTreasureMap(){
        return treasureMap;
    }

    public String getDescription() {
        String description = "";

        description += String.format("Area: %d\n", getArea());
        description += String.format("Length: %d\n", getLength());
        description += String.format("Doors: %d\n", doors.size());

        description += "\nTreasure: ";
        description += treasureMap.isEmpty() ? "None\n" : "\n";

        for (Treasure treasure : treasureMap.values()) {
            description += "  " + treasure.getDescription() + "\n";
        }

        return description;
    }
}
