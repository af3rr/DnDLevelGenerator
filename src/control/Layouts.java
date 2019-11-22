package gui;

import java.util.HashMap;

public class Layouts {

    private static final String[] square = new String[] {
        "                ",
        "+---D1--D1-----+",
        "| ############ |",
        "| ############ |",
        "| ############ |",
        "| ############ |",
        "| ############ |",
        "+~~~~~~~~~~~~~~+"
    };

    private static final String[] rectangle = new String[] {
        "              ",
        "+---D1--D1---+",
        "| ########## |",
        "| ########## |",
        "+~~~~~~~~~~~~+"
    };

    private static final String[] circle = new String[] {
        "              ",
        "  +-DLD2DR-+  ",
        "+-] ###### [-+",
        "| ########## |",
        "| ########## |",
        "| ########## |",
        "+~} ###### {~+",
        "  +~~~~~~~~+  "
    };

    private static final String[] oval = new String[] {
        "                  ",
        "  +---D1--D1---+  ",
        "+-] ########## [-+",
        "| ############## |",
        "| ############## |",
        "+~} ########## {~+",
        "  +~~~~~~~~~~~~+  "
    };

    private static final String[] unusual1 = new String[] {
        "                  ",
        "          +-D1-+  ",
        "    +-D1--] ## |  ",
        "    | ######## |  ",
        "    | ## {} ## |  ",
        "    | ## [] ## [-+",
        "+-D1] ########## |",
        "| ############ {~+",
        "+~~~~~~~~~~~~~~+  "
    };

    private static final String[] unusual2 = new String[] {
        "                ",
        "+-D1----D1-+    ",
        "| ######## |    ",
        "| ######## |    ",
        "+~~~} #### [D1-+",
        "    | ######## |",
        "    | ######## |",
        "    +~~~~~~~~~~+"
    };

    private static final String[] unusual3 = new String[] {
        "                  ",
        "+---D1--D1---+    ",
        "| ########## |    ",
        "| ########## |    ",
        "| #### {~~~~~+    ",
        "| #### [----D1---+",
        "| ############## |",
        "| ############## |",
        "+~~~~~~~~~~~~~~~~+"
    };

    private static final String[] unusual4 = new String[] {
        "                    ",
        "+-DLD2DR-++-DLD2DR-+",
        "| ###### || ###### |",
        "| ###### || ###### |",
        "| ###### [] ###### |",
        "| ################ |",
        "| ################ |",
        "+~~~~~~~~~~~~~~~~~~+"
    };

    private static final String[] unusual5 = new String[] {
        "                  ",
        "+-D1--D1-+        ",
        "| ###### |        ",
        "| ###### |        ",
        "| ###### [--D1---+",
        "| ############## |",
        "| ############## |",
        "| ############## |",
        "+~~~~~~~~~~~~~~~~+"
    };

    private static final String[] unusual6 = new String[] {
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
        "+~~~~~} ## [D1---+",
        "      | ######## |",
        "      +~~~~~~~~~~+",
    };

    private static int areaOf(String[] space) {
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

    private static int numDoors(String[] space) {
        int doors = 0;

        for (int r = 0; r < space.length; r++) {
            for (int c = 0; c < (space[0].length()/2); c+=2) {
                String cell = String.format("%s%s", space[r].charAt(c), space[r].charAt(c+1));

                if (cell.equals("D1") || cell.equals("D2")) {
                    doors++;
                }
            }
        }

        return doors;
    }

    private static final HashMap<Integer, String> descriptions = new HashMap<>() {{
        //                    Shape, Width x Height
        put(1, String.format("Square,6x6,%d,%d", areaOf(square), numDoors(square)));
        put(2, String.format("Rectangle,6x3,%d,%d", areaOf(rectangle), numDoors(rectangle)));
        put(3, String.format("Circle,?x?,%d,%d", areaOf(circle), numDoors(circle)));
        put(4, String.format("Oval,?x?,%d,%d", areaOf(oval), numDoors(oval)));
        put(5, String.format("Unusual Shape,?x?,%d,%d", areaOf(unusual1), numDoors(unusual1)));
        put(6, String.format("Unusual Shape,?x?,%d,%d", areaOf(unusual2), numDoors(unusual2)));
        put(7, String.format("Unusual Shape,?x?,%d,%d", areaOf(unusual3), numDoors(unusual3)));
        put(8, String.format("Unusual Shape,?x?,%d,%d", areaOf(unusual4), numDoors(unusual4)));
        put(9, String.format("Unusual Shape,?x?,%d,%d", areaOf(unusual5), numDoors(unusual5)));
        put(10, String.format("Unusual Shape,?x?,%d,%d", areaOf(unusual6), numDoors(unusual6)));
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
}