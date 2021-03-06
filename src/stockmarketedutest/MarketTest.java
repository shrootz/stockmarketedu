package stockmarketedutest;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/*
 * Simple UnitTest for market 
 * Will manually test singleton part
 */
public class MarketTest {
	MarketFacadeStub myMarket;
	   @Before
	   // Informs JUnit that this method should be run before each test
	   public void setUp() {
	       myMarket = new MarketFacadeStub();
	       myMarket.removeDefaultStocks();
	   }
	
	/*
	 * Check stock symbols
	 */
	@Test
	public void testStockSymbols(){
		String [] stocks = {"GOOG", "AAPL", "FB", "NFLX", "CVS", "DAL", "SPY"};
		for(String s: stocks){
			myMarket.addStock(s);
		}
		List<String> stocks_sorted = Arrays.asList("GOOG", "AAPL", "FB", "NFLX", "CVS", "DAL", "SPY");
		Collections.sort(stocks_sorted);
		//System.out.println(myMarket.getStockSymbols());
		assertEquals(myMarket.getStockSymbols(), stocks_sorted);
		
		
	} 
	
	/*
	 * Check get valid stocks
	 */
	@Test
	public void testGetStock(){
		String [] stocks = {"GOOG", "AAPL", "FB", "NFLX", "DAL"};
		String [] name = {"Alphabet", "Apple", "Facebook", "Netflix", "Delta"};
		for(String s: stocks){
			myMarket.addStock(s);
		}
		StockStub[] expected = new StockStub[5];
		for(int i = 0; i<expected.length; i++){
			expected[i] = new StockStub(name[i], 0.0, stocks[i], new Date(), 0.0, true);
		}
		for (int i = 0; i<expected.length; i++){
			assertEquals(expected[i], myMarket.getStock(stocks[i]));
		}
		
	}
	
	/*
	 * Test getting invalid stock
	 */
	@Test
	public void testGetInvalidStock(){
		assertEquals(null, myMarket.getStock("GOOG"));
	}
	
	/*
	 * Test getting default stocks
	 */
	@Test
	public void testDefaultStocks(){
		String [] stock = myMarket.getDefaultStocks();
		String [] expected = {"GOOG", "AAPL", "FB", "NFLX", "CVS", "DAL", "SPY", "SAVE", "LUV", "TGT", 
					"JNJ", "M", "PEP", "RCL", "HD", "BRKB", "SBUX", "LMT", "INTC", "ETSY", 
				"QQQ", "SHAK", "UCO", "BA", "V", "PAY" , "MA", "FIS", "PYPL" ,"AXP", "HAWK",
				"WMT", "WAL", "GE", "GM", "PG", "IBM" , "AMD", "TSLA", "MSFT" ,"LNKD", "ABT",
				"ANF", "C", "KO", "F", "FIT", "GLD", "SLV", "DIS", "DPS"};
		assertArrayEquals(stock, expected);
	}
}
		
	


