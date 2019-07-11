// var apiURL = "http://localhost:8080/api";
var apiURL = "http://thirstycat.us-east-1.elasticbeanstalk.com/api";
var apiPathLikedImages = "/image/favorites/";
var apiPathImageByDrinkID = "/image/imageByDrink/";
var username;
var likedImages = [];
var readyToPopulateImages = false;

$( document ).ready(function() {

    //Load user's liked images
    username = $('.username-holder').text();
	getLikedImageIds();

	//Replace featured favorite picture with clicked favorite picture
	$('.fav-pic').click(function() {
        //Get clicked image src
        var src = $(this).attr('src');
        //Set clicked image as featured imaged
        $('#featured-pic').attr('src', src);
	});

});


function getLikedImageIds() {

	console.log("started ajax get liked images");

	$.ajax({
		url: apiURL + apiPathLikedImages + username,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
		},
		type: "GET",
		success: function(result, status, xhr) {

			console.log("ajax get liked image IDS success");

			sessionStorage.setItem("jwt", xhr.getResponseHeader("auth"));

			likedImageIds = result;

			console.log("liked images: ");
			console.log(likedImageIds);

			getLikedImagesById(likedImageIds);

		},
		failure: function(result, status, xhr) {
			console.log("ajax get liked images failure");
		}
	});

}

function getLikedImagesById(likedImageIds) {

	var imageCount = likedImageIds.length;

	console.log("imageCount = " + imageCount);

	for (var i = 0; i < imageCount; i++) {

		console.log("not sure if this is working. i = " + i);

		if (i == imageCount - 1) {
			readyToPopulateImages = true;
			console.log("ready to populateImages? : " + readyToPopulateImages);
		}

		getImageById(likedImageIds[i], readyToPopulateImages);

	}

}

function getImageById(id, populateImagesFlag) {

	$.ajax({
		url: apiURL + apiPathImageByDrinkID + id,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
		},
		type: "GET",
		success: function(result, status, xhr) {


			console.log("get image success. populateImagesFlag: " + populateImagesFlag);

			sessionStorage.setItem("jwt", xhr.getResponseHeader("auth"));

			likedImages.push(result.imageByteArray);

			if (populateImagesFlag) {
				console.log("time to populate images!")
				populateImages();
			}

		},
		failure: function(result, status, xhr) {

			console.log("get image failure");


		}
	});
}

//Appends each image of the passed in array to the picture grid
function populateImages() {
	console.log("liked images length: " + likedImages.length)
    for (var i = 0; i < likedImages.length(); i++) {
		console.log("i is: " + i);
        $('#pic-grid-container').append("<div class='fav-pic-container'><img class='fav-pic' src='/media/catPic.jpg' alt='Picture of Shasta taking a drink'></img></div>");
    }
}