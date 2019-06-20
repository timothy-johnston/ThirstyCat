var currentDrinkId;
var apiURL = "http://localhost:8080";
var apiPathLastDrink = "/lastDrink";
var apiPathLastDrinkImage = "/lastImage/";

$( document ).ready(function() {
	
	//On initial page load, grab the most recent drink info + picture
	initiateDrinkUpdate();


	
})


//AJAX call to our REST service for daily forecast
console.log("about to make ajax call");
function ajaxGetAllDrinks(endpoint) {
	console.log("in ajax call");
	return $.ajax({
		url: endpoint,
		type:"GET",
		success: function(result) {
			console.log("result is: ");
			console.log(result);
		}
	});
}

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

function updateDrinkInfo(drinkInfo, imageBytes) {
	
	//Update drink time
	var drinkTime = formatDateToTime(new Date(drinkInfo.startTime));
	var drinkTimeString = "Shasta took this drink at " + drinkTime + " EST.";
	$('#drinkTime').text(drinkTimeString);
	
	console.log("made it down here!");
	console.log("The drink info: ");
	console.log(drinkInfo);
	console.log("The image byte array: ");
	console.log(imageBytes);
}

//Format time to be shown on screen
function formatDateToTime(jsDate) {

	//Determine AM PM
	var amPm = (jsDate.getHours() < 12) ? " AM" : " PM";

	//Format from 24H to 12H time & concatenate minutes + AM||PM
	var timeString = ((jsDate.getHours() + 11) % 12 + 1) + ":" + jsDate.getMinutes() + amPm;

	return timeString;
}
