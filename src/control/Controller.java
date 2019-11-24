package control;

import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import database.DBConnection;
import database.DBDetails;
import gui.LevelUI;
import gui.SpaceGrid;
import model.Door;
import model.DefaultLevel;
import model.DefaultPassage;
import model.DefaultChamber;
import java.util.ArrayList;

public class Controller {
    private LevelUI levelUI;
    private DefaultLevel level;
    private ArrayList<SpaceGrid> chamberDisplays;
    private ArrayList<SpaceGrid> passageDisplays;
    private ArrayList<DefaultChamber> chambers;
    private ArrayList<DefaultPassage> passages;
    private DBConnection db;

    public Controller(LevelUI gui){
        /* db = new DBConnection(DBDetails.username, DBDetails.password); */
        levelUI = gui;

        level = new DefaultLevel();

        chambers = level.getChambers();
        passages = level.getPassages();
        System.out.println("Check 1");
        createDisplays();

        System.out.println("Chambers: " + chambers.size());
        System.out.println("Passages: " + passages.size());
        System.out.println("Passage Displays: " + passageDisplays.size());
        System.out.println("Chamber Displays: " + chamberDisplays.size());
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
        chamberDisplays = new ArrayList<>();
        passageDisplays = new ArrayList<>();

        for (DefaultChamber c : chambers) {
            chamberDisplays.add(new SpaceGrid(c));
        }

        for (DefaultPassage p : passages) {
            passageDisplays.add(new SpaceGrid(p));
        }
    }
}
