package stockmarketedutest;

import static org.junit.Assert.*;

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
		double fl = 5000;
		s.setInitialCash(Double.MAX_VALUE);
		s.setInitialCash(fl);
		assertEquals(s.getEmail(),"abc@email.com");
		assertEquals(2,s.getStudentEmails().size());
	}
}
