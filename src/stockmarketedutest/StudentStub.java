package stockmarketedutest;

public class StudentStub extends Student{
	Market globalMarket;
	public StudentStub(String name, String email, double cashMoney) {
		super(name, email, cashMoney);
	}
	public double getMoney(Market m){
		globalMarket= m;
		return getMoney();
	}
	public double getMoney(){
		double totMoney = cashMoney;
		for(Position p: portfolio){
			totMoney += globalMarket.getStock(p.getStockType().getSymbol()).getPrice() * p.getShares();
		}
		return totMoney;
	}
	public boolean sellPosition(String symbol, double shares){
	    Stock toSell = globalMarket.getStock(symbol);
		Position currentPosition = getPosition(toSell);
		History currentHistory = currentPosition.sellShares(shares, toSell);
		cashMoney += currentHistory.getCashFromSale();
		if(currentPosition.getShares() <= 0){
			portfolio.remove(currentPosition);
			return false;
		}
		myHistory.add(currentHistory);
		return true;
	}
	
	
	public void sellPosition(String symbol, double shares, Market m){
		globalMarket = m;
		sellPosition(symbol, shares);
		
	}
	
	public boolean buyPosition(String symbol, double shares){
	    Stock toBuy = globalMarket.getStock(symbol);
		Position currentPosition = getPosition(toBuy);
		double costToBuy = toBuy.getPrice() * shares;
		cashMoney = cashMoney - costToBuy;
		if(cashMoney < 0){
			cashMoney = cashMoney + costToBuy;
			//money problems
			return false;
		}
		if(currentPosition == null){
			currentPosition = new Position(toBuy, shares);
			portfolio.add(currentPosition);
		}
		else{
			currentPosition.addShares(shares);
		}
		return true;
	}
	
	public void buyPosition(String symbol, double shares, Market m){
		globalMarket = m;
		buyPosition(symbol, shares);
		
	}

}
