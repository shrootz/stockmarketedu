package stockmarketedutest;

import java.util.Date;

public class HistoryStub {
	private String stockSymbol;
	private double shares;
	private double priceBought; //perShare
	private double priceSold; //perShare
	
	public HistoryStub(String stockSymbol, double shares, double priceBought, double priceSold){
		this.stockSymbol = stockSymbol;
		this.shares = shares;
		this.priceBought = priceBought;
		this.priceSold = priceSold;
	}

	
/*	public void buy(double shares, double priceBought){
		double totalExpenditure = this.shares * this.priceBought;
		totalExpenditure = totalExpenditure + shares*priceBought;
		this.shares = this.shares + shares;
		this.priceBought = totalExpenditure/(this.shares);
	}
	
	public void sell(double shares, double priceSold){
		double totalExpenditure = this.shares * this.priceSold;
		totalExpenditure = totalExpenditure + shares*priceSold;
		this.shares = this.shares - shares;
		this.priceSold = totalExpenditure/(this.shares);
	}*/
	
	public String getStockSymbol(){
		return stockSymbol;
	}

	public double getShares() {
		return shares;
	}

	public double getPriceBought() {
		return priceBought;
	}

	public double getPriceSold() {
		return priceSold;
	}
	
	public double getCashFromSale(){
		return priceSold*shares;
	}

}
