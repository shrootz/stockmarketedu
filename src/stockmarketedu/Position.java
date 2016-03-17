package stockmarketedu;

import java.util.Date;

public class Position {
	private Stock stockType;
	private float priceBought;
	private Date dateBought;
	private float shares;
	
	public Position(Stock stockType, float shares){
		this.stockType = stockType;
		this.dateBought = stockType.getTimeStamp();
		this.priceBought = stockType.getPrice();
	}
	
	public void sellShares(){
		//sell part/all of the shares from this position
	}
	public Stock getStockType() {
		return stockType;
	}
	public float getPriceBought() {
		return priceBought;
	}

	public Date getDateBought() {
		return dateBought;
	}

	public float getShares() {
		return shares;
	}

	public void setShares(float shares) {
		this.shares = shares;
	}
}
