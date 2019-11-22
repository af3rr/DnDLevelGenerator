package gui;

import javafx.scene.layout.GridPane;

import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.DefaultChamber;
import model.DefaultPassage;
import model.Space;

public class SpaceGrid {
    private GridPane grid;
    private HashMap<String, Image> textures;
    private DefaultChamber chamber;
    private DefaultPassage passage;
    private String[] layout;
    private int type;
    private int width;
    private int length;

    public SpaceGrid(Space space, int t) {
        grid = new GridPane();
        textures = new HashMap<>();
        type = t;

        if (space instanceof DefaultChamber) {
            chamber = (DefaultChamber) space;
        } else if (space instanceof DefaultPassage) {
            passage = (DefaultPassage) space;
        }

        layout = Layouts.passage(type);

        width = layout.length;
        length = layout[0].length() / 2;

        initTextures();
        parseLayout();
    }

    public void parseLayout() {
        String cell;

        for (int r = 0; r < width; r++) {
            for (int c = 0; c < length; c++) {
                grid.add(getImage(r, c), c, r);
            }
        }
    }

    String cell(int r, int c) {
        return String.format("%c%c", layout[r].charAt(c * 2), layout[r].charAt((c * 2)+1));
    }

    public GridPane getGrid() {
        return grid;
    }

    public Node getImage(int r, int c) {
        switch(cell(r, c)) {
            case "  ":
            case "+~":
            case "~+":
                return new Tile(this, r, c, Color.rgb(208,196,172));

            case "##": // Inner Tiles
                return new Tile(this, r, c, Color.rgb(162, 153, 143));

            default:
                return new ImageView(textures.get(cell(r, c)));
        }
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return length;
    }

    public void initTextures() {
        textures.put("+-", new Image("file:res/front_corner_r.png"));
        textures.put("-+", new Image("file:res/front_corner_l.png"));
        textures.put("--", new Image("file:res/front_wall.png"));
        textures.put("~~", new Image("file:res/back_wall.png"));
        textures.put(" |", new Image("file:res/right_wall.png"));
        textures.put("| ", new Image("file:res/left_wall.png"));
        textures.put("D1", new Image("file:res/door.png"));
        textures.put("DL", new Image("file:res/door_left.png"));
        textures.put("D2", new Image("file:res/door2.png"));
        textures.put("DR", new Image("file:res/door_right.png"));
        textures.put("] ", new Image("file:res/front_end_r.png"));
        textures.put(" [", new Image("file:res/front_end_l.png"));
        textures.put("} ", new Image("file:res/back_end_r.png"));
        textures.put(" {", new Image("file:res/back_end_l.png"));
    }
}
