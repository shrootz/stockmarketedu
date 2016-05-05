package stockmarketedu;

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
	MarketFacade myMarket;
	@Before
	public void setUp(){
		myMarket = new MarketFacade();
	    myMarket.removeDefaultStocks();
	    myMarket.addStock("GOOG");
	    
		mockStudents = new ArrayList<Student>();
		Random r = new Random();
		for(int i = 0; i < 100; i++){
			String name = i + "person";
			String email = name + "@stockmarketedu.461l";
			double cash = r.nextDouble();
			mockStudents.add(new Student(name, email, cash));
		}
		int i = 0;
		for(Student s: mockStudents){
			s.buyPosition("GOOG", i);
			i++;
		}
		for(Student s: mockStudents){
			s.sellPosition("GOOG", 1);
		}
	}

	@Test
	public void testReturnRankingByMoney() {
		ranks = new RankByMoney();
		mockStudents = ranks.returnRanking(mockStudents);
		System.out.println(mockStudents.get(0).getName());
		assertEquals(mockStudents.get(0).getName(), ("99person"));
	}
	
	@Test
	public void testReturnRankingByProfitableSale() {
		ranks = new RankByProfitableSale();
		mockStudents = ranks.returnRanking(mockStudents);
		fail("Not yet implemented");
	}
	
	@Test
	public void testReturnRankingByProfitPerShare() {
		ranks = new RankByProfitPerShare();
		mockStudents = ranks.returnRanking(mockStudents);
		fail("Not yet implemented");
	}

}
