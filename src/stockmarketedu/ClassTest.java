package stockmarketedu;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ClassTest {
	Class c;
	Stock s1;
	Stock s2;
	Stock s3;
	@Before
	public void setUp() throws Exception {
		c = new Class("8675309");
		s1 = new Stock("Google", 10.50, "GOOG", new java.util.Date(), 5, true);
		s2 = new Stock("Facebook", 10.50, "FB", new java.util.Date(), 5, true);
		s3 = new Stock("Netflix", 10.50, "NFLX", new java.util.Date(), 5, true);
	}
	
	@Test
	public void testGetAccessCode() {
		assertEquals(c.getAccessCode(), "8675309");
	}
	
	@Test
	public void testAddStudent(){		
		Student sn = new Student("Sneha", "Email", 8675309);
		Student sh = new Student("Shruti", "Email",8675309);
		Student r = new Student("Ross", "Email",8675309);
		Student g = new Student("G", "Email", 8675309);
		
		c.addStudent(sn);
		c.addStudent(sh);
		c.addStudent(r);
		c.addStudent(g);
		
		java.util.ArrayList<Student> s = c.getMyClass(); // list of students for the specific class
		
		assertTrue(s.contains(sn));
		assertTrue(s.contains(sh)); // does s contain the students I just added
		assertTrue(s.contains(r));
		assertTrue(s.contains(g));
		
		s.remove(r); // student misbehaving in class; can I remove student from game
		assertFalse(s.contains(r));
		
		s.clear(); // all students were being really bitchy apparently, so no fun for them
		assertTrue(s.isEmpty());
		
	}
	
	@Test
	public void testMoney(){ 
		c.setInitialMoney(1000f);
		assertTrue(c.getInitialMoney() == 1000f);
	}
	
	//This test needs integration testing with objectify
	/*@Test
	public void testAddStock(){ // Market myMarket = Market.getInstance() is giving me problems here and I don't know why
		c.addStock("GOOG");
		c.addStock("FB");
		c.addStock("NFLX");
		
		for (Stock s : c.getStocksAllowed()){
			assertTrue(c.getStocksAllowed().contains(s));
		}
		
	} */
	
	
	

}
