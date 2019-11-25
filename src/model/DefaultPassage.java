package model;

import dnd.die.Die;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Treasure;
import gui.Layouts;

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
        this(new Door());
    }

    public DefaultPassage(Door door) {
        this(door, DefaultPassage.randomType());
    }

    public DefaultPassage(Door door, int i) {
        sectionList = new ArrayList<>();
        treasureMap = new HashMap<>();
        doors = new ArrayList<>();
        rand = new Random();
        startDoor = door;

        doors.add(null);
        doors.add(null);

        type = i;
        setLayout(type);
        parseLayout();
    }

    public static int randomType() {
        Random rand = new Random();
        return (rand.nextInt(5) + 1);
    }

    private String cell(int r, int c) {
        return String.format("%c%c", layout[r].charAt(c * 2), layout[r].charAt((c * 2)+1));
    }

    public void parseLayout() {
        int[] sectionSeq = Layouts.sectionSequence(type);

        for (int i = 0; i < sectionSeq.length; i++) {
            addPassageSection(new PassageSection(true, sectionSeq[i]));
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

    boolean validPlacement(int r, int c) {
        if (cell(r, c).equals("##")) {
            if (r > 0 && !cell(r-1, c).contains("D")) {
                return true;
            }
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

    public void setLayout(int i) {
        layout = Layouts.passage(i);
    }

    public String[] getLayout() {
        return layout;
    }

    public void setLayout(String[] newLayout) {
        layout = newLayout;
    }
    
    public void setType(int i) {
        type = i;
    }

    public int getLength() {
        return sectionList.size();
    }

    @Override
    public ArrayList<Door> getDoors(){
        return new ArrayList<>(doors);
    }

    public int getArea() {
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
        StringBuilder description = new StringBuilder();

        description.append(String.format("Area: %d\n", getArea()));
        description.append(String.format("Length: %d\n", getLength()));
        description.append(String.format("Doors: %d\n", doors.size()));

        /* for (PassageSection section : sectionList) {
            description.append(String.format(" - %s\n", section.getDescription()));
        } */

        return description.toString();
    }
}
