package stockmarketedu;

import java.util.Date;

public class Position {
	private Stock stockType;
	private double priceBought;
	private Date dateBought;
	private double shares;
	
	//get info from market and create a new position
	public Position(Stock stockType, double shares){
		this.stockType = stockType;
		this.dateBought = stockType.getTimeStamp();
		this.priceBought = stockType.getPrice();
	}
	
	public void sellShares(double shares){
		//sell part/all of the shares from this position
	}
	public Stock getStockType() {
		return stockType;
	}
	public double getPriceBought() {
		return priceBought;
	}

	public Date getDateBought() {
		return dateBought;
	}

	public double getShares() {
		return shares;
	}
	
	public void addShares(double shares) {
		setShares(this.shares + shares);
	}

	public void setShares(double shares) {
		this.shares = shares;
	}
}
