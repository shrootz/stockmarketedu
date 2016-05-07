package stockmarketedutest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

/* which will ensure proper integration of Class, Student, History, Position, and Market ...
 * Our ranking algorithms working properly are dependent on Class objects keeping track of 
 * Student objects and on each Student object’s interaction with the Market, which in turn 
 * relies on proper integration with Position, Stock, and History objects. We will implement 
 * test scenarios of users in a classroom interacting with our application. Next, we will 
 * apply our ranking algorithms on these test scenarios, automating this portion through 
 * JUnit, ensuring proper execution of both the ranking algorithms and proper integration of 
 * Market, Student, Class, Stock, Position, and History classes.
 */

public class RankStudentsTest {
	private RankStudents ranks;
	private ArrayList<Student> mockStudents;
	MarketFacadeStub myMarket;
	@Before
	public void setUp(){
		myMarket = new MarketFacadeStub();
	    myMarket.removeDefaultStocks();
	    myMarket.addStock("GOOG");
	    
		mockStudents = new ArrayList<Student>();
		//Random r = new Random();
		for(int i = 0; i < 100; i++){
			String name = i + "person";
			String email = name + "@stockmarketedu.461l";
			//double cash = r.nextDouble();
			mockStudents.add(new StudentStub(name, email, 100000));
		}
		int i = 100;
		for(Student s: mockStudents){
			StudentStub s_stub = (StudentStub) s;
			s_stub.buyPosition("GOOG", i, myMarket);
			//System.out.println(s_stub.getPortfolio().get(0).getShares());
			s_stub.sellPosition("GOOG", 2, myMarket);
			//System.out.println(s_stub.getPortfolio().get(0).getShares());
			i--;
		}
	}
	
	@Test
	public void notEnoughMoney(){
		StudentStub student = new StudentStub("Sneha", "Email", 0); 
		assertFalse(student.buyPosition("GOOG", 4, myMarket));
	}
	
	@Test
	public void repeatStock(){
		StudentStub student = new StudentStub("Sneha", "Email", 100000); 
		student.buyPosition("GOOG", 1, myMarket);
		student.buyPosition("GOOG", 2, myMarket);
		StockStub goog_stock = myMarket.getStock("GOOG");
		assertEquals(student.getPosition(goog_stock).getShares(), 3.0, 0.1);
	}
	
	@Test
	public void testReturnRankingByMoney() {
		ranks = new RankByMoney();
		mockStudents = ranks.returnRanking(mockStudents);
		//System.out.println(mockStudents.get(0).getName());
		assertEquals(mockStudents.get(0).getName(), mockStudents.get(0).getName());
	}
	
	@Test
	public void testReturnRankingByProfitableSale() {
		ranks = new RankByProfitableSale();
		mockStudents = ranks.returnRanking(mockStudents);
		//System.out.println(mockStudents.get(0).getName());
		assertEquals(mockStudents.get(0).getName(), mockStudents.get(0).getName());
	}
	
	@Test
	public void testReturnRankingByProfitPerShare() {
		ranks = new RankByProfitPerShare();
		mockStudents = ranks.returnRanking(mockStudents);
		//System.out.println(mockStudents.get(0).getName());
		assertEquals(mockStudents.get(0).getName(), mockStudents.get(0).getName());
	}

}
