package stockmarketedu;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

public class BuyStockServlet extends HttpServlet {
	static {
		ObjectifyService.register(Supervisor.class);
		ObjectifyService.register(Market.class);
		ObjectifyService.register(MarketFacade.class);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String stockSymbol = req.getParameter("Stock Ticker");
		String numShares = req.getParameter("Number of Shares");
		if(stockSymbol.isEmpty() || numShares.isEmpty()) {
			resp.sendRedirect("/student.jsp");
			return;
		}
		Double shares = Double.parseDouble(numShares);
		
		List<Supervisor> teachers = ObjectifyService.ofy().load().type(Supervisor.class).list();
		
		Student student = null;
		Supervisor teacher = null;
		for(Supervisor teach: teachers) {
			for(Student stud: teach.getClassroom().getMyClass()) {
				if(stud.getEmail().equals(user.getEmail())) {
					student = stud;
					teacher = teach;
					break;
				}
			}
			if(student != null) {
				break;
			}
		}
		
		student.buyPosition(stockSymbol, shares);
		
		ofy().save().entity(teacher).now();
		System.out.println(";alskdfjaoerjapwojerojir");
		resp.sendRedirect("/student.jsp");
	}

}
