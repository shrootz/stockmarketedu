package stockmarketedu;

import java.util.Date;

public class Stock {
	private String name;
	private float price;
	private String symbol;
	private Date timeStamp;
	private float dividendShare;
	private boolean active;
	
	public Stock(){
		
	}
	
	public String getName() {
		return name;
	}
	public float getPrice() {
		return price;
	}
	public String getSymbol() {
		return symbol;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public float getDividendShare() {
		return dividendShare;
	}
	public void updateStock(float price, Date timeStamp, float dividendShare){
		this.price = price;
		this.timeStamp = timeStamp;
		this.dividendShare = dividendShare;
	}
}
