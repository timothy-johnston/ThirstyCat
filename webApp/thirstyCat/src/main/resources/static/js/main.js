var currentDrinkId;
var allDrinks;
var apiURL = "http://localhost:8080";
var apiPathLastDrink = "/lastDrink";
var apiPathAllDrinks = "/allDrinks";
var apiPathLastDrinkImage = "/lastImage/";

$( document ).ready(function() {
	
	//On initial page load, grab the most recent drink info + picture
	initiateDrinkUpdate();

	//Get all drink data
	getAllDrinks();


	
})

//Check for new drink info/picture
//If so, retrieve new data & update page
function initiateDrinkUpdate() {
	getMostRecentDrinkId();
}

function getMostRecentDrinkId() {
	console.log("In ajax call: Get most recent drink");
	$.ajax({
		url: apiURL + apiPathLastDrink,
		type: "GET",
		success: function(result) {
			//If most recent drink id differs from current drink id, update drink
			if (result.id != currentDrinkId) {
				getMostRecentDrinkImage(result);
			}
		}
	});
}

function getMostRecentDrinkImage(drinkInfo) {
	console.log("In ajax call: Get most recent image");
	$.ajax({
		url: apiURL + apiPathLastDrinkImage,
		type: "GET",
		success: function(result) {
			updateDrinkInfo(drinkInfo, result);
		}
	});
}

function getAllDrinks() {
	console.log("In ajax call: Get all drinks");
	$.ajax({
		url: apiURL + apiPathAllDrinks,
		type: "GET",
		success: function(result) {
			allDrinks = result;
		}
	});
}

function updateDrinkInfo(drinkInfo, imageBytes) {
	
	//Update drink time
	var drinkTime = formatDateToTime(new Date(drinkInfo.startTime));
	var drinkTimeString = "Shasta took this drink at " + drinkTime + " EST.";
	$('#drinkTime').text(drinkTimeString);

	//Perform calculations for stats, charts, etc.
	//First ensure that historical drink data has been loaded
	console.log("Now, in updateDrinkInfo--------------");
	console.log(allDrinks);
	var drinkStats = calculateDrinkStats();
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
function calculateDrinkStats() {

	//Calculate drinks taken today & set html text
	var drinksToday = getDrinksToday();
	
	//Calculate time since last drink;
	var timeSinceLastDrink = getTimeSinceLastDrink();

}

function getDrinksToday() {
	
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

function getTimeSinceLastDrink() {

	elapsedMillis = allDrinks(allDrinks.length -1) - allDrinks(allDrinks.length - 2);

	//Calculate hours and minutes
	hours = Math.floor(elapsedMillis / (1000 * 60 * 60));
	minuteRemainder = Math.round(elaspedMillis / (1000 * 60) % (Math.floor(hours) * 60));

	//Format string
	//I am nesting ternaries and I do expect to regret this in the future
	//In fact I already regret it
	hoursString = (hours >= 1) ? hours + ((hours > 1) ? " hours" : " hour") : "";
	minutesString = (minues >= 1) ? minutes + " minutes" : minute + " minute";

	elapsedTimeString = "It had been " + hoursString + " and " + minutesString + " since her last drink";

	return elapsedTimeString;
}
