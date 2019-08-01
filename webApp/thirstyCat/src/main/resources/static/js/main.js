//Some variables I'm using globally
// * = ones to potentially be refactored to improve code quality
var currentDrinkId;	//*
var currentImageId;	//*
var allDrinks = [];	//*
 var apiURL = "http://localhost:8080/api";
// var apiURL = "https://www.thethirstycat.net/api";
var apiPathLastDrink = "/drink/lastDrink";
var apiPathAllDrinks = "/drink/allDrinks";
var apiPathLastDrinkImage = "/image/lastImage";
var apiPathImageByDrinkID = "/image/imageByDrink/";
var apiPathFavoriteImage = "/image/favorite";
var apiPathLikedImages = "/image/favorites/";
var apiPathJWT = "/authenticateJWT";
var updateImage = true;	//*
var username;	//*
var chartId = 1;	//*
var jwt;	//*
var likedImages = [];	//*

$( document ).ready(function() {
	
	//Explanation for this REALLY hacky hack:  I've configured spring security / aws for  http -> https redirects;
	//	It works correctly, except for the very first time that a browser visits the page. No redirect happens. If the paged is then refreshed,
	//	it redirects to https as expected. Must be related to cache/cookies/etc, but have not been able to find a proper solution.
	//	So, this checks to see if the url has "http://". If it does, I want to send the page right to the https url
	//	https works. Problem solving, right... ? :O
	if (window.location.href.toLowerCase().includes("http://")) {
		//Try to redirect without adding entry to the browser history
		//Doesn't work on all browsers though (like edge/IE), so catch error and navigate to https page as fallback
		try {  
//			window.location.replace("https://www.thethirstycat.net");
		} catch(e) {
			window.location = "https://www.thethirstycat.net";
		}
	}

	//Flow Control-------------------------------------------------------------------

	//Check to see if user is logged in; if so, load their liked pictures
	username = $('.username-holder').text();
	if (userIsLoggedIn(username)) {
		getLikedImages();
	}

	initiateDrinkUpdate();

	// Check to see if new drink has been taken every minute. If so, update info on page
	setInterval(function() {
		initiateDrinkUpdate();
	}, (1000 * 60));

	//End Flow Control-----------------------------------------------------------------

	//Event Listeners------------------------------------------------------------------

	//Update graph on dropdown selection
	$('.select-dropdown').change(function() {
		
		//Turn off image update
		//Because in the performStats flow (called below) the image is updated if this flag is true
		//We don't need to do that in this flow
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

	//Handle favoriting of pictures.
	username = $('.username-holder').text();
	$('#like-heart').click(function() {

		//Check if there is a logged in user. If so, initiate favoriting of picture
		//If not, prompt to log in
		if (userIsLoggedIn(username) && !userLikedCurrentPicture(username, currentDrinkId)) {
			$(this).css("color","red");
			favoriteImage(currentDrinkId);
		} else {
			$('#login-prompt-container').css({"display":"flex"});
			$('#login-prompt-container').show();
		}
		
	});

})

//Check for new drink info/picture
//If so, retrieve new data & update page
function initiateDrinkUpdate() {
	getAllDrinks();
}

function getMostRecentDrinkId(allDrinks) {

	var latestDrink = allDrinks[allDrinks.length - 1];

	//If most recent drink id differs from current drink id, update drink
	if (latestDrink.id != currentDrinkId) {
		currentDrinkId = latestDrink.id;
		updateDrinkInfo(latestDrink);
	}

}

function getDrinkImage() {

	$.ajax({
		url: apiURL + apiPathLastDrinkImage,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
		},
		type: "GET",
		success: function(result, status, xhr) {

			sessionStorage.setItem("jwt", xhr.getResponseHeader("auth"));

			//Update the image displayed on the UI
			updateDrinkImage(result);

			//Initiate calculations for catstats section
			performStats();

		},
		failure: function(result, status, xhr) {

		}
	});
}

function favoriteImage(id) {
	
	var payload = {username: username, imageId: id};

	$.ajax({
		url: apiURL + apiPathFavoriteImage,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
		},
		dataType: 'json',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(payload),
		success: function(result, status, xhr){
			sessionStorage.setItem("jwt", xhr.getResponseHeader("auth"));
		}
	});
}

function getMostRecentImage() {
	$.ajax({
		url: apiURL + apiPathLastDrinkImage,
		type: "GET",
		success: function(result, status, xhr) {

			//Update the image displayed on the UI
			updateDrinkImage(result);

			//Initiate calculations for catstats section
			performStats();

		}
	});
}

function getAllDrinks(jwtToken) {

	$.ajax({
		url: apiURL + apiPathAllDrinks,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
		},
		type: "GET",
		success: function(result, status, xhr) {
			
			sessionStorage.setItem("jwt", xhr.getResponseHeader("auth"));
			
			allDrinks = result;
			
			getMostRecentDrinkId(allDrinks);

		},
		failure: function(result, status, xhr) {
			jwtToken  = null;
		}
	});
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
	var drinkCountString = "She has taken " + drinkStats[0] + (drinkStats[0] == 1 ? " drink" : " drinks") + " so far today.";
	var elapsedTimeString = drinkStats[1] + ", and she was at the fountain for " + drinkStats[2] + ((drinkStats[2] > 1) ? " minutes." : " minute.");
	$('#drinkCount').text(drinkCountString);
	$('#elapsedTime').text(elapsedTimeString);

	//Call function to update image (if image update flag is set to on)
	if (updateImage == true) {

		getDrinkImage();

	} else {
		performStats();
		updateImage = true;
	}
	

}

function updateDrinkImage(latestImage) {
	
	currentImageId = latestImage.id;
	var imageBytes = latestImage.imageByteArray;

	$('#drinkPic').show();
	$('#drinkPic').attr('src', `data:image/jpg;base64,${imageBytes}`);

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
		var dayEqual = (currentDate.getDate() == new Date(allDrinks[i].startTime).getDate());
		var monthEqual = (currentDate.getMonth() == new Date(allDrinks[i].startTime).getMonth());
		var yearEqual = (currentDate.getFullYear() == new Date(allDrinks[i].startTime).getFullYear());
		if (dayEqual && monthEqual && yearEqual) {
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

	if (hours == 0) {
		var minuteRemainder = Math.round(elapsedMillis / (1000 * 60));
	} else {
		var minuteRemainder = Math.round(elapsedMillis / (1000 * 60) % (Math.floor(hours) * 60));
	}

	//Format string
	//I am nesting ternaries and I do expect to regret this in the future
	//In fact I already regret it
	//Update 7-30-19: Revisiting this a few weeks after I wrote it and can confirm my regret
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

	//Create array of days from first day of ThirstyCat data to present
	var elapsedDates = createElapsedDatesArray();
	var arrayDrinksPerDay = [elapsedDates, getDrinksPerDay(elapsedDates)];

	initiateChartCreation(arrayDrinksPerDay, allDrinks);

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
	//TODO: O(n^2), Can I refactor to not use nested loop
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

	return (username.length > 0);
}

function userLikedCurrentPicture() {

	var imageIsAlreadyLiked = likedImages.includes(currentDrinkId);

	return imageIsAlreadyLiked;

}

function getLikedImages() {

	$.ajax({
		url: apiURL + apiPathLikedImages + username,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
		},
		type: "GET",
		success: function(result, status, xhr) {

			sessionStorage.setItem("jwt", xhr.getResponseHeader("auth"));

			likedImages = result;

		},
		failure: function(result, status, xhr) {

		}
	});

}


