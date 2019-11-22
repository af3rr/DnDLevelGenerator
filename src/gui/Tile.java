package gui;

import java.util.HashMap;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


public class Tile extends StackPane {
    private SpaceGrid grid;
    private Node background;
    private ObservableList<Node> children;
    private HashMap<String, Image> textures;
    private ImageView monster;
    private ImageView treasure;
    private String code;
    private int row;
    private int col;

    public Tile(SpaceGrid parent, int r, int c) {
        grid = parent;
        row = r;
        col = c;

        code = grid.cell(row, col);

        textures = new HashMap<>();
        treasure = new ImageView();
        children = getChildren();
        initTextures();

        if (isInner() || isEntity()) {
            setCornerShadow();
            setTopShadow();
            setLeftShadow();
        }

        become();

        children.add(background);
        getLastChild().toBack();
        children.add(treasure);
    }

    private void become() {
        switch(code) {
            case "  ":
            case "+~":
            case "~+":
                setBackground(Color.rgb(208,196,172));
                break;

            case "##":
                setBackground(Color.rgb(162, 153, 143));
                break;

            case "M1":
            case "M2":
            case "M3":
                setBackground(Color.rgb(162, 153, 143));
                addMonster();
                break;

            case "TT":
                setBackground(Color.rgb(162, 153, 143));
                addTreasure();
                break;

            default:
                setTexture();
                break;
        }
    }

    private boolean isEntity() {
        return code.contains("M") || code.equals("TT");
    }

    private boolean isInner() {
        return code.equals("##");
    }

    private Node getLastChild() {
        return children.get(children.size()-1);
    }

    public void addTreasure() {
        treasure.setImage(textures.get("TT"));
    }

    public void removeTreasure() {
        treasure.setImage(null);
    }

    public void addMonster() {
        MonsterAnimation a = new MonsterAnimation(grid.cell(row, col));
        monster = a.getImage();
        children.add(monster);
        a.play();
    }

    public void removeMonster() {
        monster.setImage(null);
    }

    public void setTexture() {
        Image image = textures.get(code);
        background = new ImageView(image);
    }

    public void setBackground(Paint fill) {
        background = new Rectangle(64, 64, fill);
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
        textures.put("TT", new Image("file:res/treasure.png"));
    }
}