package gui;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Paint;
import dnd.models.Monster;
import dnd.models.Stairs;
import dnd.models.Treasure;
import dnd.models.Trap;


public class Tile extends StackPane {
    SpaceGrid grid;
    Rectangle background;
    ObservableList<Node> children;
    Monster monster;
    Treasure treasure;
    Stairs stairs;
    Trap trap;
    int row;
    int col;
    
    public Tile(SpaceGrid parent, int r, int c, Paint fill) {
        grid = parent;
        row = r;
        col = c;

        setBackground(fill);        

        if (grid.cell(row, col).equals("##")) {
            setCornerShadow();
            setTopShadow();
            setLeftShadow();
        }
    }

    public void setBackground(Paint fill) {
        background = new Rectangle(64, 64, fill);
        children = getChildren();
        children.add(background);
    }

    private void setCornerShadow() {
        if (grid.cell(row-1, col-1).equals("] ")) {
            setShadow(new Image("file:res/shadow_corner_r.png"), Pos.TOP_LEFT);
        }  
    }

    private void setTopShadow() {
        switch (grid.cell(row-1, col)) {
            case "--":
            case "D1":
            case "DL":
            case "D2":
            case "DR":
            case " {":
            case "} ":
            case " [":
            case "] ":
                if (grid.cell(row-1, col-1).equals("##")) {
                    setShadow(new Image("file:res/shadow_cut_b.png"), Pos.TOP_CENTER);
                } else {
                    setShadow(new Image("file:res/shadow_t.png"), Pos.TOP_CENTER);
                }

            default:
                break;
        }
    }

    private void setLeftShadow() {
        switch (grid.cell(row, col-1)) {
            case "} ":
                setShadow(new Image("file:res/shadow_cut_l.png"), Pos.CENTER_LEFT);
                break;
            case "| ":
            case "] ":
                setShadow(new Image("file:res/shadow_l.png"), Pos.CENTER_LEFT);
        
            default:
                break;
        }
    }

    private void setShadow(Image image, Pos pos) {
        ImageView shadow = new ImageView(image);
        children.add(shadow);
        StackPane.setAlignment(shadow, pos);
    }
}