// var apiURL = "http://localhost:8080/api";
var apiURL = "https://www.thethirstycat.net/api";
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

	$.ajax({
		url: apiURL + apiPathLikedImages + username,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
		},
		type: "GET",
		success: function(result, status, xhr) {

			sessionStorage.setItem("jwt", xhr.getResponseHeader("auth"));

			likedImageIds = result;

			getLikedImagesById(likedImageIds);

		},
		failure: function(result, status, xhr) {

		}
	});

}

function getLikedImagesById(likedImageIds) {

	var imageCount = likedImageIds.length;

	if (imageCount == 0) {
		handleNoLikedImages();
	}

	for (var i = 0; i < imageCount; i++) {

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

			sessionStorage.setItem("jwt", xhr.getResponseHeader("auth"));

			//Append new image and set source to retrieved byte array
			var imageBytes = result.imageByteArray;
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
		}
	});
}

function handleNoLikedImages() {
	$('#no-favs-container').removeAttr('hidden');
}
