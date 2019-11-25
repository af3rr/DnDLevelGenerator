package gui;

import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.DefaultChamber;
import model.DefaultPassage;
import model.Space;

public class SpaceGrid extends GridPane {
    private GridPane grid;
    private DefaultChamber chamber;
    private DefaultPassage passage;
    private String[] layout;
    private Tile[][] tileMatrix;
    private ArrayList<Tile> monsters;
    private ArrayList<Tile> treasure;
    private int width;
    private int height;

    public SpaceGrid(Space space) {
        tileMatrix = new Tile[8][9];

        if (space instanceof DefaultChamber) {
            chamber = (DefaultChamber) space;
            layout = chamber.getLayout();
            
        } else if (space instanceof DefaultPassage) {
            passage = (DefaultPassage) space;
            layout = passage.getLayout();
        }

        width = layout[0].length() / 2; // 9
        height = layout.length; // 8

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                Tile tile = new Tile(this, r, c);
                tileMatrix[r][c] = tile;
                add(tile, c, r);
            }
        }
    }

    public String cell(int r, int c) {
        return String.format("%c%c", layout[r].charAt(c * 2), layout[r].charAt((c * 2)+1));
    }

    public GridPane getGrid() {
        return grid;
    }

    public Tile[][] getMatrix() {
        return tileMatrix;
    }

    public String[] getLayout() {
        StringBuilder row = new StringBuilder();
        String[] updated = new String[height];
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                row.append(tileMatrix[r][c].getCode());
            }
            updated[r] = row.toString();
            row.setLength(0);
        }

        return updated;
    }

    public void addMonster(String type, int r, int c) {
        tileMatrix[r][c].addMonster(type);
        setCell(r, c, type);
    }

    public void removeMonster(int r, int c) {
        tileMatrix[r][c].removeMonster();
        setCell(r, c, "##");
    }

    public void addTreasure(int r, int c) {
        tileMatrix[r][c].addTreasure();
        setCell(r, c, "TT");
    }

    public void removeTreasure(int r, int c) {
        tileMatrix[r][c].removeTreasure();
        setCell(r, c, "##");
    }

    public ArrayList<Tile> getMonsters() {
        monsters = new ArrayList<>();

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (cell(r, c).contains("M")) {
                    monsters.add(tileMatrix[r][c]);
                }
            }
        }

        return monsters;
    }

    public ArrayList<Tile> getTreasure() {
        treasure = new ArrayList<>();

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (cell(r, c).equals("TT")) {
                    treasure.add(tileMatrix[r][c]);
                }
            }
        }

        return treasure;
    }

    private void setCell(int row, int col, String item) {
        String[] updated = new String[layout.length];
        StringBuilder currRow;

        col *= 2;

        for (int r = 0; r < layout.length; r++) {
            currRow = new StringBuilder(layout[r]);

            if (r == row) {
                currRow.setCharAt(col + 0, item.charAt(0));
                currRow.setCharAt(col + 1, item.charAt(1));
            }

            updated[r] = currRow.toString();
            currRow.setLength(0);
        }

        layout = updated;
    }
}
