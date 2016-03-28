package stockmarketedu;

import java.util.ArrayList;

public class Student{
	private ArrayList<Position> portfolio;
	private String email; // every student should probably have an email address in this generation
	private String name; // students should know their name
	private float cashMoney;
	private ArrayList<History> myHistory;
	public Student(float cashMoney){
		this.cashMoney = cashMoney;
	}
	public void buyPosition(Stock toBuy, float shares){
		//get info from market and create a new position
	}
	public void sellPosition(Stock toSell, float shares){
		//find the position for this stock and remove from portfolio, add this to history
	}
	public ArrayList<Position> getPortfolio() {
		return portfolio;
	}
	public String getEmail(){ return email; }
	
	public String getName() { return name; }

}
