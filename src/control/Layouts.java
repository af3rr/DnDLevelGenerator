package gui;

import java.util.HashMap;

public class Layouts {

    private static final String[] square = new String[] { // 1
        "                  ",
        "+-----D1--D1-----+",
        "| ############## |",
        "| ############## |",
        "| ############## |",
        "| ############## |",
        "| ############## |",
        "+~~~~~~~~~~~~~~~~+"
    };

    private static final String[] rectangle = new String[] { // 2
        "                  ",
        "+-----D1--D1-----+",
        "| ############## |",
        "| ############## |",
        "| ############## |",
        "| ############## |",
        "+~~~~~~~~~~~~~~~~+",
        "                  "
    };

    private static final String[] circle = new String[] { // 3
        "                  ",
        "    +-DLD2DR-+    ",
        "  +-] ###### [-+  ",
        "  | ########## |  ",
        "  | ########## |  ",
        "  | ########## |  ",
        "  +~} ###### {~+  ",
        "    +~~~~~~~~+    "
    };

    private static final String[] oval = new String[] { // 4
        "                  ",
        "  +---D1--D1---+  ",
        "+-] ########## [-+",
        "| ############## |",
        "| ############## |",
        "+~} ########## {~+",
        "  +~~~~~~~~~~~~+  ",
        "                  "
    };

    private static final String[] unusual1 = new String[] { // 5
        "                  ",
        "          +-D1-+  ",
        "    +-D1--] ## |  ",
        "    | ######## |  ",
        "    | ## {} ## [-+",
        "+-D1] ## [] #### |",
        "| ############ {~+",
        "+~~~~~~~~~~~~~~+  "
    };

    private static final String[] unusual2 = new String[] { // 6
        "                  ",
        "+-D1----D1-+      ",
        "| ######## |      ",
        "| ######## |      ",
        "+~~~} #### [--D1-+",
        "    | ########## |",
        "    | ########## |",
        "    +~~~~~~~~~~~~+"
    };

    private static final String[] unusual3 = new String[] { // 7
        "                  ",
        "+---D1--D1-------+",
        "| ############## |",
        "| ############## |",
        "| #### {~~~~~~~~~+",
        "| #### [----D1---+",
        "| ############## |",
        "+~~~~~~~~~~~~~~~~+"
    };

    private static final String[] unusual4 = new String[] { // 8
        "                  ",
        "+-DLD2DR-+        ",
        "| ###### |  +-D1-+",
        "| ###### |  | ## |",
        "| ###### [--] ## |",
        "| ############## |",
        "| ############## |",
        "+~~~~~~~~~~~~~~~~+"
    };

    private static final String[] unusual5 = new String[] { // 9
        "                  ",
        "+-D1--D1-+        ",
        "| ###### |        ",
        "| ###### |        ",
        "| ###### [--D1---+",
        "| ############## |",
        "| ############## |",
        "+~~~~~~~~~~~~~~~~+"
    };

    private static final String[] unusual6 = new String[] { // 10
        "                  ",
        "+---D1--D1--D1---+",
        "| ############## |",
        "| ############## |",
        "| ###### {~~~~~~~+",
        "| ###### |        ",
        "| ###### |        ",
        "+~~~~~~~~+        "
    };

    

    private static final String[] passage1 = new String[] {
        "                  ",
        "+-D1-+            ",
        "| ## |            ",
        "| ## [-----+      ",
        "| ######## |      ",
        "+~~~~~} ## [DA---+",
        "      | ######## |",
        "      +~~~~~~~~~~+"
    };

    private static final String[] passage2 = new String[] {
        "                  ",
        "  +---DA-------+  ",
        "  | ########## |  ",
        "  +~~~~~~~} ## |  ",
        "          | ## |  ",
        "  +---DA--] ## |  ",
        "  | ########## |  ",
        "  +~~~~~~~~~~~~+  "
    };

    private static final String[] passage3 = new String[] {
        "                  ",
        "+-D1-+            ",
        "| ## |  +---DA---+",
        "| ## |  | ###### |",
        "| ## |  | ## {~~~+",
        "| ## [--] ## |    ",
        "| ########## |    ",
        "+~~~~~~~~~~~~+    "
    };

    private static final String[] passage4 = new String[] {
        "                  ",
        "            +-D1-+",
        "+-D1-+      | ## |",
        "| ## |      | ## |",
        "| ## [------] ## |",
        "| ############## |",
        "+~~~~~~~~~~~~~~~~+",
        "                  "
    };

