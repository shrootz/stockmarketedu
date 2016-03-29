package stockmarketedu;

import javax.servlet.http.HttpServlet;

import com.googlecode.objectify.ObjectifyService;

public class ServletContextListener extends HttpServlet {

	public void init()
    {
		ObjectifyService.register(Supervisor.class);
		ObjectifyService.register(Class.class);
    }
}