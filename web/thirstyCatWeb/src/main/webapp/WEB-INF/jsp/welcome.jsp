<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	<!-- Bootstrap -->
	<link rel="stylesheet" type="text/css" href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" />
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />
</head>
<body>

	<!--  NAVBAR -->
	<nav class="navbar navbar-inverse">
		<div class-"container>
			<div class="navbar-header">
				<a class="navbar-brand" href="#">ThirstyCat</a>
			</div>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
				</ul>
			</div>
		</div>
	</nav>
	
	<!-- Placeholder for any additional page header/tile text i may want -->
	<div class="container">
		<h1>This will be ThirstyCat!</h1>
		<h2>neat ${message}</h2>
	</div>
	
	<!-- DIV to contain toggle bar to switch from cat image to cat stats -->
	<div class="container row" id="toggleSection">
		<div class="toggleColumn text-center">ThirstyCat</div>
		<div class="toggleColumn text-center">Cat Stats</div>
	</div>
	
	<!--  DIV to contain the most recent thirstycat image -->
	<div class="container" id="thirstyCatImageSection">
		<h1>This will be the most recent ThirstyCat image</h1>
		<img class="center-block" src="https://media.mnn.com/assets/images/2017/02/cat-drinking-faucet.jpg.653x0_q80_crop-smart.jpg" alt="Most recent picture of cat drinking water">
	</div>
	
	<!--  DIV to contain thirstycat cat stats -->
	<div class="container" id="catStatsSection">
		<h1>This will be cat stats</h1>
		<img class="center-block" src="https://datavizcatalogue.com/methods/images/top_images/line_graph.png" alt="a cat stat graph">
	</div>
	
	<!-- DIV to contain Cats Of ThirstyCat -->
	<div class="container" id="catsOfTCSection">
		<h1>This will be cats of thirstycat</h1>
	</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="js/main.js"></script>
</body>



</html>