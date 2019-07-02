var currentDrinkId;
var currentImageId;
var allDrinks = [];
var apiURL = "http://localhost:8080/api";
// var apiURL = "http://thirstycat.us-east-1.elasticbeanstalk.com/";
var apiPathLastDrink = "/drink/lastDrink";
var apiPathAllDrinks = "/drink/allDrinks";
var apiPathLastDrinkImage = "/image/lastImage";
var apiPathImageByDrinkID = "/image/imageByDrink/";
var apiPathFavoriteImage = "/image/favorite";
var apiPathLikedImages = "/image/favorites/";
var apiPathJWT = "/authenticateJWT";
var updateImage = true;
var username;
var chartId = 1;
var jwt;

$( document ).ready(function() {
	
	//Flow Control-------------------------------------------------------------------

	//On initial page load, grab the most recent drink info + picture
		//Flow is as follows, asynch rest api calls w/callback functions on success:
			//initiateDrinkUpdate->getAllDrinks->getMostRecentDrinkId->updateDrinkInfo->
				//getDrinkImage->performStats->createCharts
	initiateDrinkUpdate();

	// Check to see if new drink has been taken every minute. If so, update info on page
	setInterval(function() {
		console.log("------starting set interval---------")
		initiateDrinkUpdate();
	}, (1000 * 60));

	//End Flow Control-----------------------------------------------------------------

	//Event Listeners------------------------------------------------------------------

	//Update graph on dropdown selection
	$('.select-dropdown').change(function() {
		
		//Turn off image update
		updateImage = false;

		//Determine visualization to display based on selection
		var option = $('.select-dropdown option:selected').val();

		switch(option) {
			case "drinksPerDay":
				chartId = 0;
				break;
			case "drinksPerTimeSlice":
				chartId = 1;
				break;
		}

		performStats();

	});

	//Expand favorite picture when clicked on
	$('.fav-pic-container').click(function() {
		//Reset size of currently expanded image (if any)
		$('.fav-pic-container').css({"width":"200px","height":"150px"});
		$('.fav-pic').css({"width":"200px","height":"150px"});
		$(this).css({"width":"800px","height":"600px"});
		$(this).find("img").css({"width":"800px","height":"600px"});
	});
	
	//Handle favoriting of pictures.
	username = $('.username-holder').text();
	$('#like-heart').click(function() {

		//Check if there is a logged in user. If so, initiate favoriting of picture
		//If not, prompt to log in
		if (userIsLoggedIn(username) && !userLikedCurrentPicture(username, currentImageId)) {
			$(this).css("color","red")
			//favoriteImage(username, currentImageId);
			getJWT(favoriteImage);
		} else {
			$('#login-prompt-container').show();
		}
		
	});

})

function getJWT(nextFunction) {
	console.log("In ajax POST - get JWT");

	var payload = {username: JWTuser, password: JWTpass};

	console.log("--------PAYLOAD------------");
	console.log(payload);
	console.log(JSON.stringify(payload));

	$.ajax({
		url: apiURL + apiPathJWT,
		dataType: 'json',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(payload),
		success: function(result){
			console.log("in success");
			console.log(result);
			//Call the next function, passing the jwt token
			nextFunction(result.token);
		},
		error: function(){
			console.log( "jwt fail" );
		}
	})
}

//Check for new drink info/picture
//If so, retrieve new data & update page
function initiateDrinkUpdate() {
	
	//Get JWT Token and pass in the callback function
	getJWT(getAllDrinks)
	
}

function getMostRecentDrinkId(allDrinks) {

	var latestDrink = allDrinks[allDrinks.length - 1];

	//If most recent drink id differs from current drink id, update drink
	if (latestDrink.id != currentDrinkId) {
		currentDrinkId = latestDrink.id;
		updateDrinkInfo(latestDrink);
	}

}

function getDrinkImage(jwtToken) {
	console.log("In ajax call: get current drink's image.");
	$.ajax({
		url: apiURL + apiPathLastDrinkImage,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + jwtToken);
		},
		type: "GET",
		success: function(result) {

			//Update the image displayed on the UI
			updateDrinkImage(result);

			//Initiate calculations for catstats section
			performStats();

		},
		failure: function(result) {
			console.log("a failure");
		}
	});
}

