<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

	<!-- For mobile-first -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<!--  Bootstrap 4 & jquery -->
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
  	
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />
	
	
	
</head>
<body>

	<!-- Great Bootstrap 4 navbar example, first tutorial on page: https://getbootstrap.com/docs/4.1/components/navbar/ -->
	<nav class="navbar navbar-expand-lg navbar-light" id="topLevelNav">
		<a class="navbar-brand" id="brandText" href="#">ThirstyCat</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		
		<div class="collapse navbar-collapse" id="navbarSupportedContent">
			<ul class="navbar-nav ml-auto" id="collapseLinks">
				<li class="nav-item">
					<a class="nav-link" href="#">Log In</a>
				</li>	
				<li class="nav-item">
					<a class="nav-link test" href="#">Sign Up</a>
				</li>
			</ul>
		</div>
	</nav>
	

	<!-- DIV to contain toggle bar to switch from cat image to cat stats -->
	<div id="toggleSection">
		<span class="toggleColumn text-center">
			<button type="button" class="btn btn-block buttonSelected" id="toggleButtonTC">ThirstyCat</button>
		</span>
		<span class="toggleColumn text-center">
			<button type="button" class="btn btn-block buttonUnselected" id="toggleButtonCatStats">Cat Stats</button>
		</span>
	</div>
	
	<!--  DIV to contain the most recent thirstycat image -->
	<div class="card" id="thirstyCatImageSection">
		<img class="center-block" src="https://media.mnn.com/assets/images/2017/02/cat-drinking-faucet.jpg.653x0_q80_crop-smart.jpg" alt="Most recent picture of cat drinking water">
	</div>
	
	<!--  DIV to contain thirstycat cat stats -->
	<div class="card" id="catStatsSection">
		<img class="center-block" src="https://datavizcatalogue.com/methods/images/top_images/line_graph.png" alt="a cat stat graph">
	</div>
	
	<!-- DIV to contain Cats Of ThirstyCat -->
	<div id="catsOfTCSection">
		<h1>This will be cats of thirstycat</h1>
	</div>



<script src="js/main.js"></script>
</body>



</html>