package stockmarketedu;

import java.util.ArrayList;

public class Student{
	private ArrayList<Position> portfolio;
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

}
