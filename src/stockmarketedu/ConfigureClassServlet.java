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

public class ConfigureClassServlet extends HttpServlet {
	static {
		ObjectifyService.register(Supervisor.class);
		ObjectifyService.register(Market.class);
		ObjectifyService.register(MarketFacade.class);

	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String emailsString = req.getParameter("Student Emails");
		if(emailsString.isEmpty()) {
			resp.sendRedirect("/teacher.jsp");
			return;
		}
		String[] emails = emailsString.split(" ");

	    List<Supervisor> teachers = ObjectifyService.ofy().load().type(Supervisor.class).list(); 
		Supervisor teacher = null;
		for(Supervisor teach: teachers) {
			if(teach.getEmail().equals(user.getEmail())) {
				teacher = teach;
				break;
			}
		}
		
		for(String s: emails) {
			if(!teacher.getStudentEmails().contains(s)) {
				teacher.addEmail(s);
			}
		}

		ofy().save().entity(teacher).now();
		resp.sendRedirect("/teacher.jsp");
	}

}
