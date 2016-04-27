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
	
	public History sellShares(double shares){
		//sell part/all of the shares from this position
		if(this.shares < shares){
			//throw some exception
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
