package stockmarketedu;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.util.ArrayList;

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
		teacher.sendInvitations();
		Market mkt = Market.getInstance();
		ArrayList<String> validStocks = new ArrayList<String>();
		for(String stock: mkt.getDefaultStocks()) {
			validStocks.add(stock);
		}
		for(String s: stocks) {
			for(String validStock: validStocks) {
				if(validStock.equals(s)) {
					teacher.getClassroom().addStock(s);
					break;
				}
			}
		}
		ofy().save().entity(teacher).now();
		resp.sendRedirect("/teacher.jsp");
	}

}
