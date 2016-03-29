package stockmarketedu;

import java.util.Date;

public class History {
	private String stockSymbol;
	private Date dateBought;
	private Date dateSold;
	private double shares;
	private double priceBought; //perShare
	private double priceSold; //perShare
	
	public History(String stockSymbol, Date dateBought,double shares, double priceBought){
		this.stockSymbol = stockSymbol;
		this.dateBought = dateBought;
		this.shares = shares;
		this.priceBought = priceBought;
	}
	
	public void buy(double shares, double priceBought){
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
	}
	
	public String getStockSymbol(){
		return stockSymbol;
	}
}
