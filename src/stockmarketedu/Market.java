package yahoofinancequerytest;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Market {
	private boolean openMarket;
	protected HashMap<String, Stock> market;
	public Market(){
		openMarket = false;
		market = new HashMap<String, Stock>();
	}
	public void openMarket(){
		openMarket = true;
	}
	public void closeMarket(){
		openMarket = false;
	}
	
	//returns false if failed. most likely cause of failure will be invalid symbol
	public abstract boolean addStock(String stockSymbol);
	
	//if the query returns anything invalid, update the stock to be invalid and sets the stock price to zero
	//basically it is useless for the person to have in their portfolio 
	public abstract void updateStock();
	
	public Stock getStock(String symbol){
		System.out.println(market.get(symbol).getName());
		return market.get(symbol);
		
	}

		
}
