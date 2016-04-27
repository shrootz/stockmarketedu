package stockmarketedu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.ObjectifyService;

@Entity
public abstract class Market {
	@Id Long id;
	private boolean openMarket;
	protected HashMap<String, Stock> market;
	private static Market myMarket = null;
	private static final String[] stockTicker = {"GOOG", "AAPL", "FB", "NFLX", "CVS", "DAL", "SPY", "SAVE", "LUV", "TGT", 
	                 							"JNJ", "M", "PEP", "RCL", "HD", "BRKB", "SBUX", "LMT", "INTC", "ETSY", 
	                							"QQQ", "SHAK", "UCO", "BA", "V", "PAY" , "MA", "FIS", "PYPL" ,"AXP", "HAWK",
	                							"WMT", "WAL", "GE", "GM", "PG", "IBM" , "AMD", "TSLA", "MSFT" ,"LNKD", "ABT",
	                							"ANF", "C", "KO", "F", "FIT", "GLD", "SLV", "DIS", "DPS"};

	protected Market(){
		openMarket = false;
		market = new HashMap<String, Stock>();
		for(String s: stockTicker){
			addStock(s);
		}
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
			List<Market> market = ObjectifyService.ofy().load().type(Market.class).list();
			if(market.isEmpty()){
				myMarket = new MarketAdapter();
				ObjectifyService.ofy().save().entity(myMarket).now();
			}
			else{
				myMarket = market.get(0);
			}
		}
	    return myMarket;
	}
	//returns false if failed. most likely cause of failure will be invalid symbol
	public abstract boolean addStock(String stockSymbol);
	
	//if the query returns anything invalid, update the stock to be invalid and sets the stock price to zero
	//basically it is useless for the person to have in their portfolio 
	public abstract void updateStock();
	
	public Stock getStock(String symbol){
		//System.out.println(market.get(symbol).getName());
		return market.get(symbol);
		
	}
	
	public ArrayList<String> getStockSymbols(){
		ArrayList<String> symbols = new ArrayList<String>(market.keySet());
		Collections.sort(symbols);
		return symbols;
	}
	
	public void removeDefaultStocks(){
		for(String s: stockTicker){
			market.remove(s);
		}
	}

		
}
