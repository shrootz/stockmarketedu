package yahoofinancequerytest;

import java.util.Date;

public class Stock {
	private String name;
	private double price;
	private String symbol;
	private Date timeStamp;
	private double dividendShare;
	private boolean active;
	
	public Stock(String name, double price, String symbol, Date timeStamp, double dividendShare, boolean active){
		this.name = name;
		this.symbol = symbol;
		this.price = price;
		this.timeStamp = timeStamp;
		this.dividendShare = dividendShare;
		this.active = active;
	}
	
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public String getSymbol() {
		return symbol;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public double getDividendShare() {
		return dividendShare;
	}
	public void updateStock(double price, Date timeStamp, double dividendShare){
		this.price = price;
		this.timeStamp = timeStamp;
		this.dividendShare = dividendShare;
	}
	
	public void setInactive(){
		active = false;
	}
}