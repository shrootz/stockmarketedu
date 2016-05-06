package stockmarketedutest;

public class PositionStub {
	private StockStub stockType;
	private double pricePerShare;
	private double shares;
	
	//get info from market and create a new position
	public PositionStub(StockStub stockType, double shares){
		this.stockType = stockType;
		this.pricePerShare = stockType.getPrice();
		this.shares = shares;
	}
	
	private PositionStub() {
		stockType = new StockStub();
		pricePerShare = -1;
		shares = -1;
	}
	
	public HistoryStub sellShares(double shares, StockStub updated){
		//if you try to sell more than you have, sell everything you have
		if(this.shares < shares){
			shares = this.shares;
		}
		updated.getPrice();
		//add to History
		HistoryStub newHist = new HistoryStub(stockType.getSymbol(), shares, pricePerShare, updated.getPrice());
		//update number of shares
		this.shares -= shares;
		return newHist;
	}
	
	public StockStub getStockType() {
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
