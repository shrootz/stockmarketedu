package stockmarketedutest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HistoryTest {
	History h;
	@Before
	public void setUp() throws Exception {
		h = new History("GOOG", 50.5, 10, 20);
	}

	@Test
	public void testGetStockSymbol() {
		assertEquals(h.getStockSymbol(), "GOOG");
	}
	
	@Test
	public void testGetShares() {
		assertTrue(h.getShares() == 50.5);
	}
	
	@Test
	public void testGetPriceBought() {
		assertTrue(h.getPriceBought() == 10);
	}
	
	@Test
	public void testGetPriceSold() {
		assertTrue(h.getPriceSold() ==  20);
	}

}
