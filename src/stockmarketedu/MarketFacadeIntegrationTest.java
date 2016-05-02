package stockmarketedu;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

//This class tests market's integration with yahoo finance api
public class MarketFacadeIntegrationTest {
	

   MarketFacade myMarket;
   @Before
   // Informs JUnit that this method should be run before each test
   public void setUp() {
       myMarket = new MarketFacade();
       myMarket.removeDefaultStocks();
   }
	   /*
	    * Make sure market is able to add stocks for valid NASDAQ
	    */
	@Test
	public void testAddingNASDAQStocks() {
	    assertTrue(myMarket.addStock("GOOG")); //google
	    assertTrue(myMarket.addStock("FB")); //facebook
	    assertTrue(myMarket.addStock("AAPL")); //apple
	    assertTrue(myMarket.addStock("NFLX")); //netflix
	}
	/*
	 * Test if market is able to add valid stocks from NYSE
	 */
	@Test
	public void testAddingNYSEStocks(){
		assertTrue(myMarket.addStock("DIS")); //disney
	    assertTrue(myMarket.addStock("TGT")); //target
	    assertTrue(myMarket.addStock("JNJ")); //johnson and johnson
	    assertTrue(myMarket.addStock("M")); //macy's
	    assertTrue(myMarket.addStock("PEP")); //pepsi
	    assertTrue(myMarket.addStock("RCL")); //royal carribean cruises 
	    assertTrue(myMarket.addStock("HD")); //home depot
	    assertTrue(myMarket.addStock("BRKB")); //berkshire B (Warren Buffet's company for lay people)
	
	}
	
	/*
	 * Stocks not from NASDAQ or NYSE are not supported by Yahoo! Finance atm, might change later
	 */
	@Test
	public void testAddingStocksFromOtherExchanges(){
	    assertFalse(myMarket.addStock("LHA")); //ETR exchange
	    assertFalse(myMarket.addStock("NESN")); //VTX exchange
	}
	
	/*
	 * Make sure market fails adding stocks when invalid symbols are given
	 */
	@Test
	public void testAddingInvalidStocks(){
		   assertFalse(myMarket.addStock("BDJKAS"));
	       assertFalse(myMarket.addStock("NOTAREALSTOCK"));
	       assertFalse(myMarket.addStock("HELLOFRIEND"));
	       assertFalse(myMarket.addStock("this should not work"));
	       assertFalse(myMarket.addStock("12345"));
	       assertFalse(myMarket.addStock("!@#$!#!abis0d")); 
	}
	
	/*
	 * Make sure same stock doesn't get added multiple times
	 */
	@Test
	public void testAddingSameStock(){
		assertTrue(myMarket.addStock("FB")); 
	    assertFalse(myMarket.addStock("FB")); 
	    assertTrue(myMarket.addStock("GOOG")); 
	    assertFalse(myMarket.addStock("FB")); 
	    assertFalse(myMarket.addStock("GOOG")); 
	    assertTrue(myMarket.addStock("DIS")); 
	    assertFalse(myMarket.addStock("FB")); 
	    assertFalse(myMarket.addStock("DIS")); 
	    assertTrue(myMarket.addStock("AAPL")); 
	    assertFalse(myMarket.addStock("DIS")); 
	    ArrayList<String> expected = new ArrayList<String>();
	    expected.add("AAPL");
	    expected.add("DIS");
	    expected.add("FB");
	    expected.add("GOOG");
	    assertEquals(myMarket.getStockSymbols(), expected);
	}
	
