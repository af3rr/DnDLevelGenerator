package model;

import dnd.models.ChamberContents;
import dnd.models.ChamberShape;
import dnd.models.Monster;
import dnd.models.Treasure;
import dnd.models.Stairs;


public abstract class Level {
    /** Set the start door for the chamber.
     * @param door The door to be set as the start door
     */
    public abstract void setStartDoor(Door door);

    /** Print a level from it's start door. */
    public abstract void printFromDoor();

    /** Print a description of a Passage from start to end.
     * @param p The Passage to println
     * @param depth Distance from the margin
     */
    public abstract void printPassage(Passage p, int depth);

    /** Print a description of a Chamber and its contents.
     * @param c The Chamber to println
     * @param depth Distance from the margin
     */
    public abstract void printChamber(Chamber c, int depth);

    /** A way to print strings and use less syntax.
     * @param str The String to be printed
     */
    public static final void print(String str) {
        System.out.print(str);
    }

    /** A way to print strings and use less syntax.
     * @param str The String to be printed
     */
    public static final void println(String str) {
        System.out.println(str);
    }

    /** Prepend a String with depth "|  "s.
     * @param str The String to that will have "|  " prepended
     * @param depth The number of "|  "s to prepend
     * @return The modified String
     */
    public static final String indent1(String str, int depth) {
        String b = "";

        for (int i = 0; i <= depth; i++) {
            b += "|  ";
        }

        return (b + str);
    }

    /** Prepend a string with depth "|  "s followed by a +.
     * @param str The String to that will have "|  "...+ prepended
     * @param depth The number of "|  "s to prepend
     * @return The modified String
     */
    public static final String indent2(String str, int depth) {
        return (indent1("", depth) + "+  " + str);
    }

    /** Print a description of a Chamber's ChmaberShape.
     * @param c The Chamber whose ChamberShape will be printed
     * @param depth The number of "|  "s to prepend
     */
    public static final void printChamberShape(Chamber c, int depth) {
        ChamberShape cs = c.getShape();
        StringBuilder out = new StringBuilder();

        print(cs.getShape());
        print(String.format(", %s", c.getChamberDimensions()));
        println(String.format(", Area: %d", cs.getArea()));
    }

    /** Print a description of a Chamber's ChamberContents.
     * @param cc The ChamberContents to be printed
     * @param depth The number of "|  "s to prepend
     */
    public static final void printChamberContents(ChamberContents cc, int depth) {
        println(indent1("Contents: " + cc.getDescription(), depth + 1));
    }

    /** Print a description of a Chamber's Treasure.
     * @param c The Chamber whose Treasure will be printed
     * @param depth The number of "|  "s to prepend
     */
    public static final void printTreasure(Chamber c, int depth) {
        Treasure t = c.getTreasure().get(0);

        println(indent1("Treasure:", depth));
        println(indent2(t.getDescription(), depth));
        println(indent1(String.format("Protection: %s", c.getTreasureProtection(0)), depth + 1));
        println(indent1(String.format("Container %s", t.getContainer()), depth + 1));
    }

    /** Print a description of a Chamber's Monster.
     * @param c The Chamber whose Monster will be printed
     * @param depth The number of "|  "s to prepend
     */
    public static final void printMonsters(Chamber c, int depth) {
        Monster m = c.getMonsters().get(0);

        println(indent1("Monsters:", depth));
        println(indent2(String.format("%s (%d-%d)", m.getDescription(), m.getMinNum(), m.getMaxNum()), depth));
    }

    /** Print a description of a Chamber's Stairs.
     * @param stairs The Stairs to be printed
     * @param depth The number of "|  "s to prepend
     */
    public static final void printStairs(Stairs stairs, int depth) {
        println(indent1("Stairs: " + stairs.getDescription(), depth + 1));
    }
}
