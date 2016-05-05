package stockmarketedu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

public class CronDividendController  extends HttpServlet {
	static {
		ObjectifyService.register(Supervisor.class);
		ObjectifyService.register(Market.class);
		ObjectifyService.register(MarketFacade.class);
	}
	private static final Logger _logger = Logger.getLogger(CronController.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {		
		//resp.sendRedirect("/home.jsp");
	    //REDIRECT TO HOME PAGE HERE TO WAKE GAE UP
		try {
			_logger.info("Cron Job has been executed");
		    List<Supervisor> sups = ObjectifyService.ofy().load().type(Supervisor.class).list();
		    for(Supervisor s:sups){
		    	Class c = s.getClassroom();
		    	ArrayList<Student> student = c.getMyClass();
		    	for(Student stu: student){
		    		_logger.info(stu.getName());
		    		stu.recieveDividends();
		    	}
		    }
		    ObjectifyService.ofy().save().entities(sups).now();
		}
		catch (Exception ex) {
		//Log any exceptions in your Cron Job
			_logger.warning(ex.getMessage());
			_logger.warning("fail");
			ex.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
