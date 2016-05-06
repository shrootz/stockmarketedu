package stockmarketedutest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StockTest {
	Stock s1;
	@Before
	public void setUp() throws Exception {
		s1 = new Stock("Google", 10.50, "GOOG", new java.util.Date(10), 5, true);
	}

	@Test
	public void testGetName() {
		assertTrue("Google" == s1.getName());
	}
	
	@Test
	public void testGetPrice(){
		assertTrue(10.50 == s1.getPrice());
	}
	
	@Test
	public void testGetSymbol(){
		assertTrue("GOOG" == s1.getSymbol());
	}
	
	@Test
	public void testGetTimeStamp(){
		java.util.Date d = new java.util.Date(10);
		assertEquals(d, s1.getTimeStamp());
	}
	
	@Test
	public void testGetDividendShare(){
		assertTrue(5 == s1.getDividendShare());
	}
	
	@Test
	public void testUpdateStock(){
		Stock s2 = new Stock("Google", 12, "GOOG", new java.util.Date(100), 12, true);
		s1.updateStock(12, new java.util.Date(100), 12);
		
		assertTrue(s2.getDividendShare() == s1.getDividendShare());
		assertTrue(s2.getPrice() == s1.getPrice());
		assertTrue(s2.getTimeStamp().equals(s1.getTimeStamp())); 
	}

}
