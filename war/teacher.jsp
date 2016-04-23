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

				<div class="row">
					<div class="4u">
						<article class="info">
							<p class="byline">View class summary</p>
						</article>
						<article class="box">
							<ul>
								<li>Top Investor: Timmy Timmsworth</li>
								<li>Net Worth: $12345<br></li>
								<li>Runner-up: Bobby Studious</li>
								<li>Net Worth: $11111<br></li>
								<li>Weeks Remaining: 24</li>
							</ul>
						</article>
					</div>
					<div class="4u">
						<article class="info">
							<p class="byline">View this week's class performance</p>
						</article>
						<article class="box" id="graph">
							<svg id="visualisation" width="100%" height="100%"></svg>
						</article>
					</div>
					<div class="4u">
						<article class="info">
							<p class="byline">View class' current net worth</p>
						</article>
						<article class="box">
							<ul>
								<li>Timmy Timmsworth: $112122</li>
								<li>Tommy Toonsmurth: $543</li>
								<li>Billy Bobbsworth: $143</li>
								<li>Stella Stevesworth: $15</li>
								<li>Bertha Bobsworth: $10</li>
								<li>Curly Curlsworth: -$100</li>
							</ul>
						</article>
					</div>
				</div>

				<div class="row">

					<div class="12u">
						<article class="info">
							<p class="byline">Configure class settings</p>
						</article>
						<article class="box">
							<form id="class-config">
								<ul class="horiz">
									<li>Class Name <input type="text" name="Class Name"></li>
									<li>Number of Weeks <input type="text" name="Number of Weeks"></li>
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