package database;

import java.util.ArrayList;
import java.util.Random;

public class DBMonster {
    private DBConnection db;
	private String monsterName;
	private String upperBound;
	private String lowerBound;
    private String description;
    private int type;

	public DBMonster(String name, String upper, String lower, String desc){
		setName(name);
		setUpperBound(upper);
		setLowerBound(lower);
		setDescription(desc);
	}
	
    public DBMonster() {
        
    }

    public void setName(String name){
		monsterName = name;
    }
    
	public void setUpperBound(String upper){
		upperBound = upper;
    }
    
	public void setLowerBound(String lower){
		lowerBound = lower;
    }

	public void setDescription(String desc){
		description = desc;
	}
    
    public String getName(){
		return monsterName;
	}
	public String getUpper(){
		return upperBound;
	}
	public String getLower(){
		return lowerBound;
	}
	public String getDescription(){
		return description;
    }
    
    public int getType() {
        switch (monsterName) {
            case "skeleton":
                return 1;

            case "spider":
                return 2;

            case "shrieker":
                return 3;
            
            default:
                return -1;
        }
    }

	@Override
	public String toString(){
        return String.format("%s,%s,%s,%s", monsterName, upperBound, lowerBound, description);
	}
}
