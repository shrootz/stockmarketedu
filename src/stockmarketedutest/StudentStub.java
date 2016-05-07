package stockmarketedutest;

public class StudentStub extends Student{
	MarketStub globalMarket;
	public StudentStub(String name, String email, double cashMoney) {
		super(name, email, cashMoney);
	}
	public double getMoney(MarketStub m){
		globalMarket= m;
		return getMoney();
	}
	public double getMoney(){
		double totMoney = cashMoney;
		for(PositionStub p: portfolio){
			totMoney += globalMarket.getStock(p.getStockType().getSymbol()).getPrice() * p.getShares();
		}
		return totMoney;
	}
	public boolean sellPosition(String symbol, double shares){
	    StockStub toSell = globalMarket.getStock(symbol);
		PositionStub currentPosition = getPosition(toSell);
		HistoryStub currentHistory = currentPosition.sellShares(shares, toSell);
		cashMoney += currentHistory.getCashFromSale();
		if(currentPosition.getShares() <= 0){
			portfolio.remove(currentPosition);
			return false;
		}
		myHistory.add(currentHistory);
		return true;
	}
	
	
	public void sellPosition(String symbol, double shares, MarketStub m){
		globalMarket = m;
		sellPosition(symbol, shares);
		
	}
	
	public boolean buyPosition(String symbol, double shares){
	    StockStub toBuy = globalMarket.getStock(symbol);
		PositionStub currentPosition = getPosition(toBuy);
		double costToBuy = toBuy.getPrice() * shares;
		cashMoney = cashMoney - costToBuy;
		if(cashMoney < 0){
			cashMoney = cashMoney + costToBuy;
			//money problems
			return false;
		}
		if(currentPosition == null){
			currentPosition = new PositionStub(toBuy, shares);
			portfolio.add(currentPosition);
		}
		else{
			currentPosition.addShares(shares);
		}
		return true;
	}
	
	public boolean buyPosition(String symbol, double shares, MarketStub m){
		globalMarket = m;
		return buyPosition(symbol, shares);
		
	}

}
