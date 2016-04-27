package stockmarketedu;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class testStudent {
	Student student;
	Supervisor supervisor;
	@Before
	public void setUp() throws Exception {
		supervisor = new Supervisor("Che"); // manually put in an access code; 
		supervisor.getClassroom().setInitialMoney(1000f);
		student = new Student("Sneha", "8675309"); // not sure I can test to see if this works using jUnit
		supervisor.getClassroom().addStudent(student);
	}

	@Test
	public void testGetName() {
		assertEquals("Sneha", student.getName());
	}
	
	@Test
	public void testGetCashMoney(){
		String s = student.getCashMoney();
		assertEquals("1000.00", student.getCashMoney());
	}
	
	@Test
	public void testBuyPosition(){
		fail("don't know how to test this one");
	}
	
	@Test
	public void testSellPosition(){  
		fail("don't know how to test this");
		
	}
	
	@Test
	public void testGetPortfolio(){
		java.util.ArrayList<Position> expected = new java.util.ArrayList<Position>();
		assertEquals(expected, student.getPortfolio());
	}
	
	@Test
	public void testGetMyHistory(){
		java.util.ArrayList<History> expected = new java.util.ArrayList<History>();
		assertEquals(expected, student.getMyHistory());
	}
	
	@Test
	public void testGetMaxProfitableSale(){ // nothing to look for
		assertTrue(student.getMaxProfitableSale() == Double.NEGATIVE_INFINITY);
	}
	
	@Test
	public void getMoney(){
		assertTrue(1000 == student.getMoney());
	}
	
	@Test
	public void testGetMaxProfitPerShare(){ // nothing to look for
		assertTrue(student.getMaxProfitPerShare() == Double.NEGATIVE_INFINITY);
	}
	

}
