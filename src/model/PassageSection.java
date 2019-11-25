package model;

import dnd.die.Die;
import dnd.models.Monster;
import dnd.models.Stairs;

public class PassageSection implements java.io.Serializable {
    private static final long serialversionUID = 184543754L;
    private String description;
    private Passage passage;
    private Door door;
    private Monster monster;
    private Stairs stairs;
    private boolean isDefault;

    /** Constructor for the PassageSection object.
     * @param roll Set the description for the passage (optional)
     */
    public PassageSection(int... roll) {
        this(false, roll);
    }

    /** Constructor for the PassageSection object.
     * @param roll Set the description for the passage (optional)
     * @param defaultStatus Set whether the PassageSection connects Chambers or DefaultChambers
     */
    public PassageSection(boolean defaultStatus, int... roll) {
        setDefault(defaultStatus);
        
        if (roll.length == 1) {
            setDescription(roll[0]);
        } else {
            setDescription();
        }
    }

    /** Constructor for the PassageSection object.
     * @param newDescription Provide a description for the PassageSection
     * @param defaultStatus Indicate whether this Passage connects Default Spaces
     */
    public PassageSection(String newDescription, boolean defaultStatus) {
        setDescription(newDescription);
        setDefault(defaultStatus);
        parseDescription();
    }

    /** Constructor for the PassageSection object.
     * @param newDescription Provide a description for the PassageSection
     */
    public PassageSection(String newDescription) {
        this(newDescription, false);
    }

    private void parseDescription() {
        if (contains("archway") || contains("door")) {
            if (!hasDoor()) {
                Door newDoor = new Door();
                newDoor.setArchway(contains("archway"));
                setDoor(newDoor);

                if (belongsToPassage()) {
                    if (isDefault) {
                        door.setSpaces(passage, new DefaultChamber(getDoor()));
                    } else {
                        door.setSpaces(passage, new Chamber(getDoor()));
                    }
                } else {
                    if (isDefault) {
                        door.setSpaceTwo(new DefaultChamber(getDoor()));
                    } else {
                        door.setSpaceTwo(new Chamber(getDoor()));
                    }
                }
            }
        } else {
            removeDoor();
        }

        if (contains("stairs")) {
            addStairs(new Stairs());
        } else {
            removeStairs();
        }

        if (contains("monster")) {
            addMonster(new Monster());
        } else {
            removeMonster();
        }
    }

    /** Check if the description contains a given sequence of characters.
     * @param item The String to be looked for in the description
     * @return A boolean representing whether item is contained by the description
     */
    public boolean contains(String item) {
        return description.toLowerCase().contains(item);
    }

    /** Checks if the Passage section comes to an end.
     * @return A boolean values representing whether or not the passage continues
     */
    public boolean continues() {
        return !contains("end");
    }

    /** Force the passage to not come to any sort of end. */
    public void forceContinue() {
        removeDoor();

        while (!continues()) {
            setDescription();
        }

        parseDescription();
    }

    /** Add a door to the PassageSection.
     * @param newDoor The door to be added to the PassageSection
     */
    public void setDoor(Door newDoor) {
        door = newDoor;
    }

    /** Check if the PassageSection has a door.
     * @return A boolean representing whether or not the PassageSection has a door
     */
    public boolean hasDoor() {
        return !(door == null);
    }

    /** Get the door object for the PassageSection.
     * @return The door associated with the PassageSection
     */
    public Door getDoor() {
        return door;
    }

    /** Set the door to null. */
    public void removeDoor() {
        door = null;
    }

    /** Add a monster to the PassageSection.
     * @param newMonster The monster to be added to the PassageSection
     */
    public void addMonster(Monster newMonster) {
        monster = newMonster;
    }

    /** Determine whether or not the PassageSection has a monster.
     * @return A boolean value representing wether or not the PassageSection has a monster
     */
    public boolean hasMonster() {
        return !(monster == null);
    }

    /** Get the Monster object associated with the PassageSection.
     * @return The Monster object associated with the PassageSection
     */
    public Monster getMonster() {
        return monster;
    }

    /** Set the Monster object for the PassageSection to null. */
    public void removeMonster() {
        monster = null;
    }

    /** Add stairs to the PassageSection.
     * @param newStairs The Stairs object to be added to the PassageSection
     */
    public void addStairs(Stairs newStairs) {
        stairs = newStairs;
    }

    /** Check if the PassageSection has stairs.
     * @return A boolean value representing whether or not the PassageSection has stairs
     */
    public boolean hasStairs() {
        return !(stairs == null);
    }

    /** Get the Stairs object associated with the PassageSection.
     * @return The Stairs object associated with the PassageSection
     */
    public Stairs getStairs() {
        return stairs;
    }

    /** Set the stairs object for the PassageSection to null. */
    public void removeStairs() {
        stairs = null;
    }

    /** Check if the PassageSection has been associated with a Passage object.
     * @return A boolean representing whether or not the PassageSection belongs to a Passage
     */
    public boolean belongsToPassage() {
        return !(passage == null);
    }

    /** Set the Passage that the PassageSection belongs to.
     * @param newPassage The Passage that the PassageSection is a part of
     */
    public void setPassage(Passage newPassage) {
        passage = newPassage;

        if (hasDoor()) {
            door.setSpaceOne(passage);
        }
    }

    public void setDefault(boolean defaultStatus) {
        isDefault = defaultStatus;
    }

    /** Set the description for the PassageSection.
     * @param newDescription The String representing the description for the PassageSection
     */
    public void setDescription(String newDescription) {
        description = newDescription;
        parseDescription();
    }

    /** Set the description for the PassageSection.
     * @param roll An optional value to specify the value of the description, random otherwise
     */
    public void setDescription(int... roll) {
        if (roll.length == 1) {
            description = descriptionTable(roll[0]);

        } else {
            description = descriptionTable(Die.d20());
        }

        parseDescription();
    }

    /** Gets the description for the PassageSection.
     * @return A String representing the PassageSection's description
     */
    public String getDescription() {
        return description;
    }

    /** A function that gets the description associated with an integer.
     * @param roll A value from 1-20 that maps to a description
     * @return The description associated with the roll value
     */
    private String descriptionTable(int roll) {
        if (roll >= 0 && roll <= 2) {
            return "passage goes straight for 10 ft";

        } else if (roll <= 5) {
            return "passage ends in Door to a Chamber";

        } else if (roll <= 7) {
            return "archway (door) to right (main passage continues straight for 10 ft)";

        } else if (roll <= 9) {
            return "archway (door) to left (main passage continues straight for 10 ft)";

        } else if (roll <= 11) {
            return "passage turns to left and continues for 10 ft";

        } else if (roll <= 13) {
            return "passage turns to right and continues for 10 ft";

        } else if (roll <= 16) {
            return "passage ends in archway (door) to chamber";

        } else if (roll <= 17) {
            return "Stairs, (passage continues straight for 10 ft)";

        } else if (roll <= 19) {
            return "Dead End";

        } else if (roll <= 20) {
            return "Wandering Monster (passage continues straight for 10 ft)";

        } else {
            return null;
        }
    }
}
