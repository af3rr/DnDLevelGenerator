package model;

import dnd.models.Monster;

import java.util.ArrayList;
import java.util.HashMap;

public class Passage extends Space {
    private ArrayList<PassageSection> sectionList;
    private HashMap<Door, PassageSection> doorMap;

    /** Constructor for the Passage Class.
     */
    public Passage() {
        sectionList = new ArrayList<>();
        doorMap = new HashMap<>();
    }

    /** Create a Passage from a starting door.
     * @param door The passage's starting door
     */
    public void createPassage(Door door) {
        PassageSection section = new PassageSection(door.getOrigin());
        addPassageSection(section);
        door.setSpaceTwo(this);
        setDoor(door);

        do {
            section = new PassageSection();
            addPassageSection(section);

            if (section.hasDoor()) {
                setDoor(section.getDoor());
            }

            if (!section.continues()) {
                if (numDoors() < 2) {
                    section.forceContinue();

                    if (section.hasDoor()) {
                        setDoor(section.getDoor());
                    }
                } else {
                    return;
                }
            }

        } while (length() < 10);

        addPassageSection(new PassageSection("Dead End"));

        return;
    }

    /** Create a Passage of length 2 from a starting door.
     * @param door The passage's starting door
     */
    public void createShortPassage(Door door) {
        PassageSection section;

        section = new PassageSection(door.getOrigin());
        addPassageSection(section);
        door.setSpaceTwo(this);
        setDoor(door);

        section = new PassageSection(5);
        addPassageSection(section);
        setDoor(door);
    }

    /** Get the door in the ith PassageSection of the Passage.
     * @param i The index of the PassageSection in the Passage
     * @return The door in the PassageSection specified by i
     */
    public Door getDoor(int i) {
        PassageSection section = sectionList.get(i);

        if (section.hasDoor()) {
            return section.getDoor();
        }

        return null;
    }

    /** Get all of the doors in a space.
     * @return An ArrayList of the doors in the space
     */
    @Override
    public ArrayList<Door> getDoors() {
        return new ArrayList<>(doorMap.keySet());
    }

    /** Add a monster to a PassageSection in the Passage.
     * @param monster The monster to be added
     * @param i The index of the PassageSection for the monster to be added to
     */
    public void addMonster(Monster monster, int i) {
        sectionList.get(i).addMonster(monster);
    }

    /** Get the monster from a PassageSection.
     * @param i The index of the PassageSection that contains the monster
     * @return The monster in the ith PassageSection of the Passage
     */
    public Monster getMonster(int i) {
        PassageSection section = sectionList.get(i);

        if (section.hasMonster()) {
            return section.getMonster();
        }

        return null;
    }

    /** Get a PassageSection from the Passage.
     * @param i The index of the PassageSection to get`
     * @return The ith PassageSection of the Passage
     */
    public PassageSection getSection(int i) {
        return sectionList.get(i);
    }

    /** Get the last PassageSection in the Passage.
     * @return The last PassageSection in the Passage
     */
    public PassageSection getLastSection() {
        if (length() == 0) {
            return null;
        }

        return sectionList.get(length() - 1);
    }


    /** Get all the PassageSections in the Passage.
     * @return An ArrayList of all the PassageSections in the Passage
     */
    public ArrayList<PassageSection> getSections() {
        return sectionList;
    }

    /** Add a PassageSection to the end of the Passage.
     * @param newSection The PassageSection to be added to the Passage.
     */
    public void addPassageSection(PassageSection newSection) {
        sectionList.add(newSection);
        newSection.setPassage(this);
    }

    /** Get the length of the Passage.
     * @return An int representing the length of the Passage
     */
    public int length() {
        return sectionList.size();
    }

    /** Get the number of doors in the Passage.
     * @return An int representing the number of doors in the Passage
     */
    public int numDoors() {
        return doorMap.size();
    }

    /** Add a door to the space.
     * @param theDoor The door to be added to the space
     */

    @Override
    public void setDoor(Door theDoor) {
        PassageSection currSection;

        currSection = sectionList.get(sectionList.size() - 1);
        doorMap.put(theDoor, currSection);

        currSection.setDoor(theDoor);
    }

    /** Remove the door from each PassageSection and set the last as a dead end. */
    public void deadEndPassage() {
        PassageSection section;

        for (int i = 1; i < length(); i++) {
            section = getSection(i);

            if (section.hasDoor()) {
                section.getDoor().clearSpaceTwo();
                doorMap.remove(section.getDoor());

                if (section.contains("end")) {
                    section.setDescription(19);
                } else {
                    section.setDescription(0);
                }
            }
        }
    }

    /** Get a description of the space.
     * @return A String representing a description of the Space
     */
    public String getDescription() {
        StringBuilder description = new StringBuilder();

        for (PassageSection section : sectionList) {
            description.append(String.format("%s\n", section.getDescription()));

            if (section.hasMonster()) {
                description.append(String.format(", %s", section.getMonster().getDescription()));
            }

            if (section.hasStairs()) {
                description.append(String.format(", %s", section.getStairs().getDescription()));
            }
        }

        return description.toString();
    }
}
