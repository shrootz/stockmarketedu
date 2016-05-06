package stockmarketedutest;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Student implements Comparable{
	private static final Logger _logger = Logger.getLogger(Student.class.getName());
	protected ArrayList<Position> portfolio;
	protected String email; // do we need to store this info for gmail login?
	protected String name; // students should know their name
	protected double cashMoney;
	protected ArrayList<History> myHistory;

	
	public Student(String name, String email, double cashMoney){
		this.name = name;
		this.email = email;
		this.cashMoney = cashMoney;
		this.myHistory = new ArrayList<History>();
		this.portfolio = new ArrayList<Position>();
	}
	
	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}
	
	public String getCashMoney() {
		NumberFormat formatter = new DecimalFormat("#0.00");
		return formatter.format(cashMoney);
	}
	
	public Position getPosition(Stock intrestedStock){
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
		for(Position myPos: portfolio){
			//dividend per share * number of shares + cashMoney
			_logger.info(Double.toString(myPos.getStockType().getDividendShare() * myPos.getShares()));
			cashMoney += (myPos.getStockType().getDividendShare() * myPos.getShares());
		}
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
		
	public void addCash(double c){
		cashMoney += c;
	}


	@Override
	public int compareTo(Object that) {
		return name.compareTo(((Student)that).name);

	}

	
	
}
