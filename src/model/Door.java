package model;

import dnd.models.Exit;
import dnd.models.Trap;
import dnd.die.Die;
import java.util.ArrayList;

public class Door implements java.io.Serializable {
    private static final long serialversionUID = 579135846L;

    private ArrayList<Space> spaces;
    private boolean trapped;
    private boolean locked;
    private boolean open;
    private boolean archway;
    private Trap trap;
    private Exit exit;
    private String description;

    /** Constructor for the door class. Creates new exit. */
    public Door() {
        this(new Exit());
    }

    /** Constructor for the door class.
     * @param newExit The Exit object associated with the Door
     */
    public Door(Exit newExit) {
        spaces = new ArrayList<>();
        spaces.add(null);
        spaces.add(null);

        trap = new Trap();

        setExit(newExit);
        setDescription();
    }

    private void setDescription() {
        setTrappedByChance();
        setLockedByChance();
        setArchwayByChance();
    }

    /** Set the space on one side of the door.
     * @param space The space to be set
     */
    public void setSpaceOne(Space space) {
        spaces.set(0, space);
    }

    /** Set the space on the other side of the door.
     * @param space The space to be set
     */
    public void setSpaceTwo(Space space) {
        spaces.set(1, space);
    }

    /** Set the spaces on both sides of the door.
     * @param spaceOne The space to be set in spaces[0]
     * @param spaceTwo The space to be set in spaces[1]
     */
    public void setSpaces(Space spaceOne, Space spaceTwo) {
        spaces.set(0, spaceOne);
        spaces.set(1, spaceTwo);
    }

    /** Set the second space to null (i.e. disconnect succeeding spaces). */
    public void clearSpaceTwo() {
        spaces.set(1, null);
    }

    /** Get the list of spaces. Always length 2.
     * @return An ArrayList of the Spaces
   . */
    public ArrayList<Space> getSpaces() {
        return spaces;
    }

    /** Get the space on one side of the door.
     * @return the space in spaces[0];
   . */
    public Space getSpaceOne() {
        return spaces.get(0);
    }

    /** Get the space on the other side of the door.
     * @return the space in spaces[1];
   . */
    public Space getSpaceTwo() {
        return spaces.get(1);
    }

    private void setTrappedByChance() {
        setTrapped((int) (Math.random() * 20) == 1);
    }

    private void setLockedByChance() {
        setLocked((int) (Math.random() * 6) != 1);
    }

    private void setArchwayByChance() {
        setArchway((int) (Math.random() * 10) == 1);
    }

    /** Set the value of trapped if the door isn't an archway.
     * @param flag The value to set trapped to
     * @param roll An optional value to set the trap description
     */
    public void setTrapped(boolean flag, int... roll) {
        if (!isArchway()) {
            trapped = flag;

            if (roll.length == 1) {
                trap.chooseTrap(roll[0]);
            } else {
                trap.chooseTrap(Die.d20());
            }
        } else {
            trapped = false;
        }
    }

    /** Get a description of the trap if their is one, null otherwise.
     * @return A String representing the trap description
     */
    public String getTrapDescription() {
        if (trapped) {
            return trap.getDescription();
        } else {
            return null;
        }
    }

    /** Set the value of locked if the door isn't an archway.
     * @param flag The value to set locked to
   . */
    public void setLocked(boolean flag) {
        if (!isArchway()) {
            locked = flag;
        } else {
            locked = false;
        }

        if (locked) {
            setOpen(false);
        }
     }

    /** Set the door as open if not locked.
     * @param flag The value to set open to
     */
    public void setOpen(boolean flag) {
        if (isLocked()) {
            open = false;
        } else if (isArchway()) {
            open = true;
        } else {
            open = flag;
        }
    }

    /** Set the door as an archway. This can't be blocked.
     * @param flag The value to set archway to
     */
    public void setArchway(boolean flag) {
        archway = flag;

        if (archway) {
            setTrapped(false);
            setLocked(false);
            setOpen(true);
        }
    }

    private void setExit(Exit newExit) {
        exit = newExit;
    }

    /** Get the value of trapped.
     * @return A boolean representing whether or not the door is trapped
     */
    public boolean isTrapped() {
        return trapped;
    }

    /** Get the value of locked.
     * @return A boolean representing whether or not the door is locked
     */
    public boolean isLocked() {
        return locked;
    }

    /** Get the value of open.
     * @return A boolean representing whether or not the door is open
     */
    public boolean isOpen() {
        return open;
    }

    /** Get the value of archway.
     * @return A boolean representing whether or not the door is an archway
     */
    public boolean isArchway() {
        return archway;
    }

    /** Get a description of the door.
     * @return A String representing a description of the door
     */
    public String getDescription() {
        /* description = String.format("%s %s, ", exit.getLocation(), exit.getDirection()); */
        description = "";
        
        if (isArchway()) {
            description += "archway, not trapped or locked";

        } else if (isTrapped()) {
            description += isLocked() ? "trapped, locked" : "trapped, open";

        } else if (isLocked()) {
            description += "locked, no trap";

        } else {
            description += "open, no trap";
        }

        return description;
    }

    /** Get a description of what is in spaceOne of the the door as if it
     * has just been entered.
     * @return A description of what is in the door's first space
     */
    public String getOrigin() {
        StringBuilder originDesc = new StringBuilder();
        Space origin = getSpaceOne();

        originDesc.append("passage starts with ");

        if (origin == null) { // initial door
            originDesc.append("archway (door)");

        } else { // all other doors
            if (isArchway()) {
                originDesc.append("archway (door) to ");
            } else {
                originDesc.append("Door to ");
            }

            if (origin instanceof Passage) {
                originDesc.append("passage");
            } else {
                originDesc.append("chamber");
            }
        }

        originDesc.append(" (entrance)");

        return originDesc.toString();
    }
}
