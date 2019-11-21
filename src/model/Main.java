package model;

public class Main {
    /** main method for Main class.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        ComplexLevel level;
        SimpleLevel slevel;
        Door door;

        door = new Door();
        door.setArchway(true);

        if (args.length > 0) {
            if (args[0].equals("a2")) {
                level = new ComplexLevel(door);
                level.createFromDoor(door, 0);
                level.printFromDoor();
                return;
            }
        }

        slevel = new SimpleLevel(door);
        slevel.createFromDoor();
        slevel.printFromDoor();
    }
}
