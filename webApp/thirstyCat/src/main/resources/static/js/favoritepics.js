//var apiURL = "http://localhost:8080/api";
// var apiURL = "http://thirstycat.us-east-1.elasticbeanstalk.com/api";
var apiURL = "http://www.thethirstycat.net";
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
		console.log("trying to replace featured");
        //Get clicked image src
		var src = $(this).attr('src');
		console.log("new src: " + src);
        //Set clicked image as featured imaged
        $('#featured-pic').attr('src', src);
	});

	// setInterval(function() {
	// 	var test = $('.fav-pic');
	// 	console.log("Test: ");
	// 	console.log(test)
	// }, 1000)
	var test = $('.fav-pic');
	console.log("Test: ");
	console.log(test)

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

	if (imageCount == 0) {
		handleNoLikedImages();
	}

	for (var i = 0; i < imageCount; i++) {

		console.log("not sure if this is working. i = " + i);

		getAndAppendImage(likedImageIds[i], i);

	}

}

function getAndAppendImage(id, imagePosition) {

	$.ajax({
		url: apiURL + apiPathImageByDrinkID + id,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
		},
		type: "GET",
		success: function(result, status, xhr) {

			console.log("RESULT:::");
			console.log(result);

			sessionStorage.setItem("jwt", xhr.getResponseHeader("auth"));

			//Append new image and set source to retrieved byte array
			var imageBytes = result.imageByteArray;
			console.log("Image bytes: " + imageBytes);
			var picID = "pic-" + Date.now().toString();
			$('#pic-grid-container').append("<div class='fav-pic-container'><img id=" + picID + " class='fav-pic' src='' alt='Picture of Shasta taking a drink'></img></div>");
			$('#' + picID).attr('src', `data:image/jpg;base64,${imageBytes}`);


			//If this is the most recently liked image, set it as the currently featured image
			if (imagePosition == 0) {
				$('#featured-pic').attr('src', `data:image/jpg;base64,${imageBytes}`);
				$('#featured-pic').show();
			}


			//Enable function to replace featured favorite picture with clicked favorite picture
			$('.fav-pic').click(function() {
				//Get clicked image src
				var src = $(this).attr('src');
				//Set clicked image as featured imaged
				$('#featured-pic').attr('src', src);
			});

		},
		failure: function(result, status, xhr) {

			console.log("get image failure");


		}
	});
}

function handleNoLikedImages() {
	$('#no-favs-container').removeAttr('hidden');
}

//Appends each image of the passed in array to the picture grid
// function populateImages() {
// 	console.log("liked images length: " + likedImages.length)
//     for (var i = 0; i < likedImages.length(); i++) {
// 		console.log("i is: " + i);
//         $('#pic-grid-container').append("<div class='fav-pic-container'><img class='fav-pic' src='/media/catPic.jpg' alt='Picture of Shasta taking a drink'></img></div>");
//     }
// }