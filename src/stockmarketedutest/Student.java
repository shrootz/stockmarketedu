package stockmarketedutest;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Student{
	private static final Logger _logger = Logger.getLogger(Student.class.getName());
	protected ArrayList<PositionStub> portfolio;
	protected String email; // do we need to store this info for gmail login?
	protected String name; // students should know their name
	protected double cashMoney;
	protected ArrayList<HistoryStub> myHistory;

	
	public Student(String name, String email, double cashMoney){
		this.name = name;
		this.email = email;
		this.cashMoney = cashMoney;
		this.myHistory = new ArrayList<HistoryStub>();
		this.portfolio = new ArrayList<PositionStub>();
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
	
	public PositionStub getPosition(StockStub intrestedStock){
		String myStockName = intrestedStock.getName();
		for(PositionStub p: portfolio){
			String stockName = p.getStockType().getName();
			if (stockName.equals(myStockName)){
				return p;
			}
		}
		return null;
	}
	
	public ArrayList<PositionStub> getPortfolio() {
		return portfolio;
	}

	public ArrayList<HistoryStub> getMyHistory() {
		return myHistory;
	}
	
	public double getMaxProfitableSale(){
		double profit = Double.NEGATIVE_INFINITY;
		for(HistoryStub h: myHistory){
			double change = h.getShares() * (h.getPriceBought() - h.getPriceSold());
			if (change > profit){
				profit = change;
			}
		}
		return profit;
	}

	public double getMaxProfitPerShare(){
		double profit = Double.NEGATIVE_INFINITY;
		for(HistoryStub h: myHistory){
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


	
	
}
