package stockmarketedutest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;

public class SupervisorTest {

	@Test
	public void testEmailSupervisor() {
		SupervisorStub s = new SupervisorStub();
		s.setEmail("abc@email.com");
		s.addEmail("abc@email.com");
		s.addEmail("email@email.com");
		s.addEmail("email@email.com");
		s.addEmail("amail@email.com");
		s.sendInvitations();
		s.sendInvitations();
		double fl = 5000;
		s.setInitialCash(Double.MAX_VALUE);
		s.setInitialCash(fl);
		assertEquals(s.getEmail(),"abc@email.com");
		assertEquals(2,s.getStudentEmails().size());
	}
	
	/*
	 * Make sure supervisor is able to call ranking algorithm. 
	 * Actual functionality of ranking tested in RankStudentsTest
	 */
	@Test
	public void testReturnRanking(){
		SupervisorStub s = new SupervisorStub();
		ArrayList<Student> ranks = s.rank(new RankByMoney());
		assertEquals(new ArrayList<Student>(), ranks);
	}
	
}
