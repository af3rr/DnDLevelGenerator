package gui;

import java.util.ArrayList;
import control.Controller;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.shape.StrokeType;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class LevelUI<toReturn> extends Application {
    private final int RATIO = 3;
    /* Even if it is a GUI it is useful to have instance variables
    so that you can break the processing up into smaller methods that have
    one responsibility.
     */
    private Controller controller;
    private GridPane root;
    private Popup descriptionPane;
    private ArrayList<ToggleButton> cButtons;
    private ArrayList<ToggleButton> pButtons;
    private Stage primaryStage;
    private SpaceGrid display;
    private GridPane mainMenu;
    private GridPane doorMenu;
    private String active;
    private ToggleGroup buttonGroup;
    private int width;
    private int height;
    private int ratio;
    
    @Override
    public void start(Stage assignedStage) {
        controller = new Controller(this);

        primaryStage = assignedStage;
        primaryStage.setTitle("D&D Level Generator");
        /* primaryStage.setResizable(false); */

        root = new GridPane();
        buttonGroup = new ToggleGroup();
        cButtons = new ArrayList<>();
        pButtons = new ArrayList<>();
        createMenu();
        setToChamber(0);
        setDimensions();

        Scene scene = new Scene(root, 1135, 768);
        primaryStage.setScene(scene);

        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        primaryStage.sizeToScene();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.show();
    }

    private void createMenu() {
        mainMenu = new GridPane();
        root.add(mainMenu, 0, 0);
        mainMenu.getStyleClass().add("menu");
        mainMenu.setHgap(20);
        mainMenu.setVgap(20);

        addChamberButtons();
        addPassageButtons();
    }

    private void addChamberButtons() {
        ToggleButton cButton;

        for (int r = 0, i = 0; r < 2; r++) {
            for (int c = 0; c < 3 && i < 5; c++, i++) {
                cButton = createChamberButton(i + 1);
                cButton.setToggleGroup(buttonGroup);
                cButtons.add(cButton);

                cButton.setOnAction((ActionEvent event) -> {
                    ToggleButton current = (ToggleButton) event.getSource();
                    String chNum = current.getText();

                    if (chNum.equals(getActive())) {
                        current.setSelected(true);

                    } else {
                        setToChamber(Integer.parseInt(chNum) - 1);
                        active = chNum;
                    }
                });
                mainMenu.add(cButton, c, r);
            }
        }
        cButtons.get(0).setSelected(true);
    }

    private void addPassageButtons() {
        ToggleButton pButton;
        
        for (int r = 0, i = 0; r < controller.numPassages(); r++, i++) {
            pButton = createPassageButton(i + 1);
            pButton.setToggleGroup(buttonGroup);
            pButtons.add(pButton);

            pButton.setOnAction((ActionEvent event) -> {
                ToggleButton current = (ToggleButton) event.getSource();
                String pText = current.getText();
                String pNum = pText.split(" ")[1];

                if (pText.equals(getActive())) {
                    current.setSelected(true);

                } else {
                    setToPassage(Integer.parseInt(pNum) - 1);
                    active = pText;
                }
            });
            mainMenu.add(pButton, 0, r + 3, 3, 1);
        }
    }

    public ToggleButton createChamberButton(int number) {
        ToggleButton cButton = new ToggleButton(String.valueOf(number));
        cButton.getStyleClass().add("chamber_button");
        cButton.setPrefWidth(19*3);
        cButton.setPrefHeight(20*3);
        cButton.setLineSpacing(10);
        
        return cButton;
    }

    public ToggleButton createPassageButton(int number) {
        ToggleButton pButton = new ToggleButton("Passage " + number);
        pButton.getStyleClass().add("passage_button");
        pButton.setPrefWidth(74*3);
        pButton.setPrefHeight(16*3);
        pButton.setLineSpacing(10);
        
        return pButton;
    }

    private void setToChamber(int i) {
        if (display != null) {
            root.getChildren().remove(display.getGrid()); 
        }
            
        display = controller.getChamberDisplay(i);
        root.add(display.getGrid(), 1, 0);
    }

    private void setToPassage(int i) {
        if (display != null) {
            root.getChildren().remove(display.getGrid()); 
        }
            
        display = controller.getPassageDisplay(i);
        root.add(display.getGrid(), 1, 0);
    }


    private void setDimensions() {
        height = 8 * 32 * RATIO;
        width = (9 * 32 * RATIO) + 300;
    }

    public String getActive() {
        return active;
    }

/*     private Popup createPopUp(int x, int y, String text) {
        Popup popup = new Popup();
        popup.setX(x);
        popup.setY(y);
        TextArea textA = new TextArea(text);
        popup.getContent().addAll(textA);
        textA.setStyle(" -fx-background-color: white;");
        textA.setMinWidth(80);
        textA.setMinHeight(50);
        return popup;
    } */

/*     private void changeDescriptionText(String text) {
        ObservableList<Node> list = descriptionPane.getContent();
        for (Node t : list) {
            if (t instanceof TextArea) {
                TextArea temp = (TextArea) t;
                temp.setText(text);
            }

        }
    } */

    public static void main(String[] args) {
        launch(args);
    }

}