function getMostRecentImage() {
	console.log("In ajax call: Get most recently available image.");
	$.ajax({
		url: apiURL + apiPathLastDrinkImage,
		type: "GET",
		success: function(result) {

			//Update the image displayed on the UI
			updateDrinkImage(result);

			//Initiate calculations for catstats section
			performStats();

		}
	});
}

function getAllDrinks(jwtToken) {
	console.log("In ajax call: Get all drinks");
	$.ajax({
		url: apiURL + apiPathAllDrinks,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + jwtToken);
		},
		type: "GET",
		success: function(result) {
			console.log("good job");
			console.log(result);
			
			allDrinks = result;
			
			getMostRecentDrinkId(allDrinks);

		},
		failure: function(result) {
			console.log("bad job");
		}
	});
}

function favoriteImage(username, imageId) {
	console.log("In ajax POST - favorite image");

	var payload = {username: username, imageId: imageId};

	console.log("--------PAYLOAD------------");
	console.log(payload);
	console.log(JSON.stringify(payload));

	$.ajax({
		url: apiURL + apiPathFavoriteImage,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + jwtToken);
		},
		dataType: 'json',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(payload),
		success: function(){
			console.log("favorite successful")
		},
		error: function(){
			console.log( "great job" );
		}
	})
}

function updateDrinkInfo(latestDrink) {
	
	//Update drink time
	var drinkTime = formatDateToTime(new Date(latestDrink.startTime));
	var drinkTimeString = "Shasta took this drink at " + drinkTime + " EST.";
	$('#drinkTime').text(drinkTimeString);

	//Perform calculations for stats, charts, etc.
	//First ensure that historical drink data has been loaded
	var drinkStats = calculateDrinkDetails();

	//Update drink count, time since last, and duration
	var drinkCountString = "She has taken " + drinkStats[0] + " drinks today.";
	var elapsedTimeString = drinkStats[1] + ", and she was at the fountain for " + drinkStats[2] + ((drinkStats[2] > 1) ? " minutes." : "minute.");
	$('#drinkCount').text(drinkCountString);
	$('#elapsedTime').text(elapsedTimeString);

	//Call function to update image (if image update flag is set to on)
	if (updateImage == true) {
		getJWT(getDrinkImage);
	} else {
		performStats();
		updateImage = true;
	}
	

}

function updateDrinkImage(latestImage) {
	
	currentImageId = latestImage.id;

	//Check if drink image has been liked. If so, turn like button red
	if (userIsLoggedIn && userLikedCurrentPicture()) {
		$('#like-heart').css("color","red")
	}



}

//Format time to be shown on screen
function formatDateToTime(jsDate) {

	//Determine AM PM
	var amPm = (jsDate.getHours() < 12) ? " AM" : " PM";

	//Format from 24H to 12H time & concatenate minutes + AM||PM
	var timeString = ((jsDate.getHours() + 11) % 12 + 1) + ":" + jsDate.getMinutes() + amPm;

	return timeString;
}

//Perform calculations on drink data
function calculateDrinkDetails() {

	//Calculate drinks taken today & set html text
	var drinksToday = getDrinksToday();
	
	//Calculate time since last drink
	var timeSinceLastDrinkString = getTimeSinceLastDrink();

	//Calculate duration of drink
	var durationOfDrink= getDurationOfDrink();

	return [drinksToday, timeSinceLastDrinkString, durationOfDrink];

}

function getDrinksToday() {
	
	var drinksToday = 0;

	//Current time
	var currentDate = new Date();
	
	//Loop through all drink start dates; sum all drinks ocurring so far today
	for (i = 0; i < allDrinks.length; i++) {
		if (currentDate.getDay() == new Date(allDrinks[i].startTime).getDay()) {
			drinksToday++;
		}
	}

	return drinksToday;
}

//Calculate time between trips to the water fountain
function getTimeSinceLastDrink() {

	var elapsedMillis = new Date(allDrinks[allDrinks.length -1].startTime) - new Date(allDrinks[allDrinks.length - 2].endTime);

	//Calculate hours and minutes
	var hours = Math.floor(elapsedMillis / (1000 * 60 * 60));
	var minuteRemainder = Math.round(elapsedMillis / (1000 * 60) % (Math.floor(hours) * 60));

	//Format string
	//I am nesting ternaries and I do expect to regret this in the future
	//In fact I already regret it
	var hoursString = (hours >= 1) ? hours + ((hours > 1) ? " hours and " : " hour and ") : "";
	var minutesString = (minuteRemainder >= 1) ? minuteRemainder + " minutes" : minuteRemainder + " minute";
	var elapsedTimeString = "It had been " + hoursString + minutesString + " since her last drink";

	return elapsedTimeString;
}

