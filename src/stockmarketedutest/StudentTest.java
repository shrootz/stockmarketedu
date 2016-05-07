package stockmarketedutest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StudentTest {
	Student student;
	StudentStub student_s;
	SupervisorStub supervisor;
	MarketStub m;
	@Before
	public void setUp() throws Exception {
		m = new MarketFacadeStub();
		supervisor = new SupervisorStub(); // manually put in an access code; 
		supervisor.getClassroom().setInitialMoney(1000.0);
		student = new Student("Sneha", "Email", supervisor.getClassroom().getInitialMoney()); 
		student_s = new StudentStub("Sneha", "Email", supervisor.getClassroom().getInitialMoney()); 
		student.addCash(1000.0);
		supervisor.getClassroom().addStudent(student);
	}
	@Test
	public void studentEmails(){
		assertEquals(student.getEmail(),"Email");
	}
	
	@Test
	public void testGetName() {
		assertEquals("Sneha", student.getName());
	}
	
	@Test
	public void testGetCashMoney(){
		String s = student.getCashMoney();
		assertEquals("2000.00", s);
	}
	
	/*@Test
	public void testBuyPosition(){
		fail("don't know how to test this one");
	}
	
	@Test
	public void testSellPosition(){  
		fail("don't know how to test this");
		
	} */
	
	@Test
	public void testGetPortfolio(){
		java.util.ArrayList<PositionStub> expected = new java.util.ArrayList<PositionStub>();
		assertEquals(expected, student.getPortfolio());
	}
	
	@Test
	public void testGetMyHistory(){
		java.util.ArrayList<HistoryStub> expected = new java.util.ArrayList<HistoryStub>();
		assertEquals(expected, student.getMyHistory());
	}
	
	@Test
	public void testGetMaxProfitableSale(){ // nothing to look for
		assertEquals(student.getMaxProfitableSale(), Double.NEGATIVE_INFINITY, 0.01);
	}
	
	@Test
	public void getMoney(){
		assertEquals(1000.0, student_s.getMoney(m), 0.01);
	}
	
	@Test
	public void testGetMaxProfitPerShare(){ // nothing to look for
		assertEquals(student.getMaxProfitPerShare(), Double.NEGATIVE_INFINITY, 0.01);
	}
	

}
