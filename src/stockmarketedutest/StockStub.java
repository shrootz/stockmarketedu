package stockmarketedutest;

import java.util.Date;

public class StockStub {
	private String name;
	private double price;
	private String symbol;
	private Date timeStamp;
	private double dividendShare;
	private boolean active;
	
	public StockStub(String name, double price, String symbol, Date timeStamp, double dividendShare, boolean active){
		this.name = name;
		this.symbol = symbol;
		this.price = price;
		this.timeStamp = timeStamp;
		this.dividendShare = dividendShare;
		this.active = active;
	}
	
	public StockStub() {
		name = null;
		price = 0;
		symbol = null;
		timeStamp = null;
		dividendShare = 0;
		active = false;
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
	
	
	@Override
	public boolean equals(Object s){
	/*	if(s.getClass() != this.getClass()){
			return false;
		}
		else{*/
			StockStub other = (StockStub) s;
			if((other.getName().contains(getName()) || getName().contains(other.getName())) && other.getSymbol().equals(getSymbol())){
				return true;
			}
			return false;
		//}		
	}
}