package control;

import gui.LevelUI;

public class Controller {
    private LevelUI levelUI;

    public Controller(LevelUI gui){
        levelUI = gui;
    }

    private String getNameList(){
        return "hey man";
    }

    public void reactToButton(){
        System.out.println("Thanks for clicking!");
    }

    public String getNewDescription(){
        //return "this would normally be a description pulled from the model of the Dungeon level.";
        return getNameList();
    }

}
