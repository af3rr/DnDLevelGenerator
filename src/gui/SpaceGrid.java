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
    private DefaultChamber chamber;
    private DefaultPassage passage;
    private String[] layout;
    private int width;
    private int length;

    public SpaceGrid(Space space) {
        grid = new GridPane();

        if (space instanceof DefaultChamber) {
            chamber = (DefaultChamber) space;
            layout = chamber.getLayout();
            
        } else if (space instanceof DefaultPassage) {
            passage = (DefaultPassage) space;
            layout = passage.getLayout();
        }

        width = layout.length;
        length = layout[0].length() / 2;

        for (int r = 0; r < width; r++) {
            for (int c = 0; c < length; c++) {
                grid.add(new Tile(this, r, c), c, r);
            }
        }
    }

    String cell(int r, int c) {
        return String.format("%c%c", layout[r].charAt(c * 2), layout[r].charAt((c * 2)+1));
    }

    public GridPane getGrid() {
        return grid;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return length;
    }
}
