package stockmarketedu;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyService;

public class CreateClassServlet extends HttpServlet {
	static {
		ObjectifyService.register(Supervisor.class);
		ObjectifyService.register(Market.class);
		ObjectifyService.register(MarketFacade.class);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String cashString = req.getParameter("Starting Cash");
		String emailsString = req.getParameter("Student Emails");
		String stocksString= req.getParameter("Permitted Stocks");
		if(cashString.isEmpty() || emailsString.isEmpty() || stocksString.isEmpty()) {
			resp.sendRedirect("/teacher.jsp");
			return;
		}
		float cash = Float.parseFloat(cashString);
		String[] emails = emailsString.split(" ");
		String[] stocks = stocksString.split(" ");
		Supervisor teacher = new Supervisor();
		teacher.setEmail(user.getEmail());
		teacher.getClassroom().setInitialMoney(cash);
		for(String s: emails) {
			teacher.addEmail(s);
		}
		
		// TODO: validate stocks with permitted stock list
		for(String s: stocks) {
			teacher.getClassroom().addStock(s);
		}
		ofy().save().entity(teacher).now();
		System.out.println("A:DLFKSJDF:LKSDJFL:KSJDFLS");
		resp.sendRedirect("/teacher.jsp");
	}

}
