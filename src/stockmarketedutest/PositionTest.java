package stockmarketedutest;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class PositionTest {
	StockStub s;
	PositionStub pos;
	@Before
	public void setUp() throws Exception {
		s = new StockStub("Google", 10.50, "GOOG", new java.util.Date(), 5, true);
		pos = new PositionStub(s, 50); // did we forget to add this.shares = shares for the constructor?
	}

	@Test
	public void testSellShare(){
		HistoryStub h;
		//try{
			h = pos.sellShares(s.getDividendShare() + .01, new StockStub()); // this should throw an exception
		/*} catch (Exception e){
			assertTrue(true); 
		}*/
		HistoryStub expected = new HistoryStub ("GOOG", 4, 10.50, 10.50);
		h = pos.sellShares(4, new StockStub("", 10.5, "GOOG", new Date(), 0.0, true));
		
		assertEquals(expected.getStockSymbol(), h.getStockSymbol()); 
		assertEquals(expected.getPriceBought(), h.getPriceBought(), 0.01);
		assertEquals(expected.getPriceSold(), h.getPriceSold(), 0.01);
		
		assertEquals(expected.getShares(), h.getShares(), 1.0);
	}
	
	@Test
	public void testGetStockType(){
		assertEquals(s, pos.getStockType());
	}
	
	@Test
	public void testGetPriceBought(){
		assertEquals(10.50, pos.getPriceBought(), 0.01);
	}
	
	@Test
	public void testgetShares(){
		assertEquals(50, pos.getShares(), 0.01);
	}
	
	@Test
	public void testAddShare(){ 
		double num = 10.5;
		pos.addShares(7);
		assertEquals(num, pos.getPriceBought(), 0.01); 
	}

}
