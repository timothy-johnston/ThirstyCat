<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>

	<!-- For mobile-first -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<!--  Bootstrap & jquery -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
  	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	<c:url value="/css/main.css" var="jstlCss" />
	<link href="${jstlCss}" rel="stylesheet" />
	
	
	
</head>
<body>

	<!--  NAVBAR -->


    <nav class="navbar navbar-default">

        <!-- Brand and toggle get grouped for better mobile display -->

        <div class="navbar-header">

            <button type="button" data-target="#navbarCollapse" data-toggle="collapse" class="navbar-toggle">

                <span class="sr-only">Toggle navigation</span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>

                <span class="icon-bar"></span>

            </button>

            <a href="#" class="navbar-brand">Brand</a>

        </div>

        <!-- Collection of nav links and other content for toggling -->

        <div id="navbarCollapse" class="collapse navbar-collapse">

            <ul class="nav navbar-nav">

                <li class="active"><a href="#">Home</a></li>

                <li><a href="#">Profile</a></li>

                <li><a href="#">Messages</a></li>

            </ul>

            <ul class="nav navbar-nav navbar-right">

                <li><a href="#">Login</a></li>

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
	<div id="thirstyCatImageSection">
		<img class="center-block" src="https://media.mnn.com/assets/images/2017/02/cat-drinking-faucet.jpg.653x0_q80_crop-smart.jpg" alt="Most recent picture of cat drinking water">
	</div>
	
	<!--  DIV to contain thirstycat cat stats -->
	<div id="catStatsSection">
		<img class="center-block" src="https://datavizcatalogue.com/methods/images/top_images/line_graph.png" alt="a cat stat graph">
	</div>
	
	<!-- DIV to contain Cats Of ThirstyCat -->
	<div id="catsOfTCSection">
		<h1>This will be cats of thirstycat</h1>
	</div>



<script src="js/main.js"></script>
</body>



</html>