//Calculate time shasta was at water bowl
function getDurationOfDrink() {

	var lastDrink = allDrinks[allDrinks.length -1];

	var durationMillis = new Date(lastDrink.endTime) - new Date(lastDrink.startTime);

	var durationMinutes = Math.round(durationMillis / (1000 * 60));

	return durationMinutes

}

function performStats() {

	//DEBUG OUTPUT TO MAKE SURE FLOW IS CORRECT
	//At this point, allDrinks should be populated with data
	console.log("----------Checking that allDrinks is defined-------------");
	console.log(allDrinks);


	//Create array of days from first day of ThirstyCat data to present
	var elapsedDates = createElapsedDatesArray();
	var test2 = getDrinksPerDay(elapsedDates);
	var arrayDrinksPerDay = [elapsedDates, getDrinksPerDay(elapsedDates)];

	//Create array: number of drinks per day
		//Chart : Bar Chart : Drinks vs Day
//	createCharts(arrayDrinksPerDay, allDrinks, 0);


	//Create array: avg number of drinks by day, grouped by day of week
		//Chart : Bar Chart : Avg # drinks vs Day of Week

	//Create array: number of drinks per hour per day
		//Chart : Heat Map : Drinks per Hour per Day [x =day, y=hour, z=drinks]
	initiateChartCreation(arrayDrinksPerDay, allDrinks);

	//Create array: avg number of drinks per hour per day, grouped by day of week
		//Chart : Heat Map : Avg # drinks per Hour per Day of Week

	//Calculate stat: Average number of drinks per day

	//Calculate stat: Max number of drinks per day

	//Calculate stat: Min number of drinks per day

}

//Builds and array of dates, 1 per day, beginning at the first data record until present
function createElapsedDatesArray() {
	var startDate = new Date(allDrinks[0].startTime);
	var currentDate = new Date();
	var allDates = [startDate];
	while(true) {

		var previousDate = allDates[allDates.length -1];
		var dateToAdd = new Date(previousDate.getTime() + 24 * 60 * 60 * 1000);

		//Compare dateToAdd to current date; add to array if it is less than current date
		if (dateToAdd < currentDate) {
			allDates.push(dateToAdd);
		} else {
			break;
		}
	}

	return allDates;

}

//Get the number of drinks taken each day from start of data collection
function getDrinksPerDay(elapsedDates) {

	//Hold count of drinks taken each day
	var drinkCount = new Array(elapsedDates.length).fill(0);

	//Loop over elapsed dates
	//TODO: Poor performance here O(n^2), should refactor to not use nested loop
	//Dear any potential employers looking at this.. please forgive me!
	for (i = 0; i < elapsedDates.length - 1; i++) {
		
		var dateBracketLow = new Date(elapsedDates[i].getFullYear() + "-" + (elapsedDates[i].getMonth() + 1) + "-" + elapsedDates[i].getDate());
		var dateBracketHigh = new Date(elapsedDates[i+1].getFullYear() + "-" + (elapsedDates[i+1].getMonth() + 1) + "-" + elapsedDates[i+1].getDate());
		
		for (let drink of allDrinks) {

			var dateStartTime = new Date(drink.startTime);

			if (dateStartTime >= dateBracketLow && dateStartTime < dateBracketHigh) {
				drinkCount[i] ++;
			}
		}

	}

	return drinkCount;

}

function initiateChartCreation(arrayDrinksPerDay, allDrinks) {
	createCharts(arrayDrinksPerDay, allDrinks, chartId);
}

function userIsLoggedIn() {
	var username = $('.username-holder').text();
	console.log("Username length: " + username.length);
	return (username.length > 0);
}

function userLikedCurrentPicture() {

	//Get list of user's liked pictures
	getJWT(getLikedImages)

}

function getLikedImages(jwtToken) {

	console.log("In ajax call: Get user's liked images");
	$.ajax({
		url: apiURL + apiPathLikedImages + username,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + jwtToken);
		},
		type: "GET",
		success: function(result) {
			console.log("good job");
			console.log(result);
			
			likedImages = result;

			console.log("liked images: ");
			console.log(likedImages);

		},
		failure: function(result) {
			console.log("bad job");
		}
	});

}


