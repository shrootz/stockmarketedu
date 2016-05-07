package stockmarketedutest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class HistoryTest {
	HistoryStub h;
	@Before
	public void setUp() throws Exception {
		h = new HistoryStub("GOOG", 50.5, 10, 20);
	}

	@Test
	public void testGetStockSymbol() {
		assertEquals(h.getStockSymbol(), "GOOG");
	}
	
	@Test
	public void testGetShares() {
		assertEquals(h.getShares(), 50.5, 0.01);
	}
	
	@Test
	public void testGetPriceBought() {
		assertEquals(h.getPriceBought(), 10.0, 0.01);
	}
	
	@Test
	public void testGetPriceSold() {
		assertEquals(h.getPriceSold(), 20.0, 0.01);
	}

}