    private static final String[] passage5 = new String[] {
        "                  ",
        "            +-D1-+",
        "            | ## |",
        "            | ## |",
        "+-D1--------] ## |",
        "| ############## |",
        "+~~~~~~~~~~~~~~~~+",
        "                  ",
    };

    private static final HashMap<Integer, String> descriptions = new HashMap<>() {{
        put(1, String.format("Square\nDimensions: 7x7\nArea: %d\nDoors: %d", getArea(square), numDoors(square)));
        put(2, String.format("Rectangle\nDimensions: 7x4\nArea: %d\nDoors: %d", getArea(rectangle), numDoors(rectangle)));
        put(3, String.format("Circle\nDimensions: ?x?\nArea: %d\nDoors: %d", getArea(circle), numDoors(circle)));
        put(4, String.format("Oval\nDimensions: ?x?\nArea: %d\nDoors: %d", getArea(oval), numDoors(oval)));
        put(5, String.format("Unusual Shape\nDimensions: ?x?\nArea: %d\nDoors: %d", getArea(unusual1), numDoors(unusual1)));
        put(6, String.format("Unusual Shape\nDimensions: ?x?\nArea: %d\nDoors: %d", getArea(unusual2), numDoors(unusual2)));
        put(7, String.format("Unusual Shape\nDimensions: ?x?\nArea: %d\nDoors: %d", getArea(unusual3), numDoors(unusual3)));
        put(8, String.format("Unusual Shape\nDimensions: ?x?\nArea: %d\nDoors: %d", getArea(unusual4), numDoors(unusual4)));
        put(9, String.format("Unusual Shape\nDimensions: ?x?\nArea: %d\nDoors: %d", getArea(unusual5), numDoors(unusual5)));
        put(10, String.format("Unusual Shape\nDimensions: ?x?\nArea: %d\nDoors: %d", getArea(unusual6), numDoors(unusual6)));
    }};

    private static final HashMap<Integer, String[]> chambers = new HashMap<>() {{
        put(1, square);
        put(2, rectangle);
        put(3, circle);
        put(4, oval);
        put(5, unusual1);
        put(6, unusual2);
        put(7, unusual3);
        put(8, unusual4);
        put(9, unusual5);
        put(10, unusual6);
    }};
    
    private static final HashMap<Integer, String[]> passages = new HashMap<>() {{
        put(1, passage1);
        put(2, passage2);
        put(3, passage3);
        put(4, passage4);
        put(5, passage5);
    }};

    private static final HashMap<Integer, int[]> passageSections = new HashMap<>() {{
        put(1, new int[] {1, 1, 11, 1, 1, 13, 1, 11, 1, 9, 19});
        put(2, new int[] {1, 9, 1, 1, 13, 1, 1, 1, 13, 1, 1, 7, 19});
        put(3, new int[] {1, 1, 1, 1, 11, 1, 1, 1, 11, 1, 1, 13, 9, 19});
        put(4, new int[] {1, 1, 11, 1, 1, 1, 1, 1, 11, 1, 1, 5});
        put(5, new int[] {11, 1, 1, 1, 1, 1, 11, 1, 1, 5});
    }};

    public static String[] chamber(int i) {
        return chambers.get(i);
    }

    public static String[] passage(int i) {
        return passages.get(i);
    }

    public static String getDescription(int i) {
        return descriptions.get(i);
    }

    public static int getWidth(String[] layout) {
        return layout[0].length() / 2;
    }

    public static int getHeight(String[] layout) {
        return layout.length;
    }

    public static int getArea(String[] space) {
        int area = 0;

        for (int r = 0; r < space.length; r++) {
            for (int c = 0; c < (space[0].length()/2); c+=2) {
                String cell = String.format("%s%s", space[r].charAt(c), space[r].charAt(c+1));

                if (cell.equals("##")) {
                    area += 10;
                }
            }
        }

        return area;
    }

    public static int numDoors(String[] space) {
        int doors = 0;

        for (int r = 0; r < space.length; r++) {
            for (int c = 0; c < space[0].length(); c+=2) {
                String cell = String.format("%s%s", space[r].charAt(c), space[r].charAt(c+1));
                if (cell.equals("D1") || cell.equals("D2")) {
                    doors++;
                }
            }
        }
        return doors;
    }

    public static int[] sectionSequence(int i) {
        return passageSections.get(i);
    }

}