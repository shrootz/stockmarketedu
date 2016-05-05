<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collections" %>
<%@ page import="com.googlecode.objectify.*" %>

<%@ page import="stockmarketedu.MarketFacadeIntegrationTest" %>
<%@ page import="stockmarketedu.MarketTest" %>
<%@ page import="stockmarketedu.RankStudentsTest" %>
<%@ page import="stockmarketedu.ClassTest" %>
<%@ page import="stockmarketedu.HistoryTest" %>
<%@ page import="stockmarketedu.PositionTest" %>
<%@ page import="stockmarketedu.StockTest" %>
<%@ page import="stockmarketedu.StudentTest" %>
<%@ page import="stockmarketedu.SupervisorTest" %>

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
									<li  class="current_page_item" ><a href="/">Home</a></li>
									<li ><a href="./student.jsp">Student Portal</a></li>
									<li ><a href="./teacher.jsp">Teacher Portal</a></li>
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

						<!-- Content -->
							<div id="">
								<article class="">
									<h2 class="title">Stock Market Edu Tests</h2>
									<ul>
										<li>MarketFacadeIntegrationTest&#58; </li>
										<li>MarketTest&#58; </li>
										<li>RankStudentsTest&#58; </li>
										<li>ClassTest&#58; </li>
										<li>HistoryTest&#58; </li>
										<li>PositionTest&#58; </li>
										<li>StockTest&#58; </li>
										<li>StudentTest&#58; </li>
										<li>SupervisorTest&#58; </li>
									</ul>
								</article>
								
							</div>

					</div>
			</div>
		</div>

	</body>
</html>