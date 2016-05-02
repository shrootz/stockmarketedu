package stockmarketedu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class MarketFacade extends Market {

	@Override
	public boolean addStock(String stockSymbol) {
		if (market.containsKey(stockSymbol)){
			return false;
		}
		try {
			String text = getQueryText(stockSymbol);
		    //System.out.println(text);
		    String name = text.substring(text.indexOf("<Name>") + "<Name>".length(), text.indexOf("</Name>"));
		    double price = Double.parseDouble(text.substring(text.indexOf("<LastTradePriceOnly>") + "<LastTradePriceOnly>".length(), text.indexOf("</LastTradePriceOnly>")));
		    Date date = new Date(); //for now setting date as time of last update
		    //String time = text.substring(text.indexOf("<LastTradeTime>") + "<LastTradeTime>".length(), text.indexOf("</LastTradeTime>"));
		    //String day = 
		    String dividendShareText = null;
		    if(text.contains("DividendShare") && text.contains("</DividendShare>")){
		    	dividendShareText = text.substring(text.indexOf("<DividendShare>") + "<DividendShare>".length(), text.indexOf("</DividendShare>"));
		    }
		    double dividend = 0.0;
		    if (dividendShareText != null && !dividendShareText.equals("")){
		    	dividend = Double.parseDouble(dividendShareText)/4.0;
		    }
		    boolean active = true;
		    //System.out.println(name + price + stockSymbol + date + dividend + active);
		    Stock s = new Stock(name, price, stockSymbol, date, dividend, active);
			market.put(stockSymbol, s);
			return true;
			
		} catch (Exception e) {
		    //System.out.println(e.getMessage());
		    return false;
		        
		}

	}

	@Override
	public void updateStock() {
		// update all the stocks in the market using YQL
	    for(String symbol: market.keySet()){
	    	try {
				String text = getQueryText(symbol);
			    double price = Double.parseDouble(text.substring(text.indexOf("<LastTradePriceOnly>") + "<LastTradePriceOnly>".length(), text.indexOf("</LastTradePriceOnly>")));
			    Date date = new Date(); 
			    String dividendShareText = null;
			    if(text.contains("DividendShare") && text.contains("</DividendShare>")){
			    	dividendShareText = text.substring(text.indexOf("<DividendShare>") + "<DividendShare>".length(), text.indexOf("</DividendShare>"));
			    }
			    double dividend = 0.0;
			    if (dividendShareText != null && !dividendShareText.equals("")){
			    	dividend = Double.parseDouble(dividendShareText);
			    }
			    market.get(symbol).updateStock(price, date, dividend);
			  

			    

			} catch (Exception e) {
			    // ...
				System.out.println(e.getMessage());
				market.get(symbol).setInactive();
				
			} 
			
	    }
	}
	
	private String getQueryText(String stockSymbol){
		try {
			URL url = new URL("http://query.yahooapis.com/v1/public/yql?q=select%20%2a%20from%20yahoo.finance.quotes%20where%20symbol%20in%20%28%22" + stockSymbol + "%22%29&env=store://datatables.org/alltableswithkeys");
		    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
		    String line;
		    String text = "";
		    while ((line = reader.readLine()) != null) {
		        text += line + '\n';
		    }
		    reader.close();
		    return text;

		    

		} catch (MalformedURLException e) {
		    // ...
			System.out.println(e.getMessage());
			return null;
			
		} catch (IOException e) {
		    System.out.println(e.getMessage());
		    return null;
		    
		}
	}
	

}
