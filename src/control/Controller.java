package control;

import dnd.models.Treasure;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ComboBox;

import database.DBConnection;
import database.DBDetails;
import database.DBMonster;
import gui.LevelUI;
import gui.SpaceGrid;
import gui.Tile;
import model.Door;
import model.Space;
import model.DefaultLevel;
import model.DefaultPassage;
import model.DefaultChamber;
import dnd.models.Treasure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class Controller {
    private LevelUI levelUI;
    private DefaultLevel level;
    private ArrayList<DefaultChamber> chambers;
    private ArrayList<DefaultPassage> passages;
    private ArrayList<SpaceGrid> chamberDisplays;
    private ArrayList<SpaceGrid> passageDisplays;
    private HashMap<String, Integer> treasureTypes;
    private DBConnection db;
   
    public Controller(LevelUI gui){
        db = new DBConnection(DBDetails.username, DBDetails.password);
        treasureTypes = new HashMap<>();
        levelUI = gui;

        level = new DefaultLevel();

        createDisplays();
    }

    public SpaceGrid getChamberDisplay(int i) {
        return chamberDisplays.get(i);
    }

    public SpaceGrid getPassageDisplay(int i) {
        return passageDisplays.get(i);
    }

    private void initDB() {
        db.deleteAllMonsters();
        db.addMonster("skeleton", "1", "5", "Nothing but bones");
        db.addMonster("shrieker", "3", "6", "A big bat with a big eye");
        db.addMonster("spider", "2", "8", "A venemous ant with some extra legs");
    }

    public int numChambers() {
        return chambers.size();
    }

    public int numPassages() {
        return passages.size();
    }

    public void createDisplays() {
        chambers = level.getChambers();
        passages = level.getPassages();

        chamberDisplays = new ArrayList<>();
        passageDisplays = new ArrayList<>();

        for (DefaultChamber c : chambers) {
            chamberDisplays.add(new SpaceGrid(c.getLayout()));
        }

        for (DefaultPassage p : passages) {
            passageDisplays.add(new SpaceGrid(p.getLayout()));
        }
    }

    public void loadLevel(File file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);

            level = (DefaultLevel) objIn.readObject();
            fileIn.close();
            objIn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveLevel(File file, String filename) {
        String path = String.format("%s/%s.level", file.getAbsolutePath(), filename);
        System.out.println(path);

        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);

            objOut.writeObject(level);
            fileOut.close();
            objOut.close();

        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public ArrayList<Button> getChamberDoorButtons(int c) {
        DefaultChamber chamber = chambers.get(c);

        return getDoorButtons(chamber.getDoors());
    }

    public ArrayList<Button> getPassageDoorButtons(int c) {
        DefaultPassage passage = passages.get(c);

        return getDoorButtons(passage.getDoors());
    }

    public ComboBox getMonsterOptions() {
        ComboBox removableMonsters = new ComboBox();
        SpaceGrid grid = levelUI.getDisplay();

        for (Tile tile : grid.getMonsters()) {
            DBMonster monster = db.findMonster(tile.getMonsterType());
            removableMonsters.getItems().add(monster.toString());
        }
        
        return removableMonsters;
    }

    public ComboBox getAllMonsterOptions() {
        ComboBox addableMonsters = new ComboBox();

        for (String description : db.getAllMonsters()) {
            addableMonsters.getItems().add(description);
        }
        
        return addableMonsters;
    }

    public void removeMonsterFromCurrent(String description, int index) {
        int a = levelUI.getActiveNum() - 1;
        SpaceGrid grid = levelUI.getDisplay();
        ArrayList<Tile> monsterTiles = grid.getMonsters();

        for (int i = 0; i < monsterTiles.size(); i++) {
            Tile tile = monsterTiles.get(i);
            DBMonster monster = db.findMonster(tile.getMonsterType());

            if (i == index) {
                if (description.equals(monster.toString())) {
                    grid.removeMonster(tile.row(), tile.column());
                    break;
                }
            }
        }

        updateLayouts(a);
    }


    public void addMonsterToCurrent(String description) {
        int a = levelUI.getActiveNum() - 1;
        DBMonster monster = new DBMonster();
        monster.setName(description.split(",")[0]);

        String type = "M" + monster.getType();

        if (levelUI.activeIsChamber()) {
            int pos = chambers.get(a).addToLayout(type);
            chamberDisplays.get(a).addMonster(type, pos / 8, pos % 8);
            setChamberText(a);

        } else {
            int pos = passages.get(a).addToLayout(type);
            passageDisplays.get(a).addMonster(type, pos / 8, pos % 8);
            setPassageText(a);
        }
    }

    public void removeTreasureFromCurrent(String description, int index) {
        int a = levelUI.getActiveNum() - 1;
        HashMap<Integer, Treasure> map;
        SpaceGrid grid = levelUI.getDisplay();
        ArrayList<Tile> treasureTiles = grid.getTreasure();

        map = levelUI.activeIsChamber() ? chambers.get(a).getTreasureMap() : passages.get(a).getTreasureMap();
        
        for (int i = 0; i < treasureTiles.size(); i++) {
            Tile tile = treasureTiles.get(i);

            if (i == index) {
                Treasure treasure = map.get(cellAsInt(tile.row(), tile.column()));

                if (description.equals(treasure.getDescription())) {
                    grid.removeTreasure(tile.row(), tile.column());
                    map.remove(cellAsInt(tile.row(), tile.column()));
                    break;
                }
            }
        }

        updateLayouts(a);
    }

    public void addTreasureToCurrent(String description) {
        int a = levelUI.getActiveNum() - 1;
        int type = treasureTypes.get(description);

        if (levelUI.activeIsChamber()) {
            int pos = chambers.get(a).addToLayout("TT", type);
            chamberDisplays.get(a).addTreasure(pos / 8, pos % 8);
            setChamberText(a);

        } else {
            int pos = passages.get(a).addToLayout("TT", type);
            passageDisplays.get(a).addTreasure(pos / 8, pos % 8);
            setPassageText(a);
        }
    }

    public ComboBox getTreasureOptions() {
        ComboBox removableTreasure = new ComboBox();
        HashMap<Integer, Treasure> treasureList;
        SpaceGrid grid = levelUI.getDisplay();
        int a = levelUI.getActiveNum();

        if (levelUI.activeIsChamber()) {
            treasureList = chambers.get(a - 1).getTreasureMap();
        } else {
            treasureList = passages.get(a - 1).getTreasureMap();
        }

        for (Tile tile : grid.getTreasure()) {
            Treasure treasure = treasureList.get(cellAsInt(tile.row(), tile.column()));
            removableTreasure.getItems().add(treasure.getDescription());
        }
        return removableTreasure;
    }

    public ComboBox getAllTreasureOptions() {
        ComboBox addableTreasure = new ComboBox();
        HashSet<String> uniqueTreasure = new HashSet<>();
        
        for (int i = 0; i < 100; i++) {
            Treasure treasure = new Treasure();
            treasure.chooseTreasure(i);

            treasureTypes.put(treasure.getDescription(), i);
            uniqueTreasure.add(treasure.getDescription());
        }

        for (String description : uniqueTreasure) {
            addableTreasure.getItems().add(description);
        }
        return addableTreasure;
    }

    private ArrayList<Button> getDoorButtons(ArrayList<Door> dList) {
        ArrayList<Button> dButtons = new ArrayList<>();
        Image image = new Image("file:res/door_button.png");

        for (int i = 0; i < dList.size(); i++) {
            Door door = dList.get(i);

            Button dButton = new Button("Door " + (i+1), new ImageView(image));
            Tooltip tooltip = new Tooltip(getDoorDescription(door));

            dButton.getStyleClass().add("door_button");
            dButton.setPrefWidth(200);
            dButton.setPrefHeight(16*3);
            dButton.setTooltip(tooltip);
            
            dButtons.add(dButton);
        }
        return dButtons;
    }

    private String getDoorDescription(Door door) {
        StringBuilder description = new StringBuilder();

        description.append(door.getDescription());

        Space space = door.getSpaceOne();
        if (space instanceof DefaultChamber) {
            description.append("\nSpace One: Chamber ");
            description.append(chambers.indexOf((DefaultChamber) space) + 1);
        } else {
            description.append("\nSpace One: Passage ");
            description.append(passages.indexOf((DefaultPassage) space) + 1);
        }

        space = door.getSpaceTwo();
        if (space instanceof DefaultChamber) {
            description.append("\nSpace Two: Chamber ");
            description.append(chambers.indexOf((DefaultChamber) space) + 1);
        } else {
            description.append("\nSpace Two: Passage ");
            description.append(passages.indexOf((DefaultPassage) space) + 1);
        }

        return description.toString();
    }

    private void updateLayouts(int i) {
        if (levelUI.activeIsChamber()) {
            chambers.get(i).setLayout(levelUI.getDisplay().getLayout());
            setChamberText(i);
        } else {
            passages.get(i).setLayout(levelUI.getDisplay().getLayout());
            setPassageText(i);
        }
    }

    public void setPassageText(int i) {
        DefaultPassage passage = passages.get(i);
        String description = passage.getDescription();

        description += getMonsterDescriptions(getPassageDisplay(i));

        levelUI.setTitle("Passage " + (i + 1));
        levelUI.setDescription(description);
    }

    public void setChamberText(int i) {
        DefaultChamber chamber = chambers.get(i);
        String description = chamber.getDescription();

        description += getMonsterDescriptions(getChamberDisplay(i));

        levelUI.setTitle("Chamber " + (i + 1));
        levelUI.setDescription(description);
    }

    private String getMonsterDescriptions(SpaceGrid grid) {
        ArrayList<Tile> monsterTiles = grid.getMonsters();

        String description = "\nMonsters: ";
        description += monsterTiles.isEmpty() ? "None\n" : "\n";

        for (Tile tile : monsterTiles) {
            DBMonster monster = db.findMonster(tile.getMonsterType());
            description += "  " + monster.getName() + "\n";
        }

        return description;
    }

    public int cellAsInt(int r, int c) {
        return (r * 8) + c;
    }
}
