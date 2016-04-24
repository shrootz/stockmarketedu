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
<%@ page import="stockmarketedu.Position" %>
<%@ page import="stockmarketedu.Stock" %>

<%@ page import="java.util.List" %>
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
	    List<Supervisor> teachers = ObjectifyService.ofy().load().type(Supervisor.class).list(); 
	    boolean signedIn = false;
	    boolean createdClass = false;
	    int teacherIndex = -1;
	    Supervisor teacher;

	    NumberFormat formatter = new DecimalFormat("#0.00");

		if (user != null) {
	    	pageContext.setAttribute("user", user);
	    	signedIn = true;
	    	for(Supervisor teach: teachers) {
    			if(user.getEmail().equals(teach.getEmail())) {
    				teacher = teach;
    				createdClass = true;
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
									<li ><a href="/index.html">Home</a></li>
									<li ><a href="/student.html">Student Portal</a></li>
									<li class="current_page_item"><a href="/teacher.html">Teacher Portal</a></li>
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
						<h2 class="title">Teacher Portal</h2>
					</div>
				</div>
				<%
					if(signedIn && createdClass) {
						RankStudents byMoney = new RankByMoney();
						ArrayList<Student> topInvestors = teacher.rank(byMoney);
				%>
				<div class="row">
					<div class="4u">
						<article class="info">
							<p class="byline">View class summary</p>
						</article>
						<article class="box">

							<ul>
								<li>Top Investor: <% topInvestors.get(0).getName(); %></li>
								<li>Net Worth: $<% formatter.format(topInvestors.get(0).getMoney()); %><br></li>
								<li>Runner-up: <% topInvestors.get(1).getName(); %></li>
								<li>Net Worth: $<% formatter.format(topInvestors.get(1).getMoney()); %><br></li>
							</ul>
						</article>
					</div>
					<div class="4u">
						<article class="info">
							<p class="byline">View current class performance</p>
						</article>
						<article class="box" id="graph">
							<svg id="visualisation" width="100%" height="100%"></svg>
						</article>
					</div>
					<div class="4u">
						<article class="info">
							<p class="byline">View current class net worth</p>
						</article>
						<article class="box">
							<ul>
								<%
									for(int i = 0; i < 5; i++) {
								%>
									<li><% topInvestors.get(i).getName(); %>&#58; $<% formatter.format(topInvestors.get(i).getMoney()); %></li>
								<%
									}
								%>
							</ul>
						</article>
					</div>
				</div>
				<% 
					} else if(signedIn && !createdClass) {
				%>
				<div class="row">

					<div class="12u">
						<article class="info">
							<p class="byline">Configure class settings</p>
						</article>
						<article class="box">
							<form id="class-config" action="/createclass">
								<ul class="horiz">
									<li>Class Name <input type="text" name="Class Name"></li>
									<li>Starting Cash <input type="text" name="Class Name"></li>
								</ul>
								Participant Emails<br>
								<textarea rows="3" name="Student Emails">Enter student emails here.</textarea>
								Permitted Stocks<br>
								<textarea rows="3" name="Permitted Stocks">Enter permitted stock tickers here.</textarea>
								<input type="submit" value="Submit" class="">
							</form>
						</article>
					</div>

				</div>
				<% 
					} else {
				%>
					<div class="row">
						<div class="12u">
							<a href="<%= userService.createLoginURL(request.getRequestURI()) %>">Sign in</a>
						</div>
					</div>
				<%
					}
				%>

			</div>
		</div>

	</body>
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