package model;

import database.DBConnection;
import dnd.die.Die;
import dnd.models.ChamberContents;
import dnd.models.Stairs;
import dnd.models.Treasure;
import control.Layouts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DefaultChamber extends Chamber {
    private ChamberContents contents;
    private ArrayList<Door> doors;
    private HashMap<Integer, Treasure> treasureMap;
    private ArrayList<Integer> monsters;
    private String[] layout;
    private int height;
    private int width;
    private Random rand;
    private int type;

    public DefaultChamber() {
        this(DefaultChamber.randomType());
    }

    public DefaultChamber(Door door) {
        this(door, DefaultChamber.randomType());
    }

    public DefaultChamber(int t) {
        this(new Door(), t);
    }

    public DefaultChamber(Door door, int t) {
        treasureMap = new HashMap<>();
        monsters = new ArrayList<>();
        doors = new ArrayList<>();
        rand = new Random();

        type = t;
        setLayout(type);
        setWidth(Layouts.getWidth(layout));
        setHeight(Layouts.getHeight(layout));
        addDoors();

        contents = new ChamberContents();
        contents.chooseContents(Die.d20());
        parseContents();
    }

    private static int randomType() {
        Random rand = new Random();
        return (rand.nextInt(10) + 1);
    }

    private String cell(int r, int c) {
        return String.format("%c%c", layout[r].charAt(c * 2), layout[r].charAt((c * 2)+1));
    }

    protected boolean containsContent(String item) {
        return contents.getDescription().toLowerCase().contains(item);
    }

    void parseContents() {
        if (containsContent("monster")) {
            int mType = rand.nextInt(3) + 1;
            monsters.add(mType);
            addToLayout("M" + mType);
        }

        if (containsContent("treasure")) {
            addToLayout("TT");
        }

        if (containsContent("stairs")) {
            addStairs(new Stairs());
        }
    }

    public int addToLayout(String code, int... givenType) {
        int validCell;
        int r;
        int c;

        do {
            r = rand.nextInt(height);
            c = rand.nextInt(width);
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
            return (r > 0) && cell(r-1, c).equals("##");
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

    private void setWidth(int w) {
        width = w;
    }

    private void setHeight(int h) {
        height = h;
    }

    private void setLayout(int i) {
        layout = Layouts.chamber(i);
    }

    public void setLayout(String[] newLayout) {
        layout = newLayout;
    }

    public String[] getLayout() {
        return layout;
    }

    public String getDescription() {
        String description = Layouts.getDescription(type);
        description += "\nTreasure: ";
        description += treasureMap.isEmpty() ? "None\n" : "\n";

        for (Treasure treasure : treasureMap.values()) {
            description += "  " + treasure.getDescription() + "\n";
        }

        return description;
    }

    private void addDoors() {
        int numDoors = Layouts.numDoors(layout);

        for (int i = 0; i < numDoors; i++) {
            doors.add(new Door());
        }
    }

    public int numDoors() {
        return doors.size();
    }

    @Override
    public ArrayList<Door> getDoors(){
        return new ArrayList<>(doors);
    }

    public HashMap<Integer, Treasure> getTreasureMap(){
        return treasureMap;
    }
}