	/*
	 * Test Updating Stocks
	 * NOTE: HOLIDAYS IS GOING TO MESS THIS TEST UP OKAY THX
	 */
	@Test
	public void testUpdatingStocks(){
		boolean open = false;
		try{
		    String string1 = "8:30:00";
		    Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);
		    Calendar calendar1 = Calendar.getInstance();
		    calendar1.setTime(time1);
	
		    String string2 = "16:30:00";
		    Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
		    Calendar calendar2 = Calendar.getInstance();
		    calendar2.setTime(time2);
		    
		    Date x = new Date();
		    
		    if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
		        open = true;
		    }
			
		}
		catch(Exception exe){
			System.out.println("Problem with test");
		}
	    
	    
		Calendar calendar = Calendar.getInstance();
		
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String[] stocks = {"GOOG", "AAPL", "FB", "NFLX", "CVS", "DAL", "SPY", "SAVE", "LUV", "TGT", 
							"JNJ", "M", "PEP", "RCL", "HD", "BRKB", "SBUX", "LMT", "INTC", "ETSY", 
							"QQQ", "SHAK", "UCO", "BA", "V", "PAY" , "MA", "FIS", "PYPL" ,"AXP", "HAWK",
							"WMT", "WAL", "GE", "GM", "PG", "IBM" , "AMD", "TSLA", "MSFT" ,"LNKD", "ABT",
							"ANF", "C", "KO", "F"};
		HashMap<String, Double> oldPrices = new HashMap<String, Double>();
		for (String s: stocks){
			myMarket.addStock(s);
			oldPrices.put(s, myMarket.getStock(s).getPrice());
		}
		if(dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY){ ///saturday or sunday or market is closed
			myMarket.updateStock();
			int same = 0;
			for(String key: oldPrices.keySet()){
				Double oldPrice = oldPrices.get(key);
				Double newPrice = myMarket.getStock(key).getPrice();
				if(Math.abs(oldPrice - newPrice) < 0.02){ //less than 2c change, say it's working b/c of after hour tradings 
					same += 1;
				}
			}
			System.out.println(same);
			assertTrue(same > (int) stocks.length * 0.75);
		}
		else if(!open){//allow larger margin b/c more after hours trading on weekdays
			myMarket.updateStock();
			int same = 0;
			for(String key: oldPrices.keySet()){
				Double oldPrice = oldPrices.get(key);
				Double newPrice = myMarket.getStock(key).getPrice();
				if(Math.abs(oldPrice - newPrice) < 0.10){ //less than 2c change, say it's working b/c of after hour tradings 
					same += 1;
				}
			}
			System.out.println(same);
			assertTrue(same > (int) stocks.length * 0.75);
		}
		else{
			myMarket.updateStock();
			int diff = 0;
			for(String key: oldPrices.keySet()){
				if(Math.abs(oldPrices.get(key) - myMarket.getStock(key).getPrice()) > 0.01){ //more than 1c change
					diff += 1;
				}
			}
			assertTrue(diff > (int) 0.75 * stocks.length);
		}
	}
	
	/*
	 * Check if stock names are retrieved correctly. Due to some bugs with Yahoo! finance API itself, only checking for approximate names
	 */
	@Test
	public void testNameParsing(){
		String [] stocks = {"GOOG", "AAPL", "FB", "NFLX", "LUV", "DAL", "INTC"};
		for(String s: stocks){
			myMarket.addStock(s);
		}
		
		assertTrue(myMarket.getStock("GOOG").getName().contains("Alphabet"));
		assertTrue(myMarket.getStock("AAPL").getName().contains("Apple"));
		assertTrue(myMarket.getStock("FB").getName().contains("Facebook"));
		assertTrue(myMarket.getStock("NFLX").getName().contains("Netflix"));
		assertTrue(myMarket.getStock("DAL").getName().contains("Delta"));
		assertTrue(myMarket.getStock("LUV").getName().contains("Southwest"));
		assertTrue(myMarket.getStock("INTC").getName().contains("Intel"));
	
	}
	
	/*
	 * Test Time Stamps on Updating
	 */
	@Test
	public void testTimeStamps(){
		
		String[] stocks = {"GOOG", "AAPL", "FB", "NFLX", "CVS", "DAL", "SPY", "SAVE", "LUV", "TGT", 
							"JNJ", "M", "PEP", "RCL", "HD", "BRKB", "SBUX", "LMT", "INTC", "ETSY", 
							"QQQ", "SHAK", "UCO", "BA", "V", "PAY" , "MA", "FIS", "PYPL" ,"AXP", "HAWK",
							"WMT", "WAL", "GE", "GM", "PG", "IBM" , "AMD", "TSLA", "MSFT" ,"LNKD", "ABT",
							"ANF", "C", "KO", "F"};
		for (String s: stocks){
			myMarket.addStock(s);
		}
		Date now = new Date();
		myMarket.updateStock();
		System.out.println("delay");
		Date later = new Date();
		System.out.println(now);
		System.out.println(later);
		for(String s: stocks){
			assertTrue(myMarket.getStock(s).getTimeStamp().after(now) && myMarket.getStock(s).getTimeStamp().before(later) || myMarket.getStock(s).getTimeStamp().equals(now) || myMarket.getStock(s).getTimeStamp().equals(later));
		}
	}


}
