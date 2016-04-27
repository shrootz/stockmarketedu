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
<%@ page import="stockmarketedu.Position" %>
<%@ page import="stockmarketedu.Stock" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.NumberFormat"%>

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
		<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
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
	    ArrayList<Supervisor> teachers = (ArrayList<Supervisor>) ObjectifyService.ofy().load().type(Supervisor.class).list(); 
	    boolean signedIn = false;
	    boolean inClass = false;
	    int teacherIndex = -1;
	    Student student = null;

	    NumberFormat formatter = new DecimalFormat("#0.00");

		if (user != null) {
	    	pageContext.setAttribute("user", user);
	    	signedIn = true;
	    	for(Supervisor teacher: teachers) {
	    		for(Student stud: teacher.getClassroom().getMyClass()) {
	    			if(user.getEmail().equals(stud.getEmail())) {
	    				student = stud;
	    				inClass = true;
	    				break;
	    			}
	    		}
	    		if(student != null) {
	    			break;
	    		}
	    	}
		}
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
									<li ><a href="/">Home</a></li>
									<li class="current_page_item" ><a href="/student">Student Portal</a></li>
									<li ><a href="/teacher">Teacher Portal</a></li>
								</ul>
							</nav>
						</header>

					</div>
				</div>
			</div>
		</div>
		<div id="feature-wrapper">
			<div class="container">
				<div class="row">

					<div class="12u skel-cell-important">
						<h2 class="title">Student Portal</h2>
					</div>
				</div>

				<div class="row">
					<%
						if(signedIn && inClass) {
					%>
						<div class="4u">
							<article class="info">
								<p class="byline">View your summary</p>
							</article>
							<article class="box">
								<ul>
									<li>Student: <% student.getName(); %></li>
									<li>Investment Value: $<% formatter.format(student.getMoney()); %></li>
									<li>Cash: $<% formatter.format(student.getCashMoney()); %></li>
								</ul>
							</article>
						</div>
						<div class="4u">
							<article class="info">
								<p class="byline">View your current performance</p>
							</article>
							<article class="box" id="graph">
								<svg id="visualisation" width="100%" height="100%"></svg>
							</article>
						</div>
						<div class="4u">
							<article class="info">
								<p class="byline">View your current holdings</p>
							</article>
							<article class="box">
								<ul>
									<%
										for(Position p: student.getPortfolio()) {
									%>
											<li>
												<% p.getStockType().getSymbol(); %>
												&#58; <% p.getShares(); %>
												 shares @ 
												$<% formatter.format(p.getStockType().getPrice()); %>
											</li>
									<%
										}
									%>
								</ul>
							</article>
						</div>
					<%
						} else if(!signedIn){
					%>
						<div class="12u">
							<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in to view performance</a>
						</div>
					<%
						} else {
					%>
						<div class="12u">
							Your email is not associated with a valid class. Please talk to your teacher.
						</div>
					<%
						}
					%>
				</div>

				<div class="row">

					<div class="6u">
						<article class="info">
							<p class="byline">Sell a stock</p>
						</article>
						<article class="box">
							<form action="/sell">
								Stock Ticker<br> <input type="text" name="Stock Ticker"><br>
								Number of Shares<br> <input type="text" name="Number of Shares"><br>
								<input type="submit" value="Submit">
							</form>
						</article>
					</div>
					<div class="6u">
						<article class="info">
							<p class="byline">Buy a stock</p>
						</article>
						<article class="box">
							<form action="/buy">
								Stock Ticker<br> <input type="text" name="Stock Ticker"><br>
								Number of Shares<br> <input type="text" name="Number of Shares"><br>
								<input type="submit" value="Submit">
							</form>
						</article>
					</div>

				</div>

				<div class="row">

					<div class="12u">
						<article class="info">
							<p class="byline">Learn about the stock market</p>
						</article>
						<article class="box">
							<p id="student-fact">
								Enable javascript to see this fact!
							</p>
							<p style="font-style:italic;">
								From <a href="http://www.themint.org/kids/what-is-the-stock-market.html">themint.org</a>
							</p>
						</article>
					</div>

				</div>

			</div>
		</div>

	</body>
		<script src="js/studentfacts.js"></script>
		<script>
			InitChart();

			function InitChart() {

			  var barData = [{
			    'x': 1,
			    'y': 5
			  }, {
			    'x': 20,
			    'y': 20
			  }, {
			    'x': 40,
			    'y': 10
			  }, {
			    'x': 60,
			    'y': 40
			  }, {
			    'x': 80,
			    'y': 5
			  }, {
			    'x': 100,
			    'y': 60
			  }];

			  var vis = d3.select('#visualisation'),
			  	container = document.getElementById("graph"),
			    WIDTH = container.offsetWidth*.70,
			    HEIGHT = container.offsetHeight*.70,
			    MARGINS = {
			      top: 5,
			      right: 5,
			      bottom: 5,
			      left: 5
			    },
			    xRange = d3.scale.ordinal().rangeRoundBands([MARGINS.left, WIDTH - MARGINS.right], 0.1).domain(barData.map(function (d) {
			      return d.x;
			    })),


			    yRange = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom]).domain([0,
			      d3.max(barData, function (d) {
			        return d.y;
			      })
			    ]),

			    xAxis = d3.svg.axis()
			      .scale(xRange)
			      .tickSize(5)
			      .tickSubdivide(true),

			    yAxis = d3.svg.axis()
			      .scale(yRange)
			      .tickSize(5)
			      .orient("left")
			      .tickSubdivide(true);

			  vis.append('svg:g')
			    .attr('class', 'x axis')
			    .attr('transform', 'translate(0,' + (HEIGHT - MARGINS.bottom) + ')')
			    .call(xAxis);

			  vis.append('svg:g')
			    .attr('class', 'y axis')
			    .attr('transform', 'translate(' + (MARGINS.left) + ',0)')
			    .call(yAxis);

			  vis.selectAll('rect')
			    .data(barData)
			    .enter()
			    .append('rect')
			    .attr('x', function (d) {
			      return xRange(d.x);
			    })
			    .attr('y', function (d) {
			      return yRange(d.y);
			    })
			    .attr('width', xRange.rangeBand())
			    .attr('height', function (d) {
			      return ((HEIGHT - MARGINS.bottom) - yRange(d.y));
			    })
			    .attr('fill', 'white')
			    .on('mouseover',function(d){
			      d3.select(this)
			        .attr('fill','blue');
			    })
			    .on('mouseout',function(d){
			      d3.select(this)
			        .attr('fill','white');
			    });

			}
		</script>
</html>