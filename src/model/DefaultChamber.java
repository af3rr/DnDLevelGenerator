package model;

import dnd.die.Die;
import dnd.models.ChamberContents;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Treasure;
import gui.Layouts;

import java.util.ArrayList;
import java.util.Random;

import database.DBMonster;

public class DefaultChamber extends Chamber {
    private Door entrance;
    private ChamberContents contents;
    private ArrayList<Door> doors;
    private ArrayList<Treasure> treasure;
    private ArrayList<Monster> monsters;
    private String[] layout;
    private Stairs stairs;
    private int height;
    private int width;
    private int area;
    private Random rand;
    int type;

    public DefaultChamber() {
        this(DefaultChamber.randomType());
    }

    public DefaultChamber(int i) {
        this(new Door(), i);
    }

    public DefaultChamber(Door door, int i) {
        treasure = new ArrayList<>();
        monsters = new ArrayList<>();
        doors = new ArrayList<>();
        rand = new Random();

        entrance = door;
        entrance.setSpaceTwo(this);

        setType(i);
        setLayout(type);
        setWidth(Layouts.getWidth(layout));
        setHeight(Layouts.getHeight(layout));
        addDoors();

        contents = new ChamberContents();
        contents.chooseContents(Die.d20());
        parseContents();
    }

    public static int randomType() {
        Random rand = new Random();
        return (rand.nextInt(10) + 1);
    }

    private String cell(int r, int c) {
        return String.format("%c%c", layout[r].charAt(c * 2), layout[r].charAt((c * 2)+1));
    }

    public void addDoors() {
        int numDoors = Layouts.numDoors(layout);

        for (int i = 0; i < numDoors; i++) {
            doors.add(new Door());
        }
    }

    void parseContents() {
        if (containsContent("monster")) {
            addToLayout(DBMonster.getRandom());
        }

        if (containsContent("treasure")) {
            addTreasure(new Treasure());
            addToLayout("TT");
        }

        if (containsContent("stairs")) {
            addStairs(new Stairs());
        }
    }

    private void addToLayout(String code) {
        int r;
        int c;

        do {
            r = rand.nextInt(height);
            c = rand.nextInt(width);
        } while (!validPlacement(r, c));

        setCell(r, c, code);
    }

    protected boolean containsContent(String item) {
        return contents.getDescription().toLowerCase().contains(item);
    }

    boolean validPlacement(int r, int c) {
        if (cell(r, c).equals("##")) {
            if (r > 0 && cell(r-1, c).equals("##")) {
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

    public void setType(int i) {
        type = i;
    }

    public void setWidth(int w) {
        width = w;
    }

    public void setHeight(int h) {
        height = h;
    }

    public int getWidth() {
        return width;   
    }

    public int getHeight() {
        return height;   
    }

    public void setLayout(int i) {
        layout = Layouts.chamber(i);
    }

    public String[] getLayout() {
        return layout;
    }

    @Override
    public ArrayList<Door> getDoors(){
        return new ArrayList<>(doors);
    }

    public int numDoors() {
        return doors.size();
    }
}
