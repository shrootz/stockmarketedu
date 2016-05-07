package stockmarketedutest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MarketStub {

	private boolean openMarket;
	protected Map<String, StockStub> market;
	private static MarketStub myMarket = null;
	private static final String[] stockTicker = {"GOOG", "AAPL", "FB", "NFLX", "CVS", "DAL", "SPY", "SAVE", "LUV", "TGT", 
	                 							"JNJ", "M", "PEP", "RCL", "HD", "BRKB", "SBUX", "LMT", "INTC", "ETSY", 
	                							"QQQ", "SHAK", "UCO", "BA", "V", "PAY" , "MA", "FIS", "PYPL" ,"AXP", "HAWK",
	                							"WMT", "WAL", "GE", "GM", "PG", "IBM" , "AMD", "TSLA", "MSFT" ,"LNKD", "ABT",
	                							"ANF", "C", "KO", "F", "FIT", "GLD", "SLV", "DIS", "DPS"};

	protected MarketStub(){
		openMarket = false;
		market = new HashMap<String, StockStub>();
		for(String s: stockTicker){
			addStock(s);
		}
		//myMarket = new MarketAdapter();
	}
	
	//returns false if failed. most likely cause of failure will be invalid symbol
	public abstract boolean addStock(String stockSymbol);
	
	//if the query returns anything invalid, update the stock to be invalid and sets the stock price to zero
	//basically it is useless for the person to have in their portfolio 
	public abstract void updateStock();
	
	public StockStub getStock(String symbol){
		//System.out.println(market.get(symbol).getName());
		if(market.containsKey(symbol)){
			return market.get(symbol);
		}
		else{
			return null;
		}
		
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
	
	public String[] getDefaultStocks() {
		return stockTicker;
	}
	
	public void saveMarket(){
		//ObjectifyService.ofy().delete().entity(myMarket).now(); 
		//ObjectifyService.ofy().save().entity(myMarket).now();
	}

		
}
