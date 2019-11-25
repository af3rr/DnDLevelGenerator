package model;

import dnd.exceptions.NotProtectedException;
import dnd.exceptions.UnusualShapeException;
import dnd.models.ChamberContents;
import dnd.models.ChamberShape;

import dnd.models.Exit;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Treasure;
import dnd.die.Die;

import java.util.ArrayList;

public class Chamber extends Space {
    private ChamberContents contents;
    private ChamberShape shape;
    private ArrayList<Door> doors;
    private ArrayList<Treasure> treasure;
    private ArrayList<Monster> monsters;
    private Stairs stairs;
    private int width;
    private int length;
    private int area;
    private Door entrance;

    /** Constructor for the Chamber object. */
    public Chamber() {
        this(new Door());
    }

    /** Constructor for the Chamber class.
     * @param door The entrance to the Chamber
     */
    public Chamber(Door door) {
        doors = new ArrayList<>();
        treasure = new ArrayList<>();    
        monsters = new ArrayList<>();
        
        contents = new ChamberContents();
        contents.chooseContents(Die.d20());

        shape = ChamberShape.selectChamberShape(Die.d20());

        entrance = door;
        entrance.setSpaceTwo(this);

        /* parseContents(); */
        parseShape();
    }

    /** Get the entrance to the Chamber.
     * @return A Door object representing the entrance to the Chamber.
     */
    protected Door getStartDoor() {
        return entrance;
    }

    void parseContents() {
        if (containsContent("monster")) {
            addMonster(new Monster());
        }

        if (containsContent("treasure")) {
            addTreasure(new Treasure());
        }

        if (containsContent("stairs")) {
            addStairs(new Stairs());
        }
    }

    private void parseShape() {
        getShapeDimensions();
        area = shape.getArea();
        doors.clear();

        for (Exit exit : shape.getExits()) {
            setDoor(new Door(exit));
        }
    }

    private void getShapeDimensions() {
        try {
            width = shape.getWidth();
            length = shape.getLength();

        } catch (UnusualShapeException e) {
            width = 0;
            length = 0;
        }
    }

    protected boolean containsContent(String item) {
        return contents.getDescription().toLowerCase().contains(item);
    }

    /** Get the Chamber's contents object.
     * @return the ChamberContents object associated with the Chamber
     */
    public ChamberContents getContents() {
        return contents;
    }

    /** Set the shape of the Chamber and update Chamber attributes for the new shape.
     * @param newShape The new shape for the Chamber
     */
    public void setShape(ChamberShape newShape) {
        shape = newShape;
        parseShape();
    }

    /** Get the Chamber's shape object.
     * @return The ChamberShape object associated with the Chamber.
     */
    public ChamberShape getShape() {
        return shape;
    }

    /** Get all of the doors in the Chamaber.
     * @return An ArrayList of the doors in the Chamber
     */
    @Override
    public ArrayList<Door> getDoors() {
        return new ArrayList<>(doors);
    }

    public void addStairs(Stairs newStairs) {
        stairs = newStairs;
        stairs.setType(Die.d20());
    }

    /** Checks if the Chamber has stairs.
     * @return A boolean value representing whether or not the Chamber has stairs
     */
    public boolean hasStairs() {
        return !(stairs == null);
    }

    /** Get the Chamber's stairs object.
     * @return The Stairs object associated with the Chamber.
     */
    public Stairs getStairs() {
        return stairs;
    }

    public void addMonster(Monster newMonster) {
        monsters.add(newMonster);
    }

    /** Check if the Chamber has  a monster.
     * @return A boolean representing whether or not the Chamber has atleast one monster.
     */
    public boolean hasMonsters() {
        return (monsters.size() > 0);
    }

    /** Get the list of monsters in the Chamber.
     * @return The ArrayList of monsters in the Chamber.
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public void addTreasure(Treasure newTreasure) {
        treasure.add(newTreasure);
    }

    /** Check if the Chamber has treasure.
     * @return A boolean value representing whether or not the Chamber has treasure.
     */
    public boolean hasTreasure() {
        return (treasure.size() > 0);
    }

    /** Get the list of trasure in the Chamber.
     * @return The ArrayList of treasure in the Chamber
     */
    public ArrayList<Treasure> getTreasure() {
        return treasure;
    }

    /** Get a String representing the treasure's protection.
     * @return A String representing the treasure's protection
     */
    public String getTreasureProtection(int i) {
        try {
            return treasure.get(i).getProtection();
        } catch (NotProtectedException e) {
            return "none";
        }
    }

    /** Get a String representing the dimensions of the Chamber.
     * @return A String representing the dimensions of the Chamber
     */
    public String getChamberDimensions() {
        if (length + width == 0) {
            return "?x?";
        } else {
            return String.format("%dx%d", length, width);
        }
    }

    /** Get the number of doors in the Chamber.
     * @return An int representing the number of doors in the Chamber
     */
    public int numDoors() {
        return doors.size();
    }

    /** Add a door to the Chamber.
     * @param door The door to be added to the Chamber
     */
    @Override
    public void setDoor(Door door) {
        doors.add(door);
        door.setSpaceOne(this);
    }

    /** Remove a known Door from the Chamber.
     * @param door The door to be removed from the Chamber
     */
    public void removeDoor(Door door) {
        doors.remove(door);
    }

    /** Change the Chamber's shape until it has atleast one door. */
    public void forceContinue() {
        while (shape.getNumExits() == 0) {
            shape.setNumExits();
        }

        parseShape();
    }

    /** Remove all the doors in the Chamber. */
    public void removeDoors() {
        shape.setNumExits(0);
        doors.clear();
    }
}
