package stockmarketedutest;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class StockTest {
	StockStub s1;
	@Before
	public void setUp() throws Exception {
		s1 = new StockStub("Google", 10.50, "GOOG", new java.util.Date(10), 5, true);
	}

	@Test
	public void testGetName() {
		assertEquals("Google", s1.getName());
	}
	
	@Test
	public void testGetPrice(){
		assertEquals(10.50, s1.getPrice(), 0.01);
	}
	
	@Test
	public void testGetSymbol(){
		assertEquals("GOOG", s1.getSymbol());
	}
	
	@Test
	public void testGetTimeStamp(){
		java.util.Date d = new java.util.Date(10);
		assertEquals(d, s1.getTimeStamp());
	}
	
	@Test
	public void testGetDividendShare(){
		assertEquals(5, s1.getDividendShare(), 0.01);
	}
	
	@Test
	public void testUpdateStock(){
		StockStub s2 = new StockStub("Google", 12, "GOOG", new java.util.Date(100), 12, true);
		s1.updateStock(12, new java.util.Date(100), 12);
		
		assertEquals(s2.getDividendShare(), s1.getDividendShare(), 0.01);
		assertEquals(s2.getPrice(), s1.getPrice(), 0.01);
		assertEquals(s2.getTimeStamp(), s1.getTimeStamp()); 
	}
	
	@Test
	public void testEquals(){
		StockStub s1 = new StockStub("Google", 12, "GOOG", new Date(), 12, true);
		StockStub s2 = new StockStub("Google", 12, "GOOG", new Date(), 12, true);
		StockStub s3 = new StockStub("Google Inc", 12, "GOOG", new Date(), 12, true);
		StockStub s4 = new StockStub("Goog", 12, "GOOGL", new Date(), 12, true);
		StockStub s5 = new StockStub("Alphabet", 12, "GOOG", new Date(), 12, true);
		assertEquals(s1, s2);
		assertEquals(s1, s3);
		assertEquals(s2, s3);
		assertEquals(s2, s3);
		assertEquals(s3, s1);
		assertEquals(s3, s2);
		assertNotEquals(s4, s3);
		assertNotEquals(s4, s1);
		assertNotEquals(s4, s2);
		assertNotEquals(s4, s5);
		assertNotEquals(s5, s1);
		
	}

}
