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

	
	public Student(String name, String accessCode){
		this.name = name;
		this.myHistory = new ArrayList<History>();
		try{
			List<Supervisor> allSupervisor = ObjectifyService.ofy().load().type(Supervisor.class).list();
			for(Supervisor s: allSupervisor){
				Class c = s.getClassroom();
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
			//class not found exception <-- need a class for this?
		}
	}
	
	//need to handle errors here - Stock does not exist, not enough money
	public boolean buyPosition(Stock toBuy, double shares){
		Position currentPosition = getPosition(toBuy);
		double costToBuy = toBuy.getPrice() * shares;
		cashMoney = cashMoney - costToBuy;
		if(cashMoney < 0){
			cashMoney = cashMoney + costToBuy;
			//money problems
			return false;
		}
		if(currentPosition == null){
			currentPosition = new Position(toBuy, shares);
			portfolio.add(currentPosition);
		}
		else{
			currentPosition.addShares(shares);
		}
		return true;
	}
	
	//need to handle errors here - Stock does not exist, not enough shares to sell
	public void sellPosition(Stock toSell, double shares){
		Position currentPosition = getPosition(toSell);
		History currentHistory = currentPosition.sellShares(shares);
		if(currentPosition.getShares() <= 0){
			portfolio.remove(currentPosition);
		}
		myHistory.add(currentHistory);
	}
	
	/*private History getHistory(Stock intrestedStock){
		String myStockName = intrestedStock.getName();
		for(History h: myHistory){
			String stockName = h.getStockSymbol();
			if (stockName.equals(myStockName)){
				return h;
			}
		}
		return null;
	}*/
	
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
	
	//called from a cron job, goes through the entire portfolio and adds to the cashMoney value
	public void recieveDividends(){
		
	}
	
	public ArrayList<Position> getPortfolio() {
		return portfolio;
	}

	public ArrayList<History> getMyHistory() {
		return myHistory;
	}
	
	public double getMaxProfitableSale(){
		double profit = Double.NEGATIVE_INFINITY;
		for(History h: myHistory){
			double change = h.getShares() * (h.getPriceBought() - h.getPriceSold());
			if (change > profit){
				profit = change;
			}
		}
		return profit;
	}
	
	public double getMoney(){
		double totMoney = cashMoney;
		for(Position p: portfolio){
			totMoney += p.getPriceBought() * p.getShares();
		}
		return totMoney;
	}
	public double getMaxProfitPerShare(){
		double profit = Double.NEGATIVE_INFINITY;
		for(History h: myHistory){
			double change = h.getPriceBought() - h.getPriceSold();
			if (change > profit){
				profit = change;
			}
		}
		return profit;
	}
	
}
