package stockmarketedu;

public class Position {
	private Stock stockType;
	private double pricePerShare;
	private double shares;
	
	//get info from market and create a new position
	public Position(Stock stockType, double shares){
		this.stockType = stockType;
		this.pricePerShare = stockType.getPrice();
		this.shares = shares;
	}
	
	private Position() {
		stockType = new Stock();
		pricePerShare = -1;
		shares = -1;
	}
	
	public History sellShares(double shares){
		//if you try to sell more than you have, sell everything you have
		if(this.shares < shares){
			shares = this.shares;
		}
		stockType.getPrice();
		//add to History
		History newHist = new History(stockType.getSymbol(), shares, pricePerShare, stockType.getPrice());
		//update number of shares
		this.shares -= shares;
		return newHist;
	}
	
	public Stock getStockType() {
		return stockType;
	}
	public double getPriceBought() {
		return pricePerShare;
	}


	public double getShares() {
		return shares;
	}
	
	public void addShares(double shares) {
		//update price per share
		double totalPrice = (pricePerShare * this.shares + stockType.getPrice() * shares);
		this.shares = (this.shares + shares);
		pricePerShare = totalPrice/this.shares;
	}
}
