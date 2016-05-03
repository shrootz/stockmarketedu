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
<%@ page import="stockmarketedu.Market" %>
<%@ page import="stockmarketedu.RankStudents" %>
<%@ page import="stockmarketedu.RankByMoney" %>
<%@ page import="stockmarketedu.RankByProfitableSale" %>
<%@ page import="stockmarketedu.RankByProfitPerShare" %>
<%@ page import="stockmarketedu.Position" %>
<%@ page import="stockmarketedu.Stock" %>

<%@ page import="java.util.ArrayList" %>
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
	    Supervisor teacher = null;
	    int classSize = 0;

	    Market mkt = Market.getInstance();
		pageContext.setAttribute("allowed_stocks", topInvestor.getMoney());

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
						Student topInvestor = topInvestors.get(0);
						pageContext.setAttribute("top_money_name", topInvestor.getName());
						pageContext.setAttribute("top_money_money", topInvestor.getMoney());

						RankStudents byProfitableSale = new RankByProfitableSale();
						ArrayList<Student> topSales = teacher.rank(byProfitableSale);
						Student topSale = topSales.get(0);
						pageContext.setAttribute("top_sale_name", topSale.getName());
						pageContext.setAttribute("top_sale_profit", topSale.getMaxProfitableSale());

						RankStudents byProfitPerShare = new RankByProfitPerShare();
						ArrayList<Student> topShares = teacher.rank(byProfitPerShare);
						Student topShare = topShares.get(0);
						pageContext.setAttribute("top_share_name", topShare.getName());
						pageContext.setAttribute("top_share_profit", topShare.getMaxProfitPerShare());
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
							<p class="byline">View current class performance</p>
						</article>
						<article class="box" id="graph">
							<svg id="visualisation" width="100%" height="100%"></svg>
						</article>
					</div>
					<div class="4u">
						<article class="info">
							<p class="byline">View current class statistics</p>
						</article>
						<article class="box" id="rank-money">
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
						<article class="box" id="rank-sales">
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
						<article class="box" id="rank-shares">
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
				</div>
				<div class="row">
						<div class="12u">
							<article class="info">
							<p class="byline">Add more student emails or stocks below, if desired.</p>
							</article>
							<article class="box">
								<form id="class-config" action="/configureclass" method="post">
									Participant Emails<br>
									<textarea rows="3" name="Student Emails" placeholder="Enter student emails separated by a space."></textarea>
									Permitted Stocks<br>
									<textarea rows="3" name="Permitted Stocks" placeholder="Enter permitted stock tickers separated by a space. Valid stock choices are ${allowed_stocks}"></textarea>
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
							<p class="byline">Configure class settings</p>
						</article>
						<article class="box">
							<form id="class-config" action="/createclass" method="post">
								<ul class="horiz">
									<li>Starting Cash <input type="text" name="Starting Cash"></li>
								</ul>
								Participant Emails<br>
								<textarea rows="3" name="Student Emails" placeholder="Enter student emails separated by a space."></textarea>
								Permitted Stocks<br>
								<textarea rows="3" name="Permitted Stocks" placeholder="Enter permitted stock tickers separated by a space. Valid stock choices are ${allowed_stocks}"></textarea>
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
								<form id="class-config" action="/configureclass" method="post">
									Participant Emails<br>
									<textarea rows="3" name="Student Emails" placeholder="Enter student emails separated by a space."></textarea>
									Permitted Stocks<br>
									<textarea rows="3" name="Permitted Stocks" placeholder="Enter permitted stock tickers separated by a space. Valid stock choices are ${allowed_stocks}"></textarea>
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