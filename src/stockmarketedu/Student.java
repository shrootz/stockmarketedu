package stockmarketedu;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.googlecode.objectify.ObjectifyService;

public class Student{
	private ArrayList<Position> portfolio;
	private String email; // do we need to store this info for gmail login?
	private String name; // students should know their name
	private double cashMoney;
	private ArrayList<History> myHistory;
	private ArrayList<History> workingHistory;

	
	public Student(String name, String accessCode){
		this.name = name;
		this.myHistory = new ArrayList<History>();
		try{
			List<Class> allClasses = ObjectifyService.ofy().load().type(Class.class).list();
			for(Class c: allClasses){
				String classCode = c.getAccessCode();
				if(classCode.equals(accessCode)){
					c.addStudent(this);
					this.cashMoney = c.getInitialMoney();
				}
			}
			if(this.cashMoney == 0.0f){
				Exception e = new Exception ();
				throw e;
			}
		}
		catch(Exception e){
			//student not found exception <-- need a class for this?
		}
	}
	
	//need to handle errors here - Stock does not exist, not enough money
	public void buyPosition(Stock toBuy, double shares){
		Position currentPosition = getPosition(toBuy);
		History currentHistory = getHistory(toBuy);
		double costToBuy = toBuy.getPrice() * shares;
		cashMoney = cashMoney - costToBuy;
		if(cashMoney < 0){
			cashMoney = cashMoney + costToBuy;
			//throw some error here --> new exception, money problems?
		}
		if(currentPosition == null){
			currentPosition = new Position(toBuy, shares);
			currentHistory = new History(toBuy.getName(), toBuy.getTimeStamp(), shares, toBuy.getPrice());
			portfolio.add(currentPosition);
			workingHistory.add(currentHistory);
		}
		else{
			currentPosition.addShares(shares);
			currentHistory.buy(shares, toBuy.getPrice());
		}
	}
	
	//need to handle errors here - Stock does not exist, not enough shares to sell
	public void sellPosition(Stock toSell, double shares){
		Position currentPosition = getPosition(toSell);
		History currentHistory = getHistory(toSell);
		currentPosition.sellShares(shares);
		currentHistory.sell(shares, toSell.getPrice());
		if(currentPosition.getShares() <= 0){
			portfolio.remove(currentPosition);
			workingHistory.remove(currentHistory);
		}
	}
	
	private History getHistory(Stock intrestedStock){
		String myStockName = intrestedStock.getName();
		for(History h: myHistory){
			String stockName = h.getStockSymbol();
			if (stockName.equals(myStockName)){
				return h;
			}
		}
		return null;
	}
	
	private Position getPosition(Stock intrestedStock){
		String myStockName = intrestedStock.getName();
		for(Position p: portfolio){
			String stockName = p.getStockType().getName();
			if (stockName.equals(myStockName)){
				return p;
			}
		}
		return null;
	}
	
	public ArrayList<Position> getPortfolio() {
		return portfolio;
	}

	public ArrayList<History> getMyHistory() {
		return myHistory;
	}
}
