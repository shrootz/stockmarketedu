package stockmarketedu;

import java.util.ArrayList;
import java.util.HashMap;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.ObjectifyService;

@Entity
public abstract class Market {
	private boolean openMarket;
	protected HashMap<String, Stock> market;
	private static Market myMarket = null;

	protected Market(){
		openMarket = false;
		market = new HashMap<String, Stock>();
		//myMarket = new MarketAdapter();
	}
	public void openMarket(){
		openMarket = true;
	}
	public void closeMarket(){
		openMarket = false;
	}
	public static Market getInstance() {
		if(myMarket == null){
			myMarket = new MarketAdapter();
			ObjectifyService.ofy().save().entity(myMarket).now();
		}
	    return myMarket;
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
