package stockmarketedu;

import static org.junit.Assert.*;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;

import org.junit.*;

public class SupervisorTest {
	
	  private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig(),
              new LocalMemcacheServiceTestConfig());
	    
	  @Before
	  public void setUp() {
	    helper.setUp();
	    ObjectifyService.register(Supervisor.class);
	    ObjectifyService.register(Market.class);
	  }

	  @After
	  public void tearDown() {
	    helper.tearDown();
	  }

	@Test
	public void testTimePM() {
		Supervisor s = new Supervisor();
		s.addEmail("email@email.com");
		s.addEmail("amail@email.com");
		float fl = 500.0f;
		s.setInitialCash(fl);
		Class classroom =  s.getClassroom();
		String access = classroom.getAccessCode();
		Student name = new Student("Name", access);
		int size = classroom.getMyClass().size();
		assertTrue(size == 1);
	}
}
