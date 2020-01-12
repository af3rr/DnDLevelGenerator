package gui;

import control.Controller;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.DirectoryChooser;
import javafx.geometry.Pos;

import java.io.File;

public class LevelUI<toReturn> extends Application {
    private final int RATIO = 3;

    private Controller controller;
    private VBox root;
    private GridPane view;
    private Popup descriptionPane;
    private ArrayList<ToggleButton> cButtons;
    private ArrayList<ToggleButton> pButtons;
    private HashMap<Button, ComboBox> editButtons;
    private Stage primaryStage;
    private Stage secondaryStage;
    private Scene scene;
    private SpaceGrid display;
    private MenuBar menuBar;
    private GridPane mainMenu;
    private GridPane doorMenu;
    private String active;
    private ToggleGroup buttonGroup;
    private Label spaceTitle;
    private Label spaceDesc;
    private int ratio;
    
    @Override
    public void start(Stage assignedStage) {
        controller = new Controller(this);
        buttonGroup = new ToggleGroup();
        cButtons = new ArrayList<>();
        pButtons = new ArrayList<>();
        editButtons = new HashMap<>();
        spaceTitle = new Label();
        spaceDesc = new Label();
        root = new VBox();

        initializeUI();
        scene = new Scene(root, 1351, 768);
        scene.getStylesheets().add(getClass().getResource("buttons.css").toExternalForm());

        primaryStage = assignedStage;
        primaryStage.setTitle("D&D Level Generator");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.show();
    }

    private void initializeUI() {
        createMenuBar();
        createView();

        spaceTitle.setWrapText(true);
        spaceTitle.setMaxWidth(200);
        spaceDesc.setWrapText(true);
        spaceDesc.setMaxWidth(200);
        spaceTitle.getStyleClass().add("space_title");
        spaceDesc.getStyleClass().add("space_desc");
        root.getChildren().addAll(menuBar, view);
        setToChamber(0);
    }

    private void createView() {
        view = new GridPane();
        mainMenu = new GridPane();
        view.add(mainMenu, 0, 0);
        mainMenu.getStyleClass().add("space-menu");
        mainMenu.setHgap(20);
        mainMenu.setVgap(20);

        addChamberButtons();
        addPassageButtons();
    }

    private void createDoorMenu(ArrayList<Button> dButtons) {
        doorMenu = new GridPane();
        doorMenu.getStyleClass().add("door-menu");
        doorMenu.setAlignment(Pos.TOP_LEFT);
        doorMenu.setVgap(20);
        view.add(doorMenu, 2, 0);

        doorMenu.add(createEditButton(), 0, 0);
        doorMenu.add(spaceTitle, 0, 1);
        doorMenu.add(spaceDesc, 0, 2);

        for (int i = 0; i < dButtons.size(); i++) {
            Button dButton = dButtons.get(i);
            doorMenu.add(dButton, 0, i + 3);
        }
    }

    private void createMenuBar() {
        menuBar = new MenuBar();
        Menu file = new Menu("File");
        
        file.getItems().addAll(createSaveItem(), createLoadItem());
        menuBar.getMenus().add(file);
    }

