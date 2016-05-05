<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="com.googlecode.objectify.*" %>

<%@ page import="stockmarketedu.Supervisor" %>
<%@ page import="stockmarketedu.Student" %>
<%@ page import="stockmarketedu.Class" %>
<%@ page import="stockmarketedu.RankStudents" %>
<%@ page import="stockmarketedu.RankByMoney" %>
<%@ page import="stockmarketedu.RankByProfitableSale" %>
<%@ page import="stockmarketedu.RankByProfitPerShare" %>
<%@ page import="stockmarketedu.Position" %>
<%@ page import="stockmarketedu.Market" %>
<%@ page import="stockmarketedu.MarketFacade" %>
<%@ page import="stockmarketedu.Stock" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.NumberFormat"%>
<%@ page isELIgnored="false" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE HTML>
<html>
	<head>
		<title>StockMarketEdu</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8" />
		<meta name="description" content="" />
		<meta name="keywords" content="" />
		<link href='https://fonts.googleapis.com/css?family=Nunito' rel='stylesheet' type='text/css'>
		<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,800" rel="stylesheet" type="text/css" />
		<link href="http://fonts.googleapis.com/css?family=Oleo+Script:400" rel="stylesheet" type="text/css" />
		<script src="js/jquery.min.js"></script>
		<script src="js/config.js"></script>
		<script src="js/skel.min.js"></script>
		<script src="js/skel-panels.min.js"></script>
		<noscript>
			<link rel="stylesheet" href="css/skel-noscript.css" />
			<link rel="stylesheet" href="css/style.css" />
			<link rel="stylesheet" href="css/style-desktop.css" />
		</noscript>
	</head>
	<%
	    UserService userService = UserServiceFactory.getUserService();
	    User user = userService.getCurrentUser();

	    ObjectifyService.register(Supervisor.class);
	    ObjectifyService.register(Market.class);
	    ObjectifyService.register(MarketFacade.class);
	    List<Supervisor> teachers = ObjectifyService.ofy().load().type(Supervisor.class).list(); 
	    boolean signedIn = false;
	    boolean createdClass = false;
	    int teacherIndex = -1;
	    Supervisor teacher = null;
	    int classSize = 0;

	    NumberFormat formatter = new DecimalFormat("#0.00");

		if (user != null) {
	    	pageContext.setAttribute("user", user);
	    	signedIn = true;
	    	for(Supervisor teach: teachers) {
    			if(user.getEmail().equals(teach.getEmail())) {
    				teacher = teach;
    				createdClass = true;
    				classSize = teacher.getClassroom().getMyClass().size();
    				break;
    			}
	    	}
		}
		Market m = Market.getInstance();
		ArrayList<String> valids = new ArrayList<String>();
		String placeholderStocks = new String("");
		for(String stock: m.getDefaultStocks()) {
			placeholderStocks = new String(placeholderStocks + stock + " ");
		}
		pageContext.setAttribute("placeholders", placeholderStocks);
	%>
	<body class="left-sidebar">

		<div id="header-wrapper">
			<div class="container">
				<header id = "header" class = "header">
					<div class = "row">
						<div class = "12u">
						<header id="header" class="header">
												
							<div id="logo">
								<h2><a href="/">StockMarketEdu</a><h2>
							</div>
							<nav id="nav">
								<ul>
									<li ><a href="/index.jsp">Home</a></li>
									<li ><a href="/student.jsp">Student Portal</a></li>
									<li class="current_page_item"><a href="/teacher.jsp">Teacher Portal</a></li>
								</ul>
							</nav>
							<%
								if(signedIn) {
							%>
								<span style="float:right"><a href="<%= userService.createLogoutURL(request.getRequestURI()) %>">Sign out</a></span>
							<%
								}
							%>
						</header>

					</div>
				</div>
			</div>
		</div>
		
		<div id="feature-wrapper">
			<div class="container">
				<div class="row">

					<div class="12u skel-cell-important">
						<h2 class="title">Teacher Portal</h2>
					</div>
				</div>
				<%
					if(signedIn && createdClass && (classSize > 0)) {
						RankStudents byMoney = new RankByMoney();
						ArrayList<Student> topInvestors = teacher.rank(byMoney);
						Collections.reverse(topInvestors);
						Student topInvestor = topInvestors.get(0);
						pageContext.setAttribute("top_money_name", topInvestor.getName());
						pageContext.setAttribute("top_money_money", formatter.format(topInvestor.getMoney()));

						RankStudents byProfitableSale = new RankByProfitableSale();
						ArrayList<Student> topSales = teacher.rank(byProfitableSale);
						Collections.reverse(topSales);
						Student topSale = topSales.get(0);
						pageContext.setAttribute("top_sale_name", topSale.getName());
						pageContext.setAttribute("top_sale_profit", formatter.format(topSale.getMaxProfitableSale()));

						RankStudents byProfitPerShare = new RankByProfitPerShare();
						ArrayList<Student> topShares = teacher.rank(byProfitPerShare);
						Collections.reverse(topShares);
						Student topShare = topShares.get(0);
						pageContext.setAttribute("top_share_name", topShare.getName());
						pageContext.setAttribute("top_share_profit", formatter.format(topShare.getMaxProfitPerShare()));
				%>
				<div class="row">
					<div class="4u">
						<article class="info">
							<p class="byline">View class summary</p>
						</article>
						<article class="box">

							<ul>
								<li>Top Investor: ${top_money_name}</li>
								<li>Net Worth: $${top_money_money}<br></li>
								<li>Top Salesperson: ${top_sale_name}</li>
								<li>Highest Value Sale: $${top_sale_profit}<br></li>
								<li>Top Prospector: ${top_share_name}</li>
								<li>Highest Profit Per Share: $${top_share_profit}<br></li>
							</ul>
						</article>
					</div>
					<div class="4u">
						<article class="info">
							<p class="byline">View current class statistics</p>
						</article>
						<article class="box last" id="rank-money">
							<p class="byline">Ranks by net worth</p>
							<ul>
								<%
									for(int i = 0; i < topInvestors.size(); i++) {
										pageContext.setAttribute("investor_name", topInvestors.get(i).getName());
										pageContext.setAttribute("investor_money", formatter.format(topInvestors.get(i).getMoney()));
								%>
									<li>${investor_name} &#58; $${investor_money}</li>
								<%
									}
								%>
							</ul>
						</article>
						<article class="box last" id="rank-sales">
							<p class="byline">Ranks by highest profitable sale</p>
							<ul>
								<%
									for(int i = 0; i < topSales.size(); i++) {
										pageContext.setAttribute("salesperson_name", topSales.get(i).getName());
										pageContext.setAttribute("salesperson_profit", formatter.format(topSales.get(i).getMaxProfitableSale()));
								%>
									<li>${salesperson_name} &#58; $${salesperson_profit}</li>
								<%
									}
								%>
							</ul>
						</article>
						<article class="box last" id="rank-shares">
							<p class="byline">Ranks by highest profit per share</p>
							<ul>
								<%
									for(int i = 0; i < topShares.size(); i++) {
										pageContext.setAttribute("prospector_name", topShares.get(i).getName());
										pageContext.setAttribute("prospector_profit", formatter.format(topShares.get(i).getMaxProfitPerShare()));
								%>
									<li>${prospector_name} &#58; $${prospector_profit}</li>
								<%
									}
								%>
							</ul>
						</article>
						<ul id="rank-selection">
							<li ><a href="#" class="button alt" onclick="showRankByMoney();">Money</a></li>
							<li ><a href="#" class="button alt" onclick="showRankBySale();">Profitable Sale</a></li>
							<li ><a href="#" class="button alt" onclick="showRankByShare();">Profit Per Share</a></li>
						</ul>
					</div>
					<div class="4u">
						<article class="info">
							<p class="byline">View detailed class data</p>
						</article>
						<article class="box" style="overflow:scroll;">
							<table border="1">
								<tr>
									<td>Student Name</td>
									<td>Cash</td>
									<td>Net Worth</td>
								</tr>
								<%
									ArrayList<Student> studs = teacher.getClassroom().getMyClass();
									Collections.sort(studs);
									for(Student s: studs) {
										pageContext.setAttribute("stud_name", s.getName());
										pageContext.setAttribute("stud_cash", s.getCashMoney());
										pageContext.setAttribute("stud_money", formatter.format(s.getMoney()));
								%>
										<tr>
											<td>${stud_name}</td>
											<td>${stud_cash}</td>
											<td>${stud_money}</td>
										</tr>
								<%
									}
								%>
							</table>
						</article>
					</div>
					
				</div>
				<div class="row">
						<div class="12u">
							<article class="info">
							<p class="byline">Add more student emails or stocks below, if desired.</p>
							</article>
							<script>
									function validateConfigure() {
										var fields = Boolean(validateConfigureClass());
										if(!fields) {
											return false;
										}
										var stocks = Boolean(validatePermittedStocksConfig());
										if(!stocks) {
											return false;
										}
										return true;
									}
							</script>
							<article class="box">
								<form id="class-config" action="/configureclass" name="configure" method="post" onsubmit="return validateConfigure()">
									Participant Emails<br>
									<textarea rows="3" name="Student Emails" placeholder="Enter student emails separated by a space."></textarea>
									Permitted Stocks<br>
									<textarea rows="3" name="Permitted Stocks" placeholder="Enter permitted stock tickers separated by a space. Valid stock symbols are: ${placeholders}"></textarea>
									<input type="submit" value="Submit">
								</form>
							</article>
						</div>
				</div>

				<% 
					} else if(signedIn && !createdClass) {
				%>
				<div class="row">

					<div class="12u">
						<article class="info">
							<p class="byline">Create class settings</p>
						</article>
						<article class="box">
							<script>
									function validateCreate() {
										var fields = Boolean(validateCreateClass());
										if(!fields) {
											return false;
										}
										var stocks = Boolean(validatePermittedStocksCreate());
										if(!stocks) {
											return false;
										}
										return true;
									}
							</script>
							<form id="class-config" action="/createclass" name="create" method="post" onsubmit="return validateCreate()">
								<ul class="horiz">
									<li>Starting Cash <input type="text" name="Starting Cash"></li>
								</ul>
								Participant Emails<br>
								<textarea rows="3" name="Student Emails" placeholder="Enter student emails separated by a space."></textarea>
								Permitted Stocks<br>
								<textarea rows="3" name="Permitted Stocks" placeholder="Enter permitted stock tickers separated by a space. Valid stock symbols are: ${placeholders}"></textarea>
								<input type="submit" value="Submit">
							</form>
						</article>
					</div>

				</div>
				<% 
					} else if(signedIn && (classSize <= 0)) {
				%>
					<div class="row">
						<div class="12u">
							<article class="info">
							<p class="byline">No student in your class has completed registration. Add more student emails or stocks below, if desired.</p>
							</article>
							<article class="box">
								<script>
									function validateConfigure() {
										var fields = Boolean(validateConfigureClass());
										if(!fields) {
											return false;
										}
										var stocks = Boolean(validatePermittedStocksConfig());
										if(!stocks) {
											return false;
										}
										return true;
									}
								</script>
								<form id="class-config" action="/configureclass" name="configure" method="post" onsubmit="return validateConfigure()">
									Participant Emails<br>
									<textarea rows="3" name="Student Emails" placeholder="Enter student emails separated by a space."></textarea>
									Permitted Stocks<br>
									<textarea rows="3" name="Permitted Stocks" placeholder="Enter permitted stock tickers separated by a space. Valid stock symbols are: ${placeholders}"></textarea>
									<input type="submit" value="Submit">
								</form>
							</article>
						</div>
					</div>
				<% 
					} else {
				%>
					<div class="row">
						<div class="12u">
							<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in to create or access a class</a>
						</div>
					</div>
				<%
					}
				%>

			</div>
		</div>

	</body>
	<script>
		function validatePermittedStocksCreate() {
    		var stocks = document.forms["create"]["Permitted Stocks"].value;
			var validStocks = "${placeholders}";
			var tickers = stocks.split(" ");
			var validTickers = validStocks.split(" ");
			for(var i = 0; i < tickers.length; i++) {
				var found = false;
				for(var j = 0; j < validTickers.length; j++) {
					if(tickers[i] === validTickers[j]) {
						found = true;
					}
				}
				if(!found) {
					alert(tickers[i] + " is not a supported stock");
					return false;
				}
			}
			return true;
		}
	</script>
	<script>
		function validatePermittedStocksConfig() {
    		var stocks = document.forms["configure"]["Permitted Stocks"].value;
    		if(stocks == "" || stocks == null) {
    			return true;
    		}
			var validStocks = "${placeholders}";
			var tickers = stocks.split(" ");
			var validTickers = validStocks.split(" ");
			for(var i = 0; i < tickers.length; i++) {
				var found = false;
				for(var j = 0; j < validTickers.length; j++) {
					if(tickers[i] === validTickers[j]) {
						found = true;
					}
				}
				if(!found) {
					alert(tickers[i] + " is not a supported stock");
					return false;
				}
			}
			return true;
		}
	</script>
	<script>
	  function showRankByMoney() {
	    document.getElementById('rank-shares').style.display = 'none';
	    document.getElementById('rank-sales').style.display = 'none';
	    document.getElementById('rank-money').style.display = 'block';
	  }
	</script>
	<script>
	  function showRankBySale() {
	    document.getElementById('rank-shares').style.display = 'none';
	    document.getElementById('rank-sales').style.display = 'block';
	    document.getElementById('rank-money').style.display = 'none';
	  }
	</script>
	<script>
	  function showRankByShare() {
	    document.getElementById('rank-shares').style.display = 'block';
	    document.getElementById('rank-sales').style.display = 'none';
	    document.getElementById('rank-money').style.display = 'none';
	  }
	</script>
	<script src="js/validate.js"></script>
</html>