package gui;

import java.util.HashMap;


public class Layouts {

    private static String[] square = new String[] {
        "                ",
        "+---D1--D1-----+",
        "| ############ |",
        "| ############ |",
        "| ############ |",
        "| ############ |",
        "+~~~~~~~~~~~~~~+"
    };

    private static String[] rectangle = new String[] {
        "              ",
        "+---D1--D1---+",
        "| ########## |",
        "| ########## |",
        "+~~~~~~~~~~~~+"
    };

    private static String[] circle = new String[] {
        "              ",
        "  +-DLD2DR-+  ",
        "+-] ###### [-+",
        "| ########## |",
        "| ########## |",
        "| ########## |",
        "+~} ###### {~+",
        "  +~~~~~~~~+  "
    };

    private static String[] oval = new String[] {
        "                  ",
        "  +---D1--D1---+  ",
        "+-] ########## [-+",
        "| ############## |",
        "| ############## |",
        "+~} ########## {~+",
        "  +~~~~~~~~~~~~+  "
    };

    private static String[] unusual1 = new String[] {
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

    private static String[] unusual2 = new String[] {
        "                ",
        "+-D1----D1-+    ",
        "| ######## |    ",
        "| ######## |    ",
        "+~~~} #### [D1-+",
        "    | ######## |",
        "    | ######## |",
        "    +~~~~~~~~~~+"
    };

    private static String[] unusual3 = new String[] {
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

    private static String[] unusual4 = new String[] {
        "                    ",
        "+-DLD2DR-++-DLD2DR-+",
        "| ###### || ###### |",
        "| ###### || ###### |",
        "| ###### [] ###### |",
        "| ################ |",
        "| ################ |",
        "+~~~~~~~~~~~~~~~~~~+"
    };

    private static String[] unusual5 = new String[] {
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

    private static String[] unusual6 = new String[] {
        "                  ",
        "+---D1--D1--D1---+",
        "| ############## |",
        "| ############## |",
        "| ###### {~~~~~~~+",
        "| ###### |        ",
        "| ###### |        ",
        "+~~~~~~~~+        "
    };

    private static HashMap<Integer, String[]> chambers = new HashMap<>() {{
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
    

    private static HashMap<Integer, String[]> passages = new HashMap<>() {{

    }};
    

    public static String[] chamber(int i) {
        return chambers.get(i);
    }

    public static String[] passage(int i) {
        return passages.get(i);
    }
}