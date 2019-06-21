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
			
			//Perform statistics to populate catstats charts and data
			performStats();
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
	var drinkStats = calculateDrinkDetails();
	console.log("----------Drink stats calculated----------");
	console.log(drinkStats);

	//Update drink count, time since last, and duration
	var drinkCountString = "She has taken " + drinkStats[0] + " drinks today.";
	var elapsedTimeString = drinkStats[1] + ", and she was at the fountain for " + drinkStats[2] + ((drinkStats[2] > 1) ? " minutes." : "minute.");
	$('#drinkCount').text(drinkCountString);
	$('#elapsedTime').text(elapsedTimeString);

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

	//Create array of days from first day of ThirstyCat data to present
	var elapsedDates = createElapsedDatesArray()
	console.log("made all the dates");
	console.log(elapsedDates);

	//Create array: number of drinks per day
		//Chart : Bar Chart : Drinks vs Day
	var statArrayDrinksPerDay = getDrinksPerDay(elapsedDates);


	//Create array: avg number of drinks by day, grouped by day of week
		//Chart : Bar Chart : Avg # drinks vs Day of Week

	//Create array: number of drinks per hour per day
		//Chart : Heat Map : Drinks per Hour per Day [x =day, y=hour, z=drinks]

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
}
