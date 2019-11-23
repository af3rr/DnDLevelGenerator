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
    private HashMap<Door,PassageSection> doorMap;
    private String[] layout;
    private Door startDoor;
    private Door endDoor;
    private int type;

    public DefaultPassage() {
        this(new Door());
    }

    public DefaultPassage(Door door) {
        this(door, DefaultPassage.randomType());
    }

    public DefaultPassage(Door door, int i) {
        sectionList = new ArrayList<>();
        doorMap = new HashMap<>();
        startDoor = door;

        type = i;
        setLayout(type);
        parseLayout();
    }

    public static int randomType() {
        Random rand = new Random();
        return (rand.nextInt(5) + 1);
    }

    public void parseLayout() {

    }

    public void setLayout(int i) {
        layout = Layouts.passage(i);
    }

    public String[] getLayout() {
        return layout;
    }

    public void setType(int i) {
        type = i;
    }

    public void setEndDoor(Door door){
        startDoor = door;
    }

    public void setStartDoor(Door door){
        endDoor = door;
    }

    @Override
    public ArrayList<Door> getDoors(){
        return new ArrayList<>(doorMap.keySet());
    }

    public int getArea() {
        return Layouts.getArea(layout);
    }
}