    private void addChamberButtons() {
        ToggleButton cButton;

        for (int r = 0, i = 0; r < 2; r++) {
            for (int c = 0; c < 3 && i < 5; c++, i++) {
                cButton = createChamberButton(i + 1);

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

    private ToggleButton createChamberButton(int number) {
        ToggleButton cButton = new ToggleButton(String.valueOf(number));
        cButton.getStyleClass().add("chamber_button");
        cButton.setToggleGroup(buttonGroup);
        cButtons.add(cButton);
        cButton.setPrefWidth(19*3);
        cButton.setPrefHeight(20*3);
        
        return cButton;
    }

    private ToggleButton createPassageButton(int number) {
        ToggleButton pButton = new ToggleButton("Passage " + number);
        pButton.getStyleClass().add("passage_button");
        pButton.setToggleGroup(buttonGroup);
        pButtons.add(pButton);
        pButton.setPrefWidth(74*3);
        pButton.setPrefHeight(16*3);
        
        return pButton;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button");
        button.setPrefWidth(43*3);
        button.setPrefHeight(16*3);
        
        return button;
    }

    private Button createEditButton() {
        Button button = new Button();
        button.getStyleClass().add("edit_button");
        button.setPrefWidth(19*3);
        button.setPrefHeight(16*3);

        button.setOnAction((ActionEvent event) -> {
            openEdit();
        });
        
        return button;
    }

    private Button createConfirmEditButton(int i) {
        switch(i) {
            case 0:
                return createRemoveMonsterButton();
            case 1:
                return createAddMonsterButton();
            case 2:
                return createRemoveTreasureButton();
            case 3:
                return createAddTreasureButton();
            default:
                return baseEditButton();
        }
    }

    private Button baseEditButton() {
        Button button = new Button();
        button.getStyleClass().add("select_button");
        button.setPrefWidth(12*3);
        button.setPrefHeight(16*3);

        return button;
    }

    private Button createRemoveMonsterButton() {
        Button button = baseEditButton();

        button.setOnAction((ActionEvent event) -> {
            Button current = (Button) event.getSource();
            ComboBox cBox = editButtons.get(current);
            String monster = (String) cBox.getValue();            
            int index = cBox.getItems().indexOf(monster);

            if (index >= 0) {
                controller.removeMonsterFromCurrent(monster, index);
                secondaryStage.close();
            }
        });

        return button;
    }

    private Button createAddMonsterButton() {
        Button button = baseEditButton();

        button.setOnAction((ActionEvent event) -> {
            Button current = (Button) event.getSource();
            ComboBox cBox = editButtons.get(current);
            String monster = (String) cBox.getValue();

            if (monster.length() != 0) {
                controller.addMonsterToCurrent(monster);
                secondaryStage.close();
            }
        });

        return button;
    }

    private Button createRemoveTreasureButton() {
        Button button = baseEditButton();

        button.setOnAction((ActionEvent event) -> {
            Button current = (Button) event.getSource();
            ComboBox cBox = editButtons.get(current);
            String treasure = (String) cBox.getValue();            
            int index = cBox.getItems().indexOf(treasure);

            if (index >= 0) {
                controller.removeTreasureFromCurrent(treasure, index);
                secondaryStage.close();
            }
        });

        return button;
    }

    private Button createAddTreasureButton() {
        Button button = baseEditButton();

        button.setOnAction((ActionEvent event) -> {
            Button current = (Button) event.getSource();
            ComboBox cBox = editButtons.get(current);
            String treasure = (String) cBox.getValue();

            if (treasure.length() != 0) {
                controller.addTreasureToCurrent(treasure);
                secondaryStage.close();
            }
        });

        return button;
    }

    private MenuItem createSaveItem() {
        MenuItem save = new MenuItem("Save...");

        save.setOnAction((ActionEvent event) -> {
            prompt();
        });

        return save;
    }

    private MenuItem createLoadItem() {
        MenuItem load = new MenuItem("Load...");

        load.setOnAction((ActionEvent event) -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load Level from File...");

            File loadFile = fileChooser.showOpenDialog(primaryStage);

            if (loadFile != null) {
                controller.loadLevel(loadFile);
                controller.createDisplays();

                for (ToggleButton pButton : pButtons) {
                    mainMenu.getChildren().remove(pButton);
                }
                
                addPassageButtons();
                setToChamber(0);
                cButtons.get(0).setSelected(true);
                active = "1";
            }
        });

        return load;
    }

    private void prompt() {
        TextField filename = new TextField();
        Button confirm = createButton("OK");
        VBox saveFilePane = new VBox(20, filename, confirm);

        saveFilePane.setAlignment(Pos.CENTER);
        filename.setAlignment(Pos.CENTER);

        Scene secondScene = new Scene(saveFilePane, 200, 150);
        secondScene.getStylesheets().add(getClass().getResource("popup.css").toExternalForm());

        secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(secondScene);
        secondaryStage.setTitle("Save File");
        secondaryStage.setResizable(false);

        secondaryStage.setX(primaryStage.getX() + 300);
        secondaryStage.setY(primaryStage.getY() + 200);
        secondaryStage.show();

        confirm.setOnAction((ActionEvent event) -> {
            if (filename.getText().length() > 0) {
                DirectoryChooser dirChooser = new DirectoryChooser();
                dirChooser.setTitle("Save Current Level to Directory...");
                File saveDir = dirChooser.showDialog(primaryStage);

                if (saveDir != null) {
                    controller.saveLevel(saveDir, filename.getText());
                }

                secondaryStage.close();
            }
        });
    }

    private void openEdit() {
        VBox editRoot = new VBox();
        ComboBox removableMonsters = controller.getMonsterOptions();
        ComboBox addableMonsters = controller.getAllMonsterOptions();
        ComboBox removableTreasure = controller.getTreasureOptions();
        ComboBox addableTreasure = controller.getAllTreasureOptions();

        ComboBox[] comboBoxes = new ComboBox[] {removableMonsters, addableMonsters, removableTreasure, addableTreasure};
        String[] labels = new String[] {"Remove Monster", "Add Monster", "Remove Treasure", "Add Treasure"};

        initEditRoot(editRoot, comboBoxes, labels);

        Scene secondScene = new Scene(editRoot, 526, 360);
        secondScene.getStylesheets().add(getClass().getResource("edit.css").toExternalForm());

        secondaryStage = new Stage();
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.setScene(secondScene);
        secondaryStage.setTitle("Edit Space");
        secondaryStage.setX(primaryStage.getX() + 300);
        secondaryStage.setY(primaryStage.getY() + 200);
        secondaryStage.setResizable(false);
        secondaryStage.show();
    }

    private void initEditRoot(VBox editRoot, ComboBox[] comboBoxes, String[] labels) {
        editRoot.getStyleClass().add("edit_box");

        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("edit_row");
        gridPane.setAlignment(Pos.CENTER_RIGHT);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        editRoot.getChildren().add(gridPane);

        for (int i = 0; i < 4; i++) {
            Label field = new Label();
            field.getStyleClass().add("field_label");
            gridPane.add(field, 0, i);

            gridPane.add(comboBoxes[i], 1, i);

            Button select = createConfirmEditButton(i);
            editButtons.put(select, comboBoxes[i]);
            gridPane.add(select, 2, i);

            field.setText(labels[i]);
        }
    }

    private void setToChamber(int i) {
        if (display != null) {
            view.getChildren().remove(display);
        }

        if (doorMenu != null) {
            view.getChildren().remove(doorMenu);
        }

        active = String.valueOf(i + 1);
        display = controller.getChamberDisplay(i);
        controller.setChamberText(i);
        view.add(display, 1, 0);
        createDoorMenu(controller.getChamberDoorButtons(i));
    }

    private void setToPassage(int i) {
        if (display != null) {
            view.getChildren().remove(display);
        }

        if (doorMenu != null) {
            view.getChildren().remove(doorMenu);
        }

        display = controller.getPassageDisplay(i);
        controller.setPassageText(i);
        view.add(display, 1, 0);
        createDoorMenu(controller.getPassageDoorButtons(i));
    }


    public int getActiveNum() {
        if (activeIsChamber()) {
            return Integer.parseInt(active);
        } else {
            return Integer.parseInt(active.split(" ")[1]);
        }
    }

    private String getActive() {
        return active;
    }

    public SpaceGrid getDisplay() {
        return display;
    }

    public boolean activeIsChamber() {
        return !active.contains("Passage");
    }

    public void setTitle(String title) {
        spaceTitle.setText(title);
    }

    public void setDescription(String description) {
        spaceDesc.setText(description);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
