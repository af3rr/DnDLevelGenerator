package model;

import java.util.ArrayList;

public abstract class Space {

    /** Get a description of the space.
     * @return A String representing a description of the Space
     */
    public abstract String getDescription();

    /** Add a door to the space.
     * @param theDoor The door to be added to the space
     */
    public abstract void setDoor(Door theDoor);

    /** Get all of the doors in a space.
     * @return An ArrayList of the doors in the space
     */
    public abstract ArrayList<Door> getDoors();
}
