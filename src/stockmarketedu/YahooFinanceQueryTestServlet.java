package yahoofinancequerytest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.*;



@SuppressWarnings("serial")
public class YahooFinanceQueryTestServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Testing");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
		System.out.println("in Post");
		System.out.println("Getting stock");
		Market market = new MarketAdapter();
		market.addStock(request.getParameter("symbol"));
		Stock s = market.getStock(request.getParameter("symbol"));
		response.getWriter().println("Stock Symbol: " + s.getSymbol());
		response.getWriter().println("Company Name: " + s.getName());
		response.getWriter().println("Price: " + s.getPrice());
		response.getWriter().println("Dividends Per Share: " + s.getDividendShare());
		response.getWriter().println("Current as of " + s.getTimeStamp());
		
	}
}
