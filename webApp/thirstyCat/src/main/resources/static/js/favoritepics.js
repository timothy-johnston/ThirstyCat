var apiURL = "http://localhost:8080/api";
// var apiURL = "http://thirstycat.us-east-1.elasticbeanstalk.com/";
var apiPathLikedImages = "/image/favorites/";
var username;

$( document ).ready(function() {

    //Load user's liked images
    username = $('.username-holder').text();
	getLikedImages();

	//Replace featured favorite picture with clicked favorite picture
	$('.fav-pic').click(function() {
        //Get clicked image src
        var src = $(this).attr('src');
        //Set clicked image as featured imaged
        $('#featured-pic').attr('src', src);
	});

});


// function getLikedImages(jwtToken) {
// 	console.log("In ajax call: Get user's liked images");
// 	$.ajax({
// 		url: apiURL + apiPathLikedImages + username,
// 		beforeSend: function (xhr) {
// 			xhr.setRequestHeader('Authorization', 'Bearer ' + jwtToken);
// 		},
// 		type: "GET",
// 		success: function(result) {
// 			likedImages = result;
//             populateImages(likedImages);
// 		},
// 		failure: function(result) {
// 			console.log("Couldn't retrieve liked images");
// 		}
// 	});

// }

function getLikedImages(jwtToken) {

	console.log("In ajax call: Get user's liked images");
	$.ajax({
		url: apiURL + apiPathLikedImages + username,
		beforeSend: function (xhr) {
			xhr.setRequestHeader('Authorization', 'Bearer ' + sessionStorage.getItem("jwt"));
		},
		type: "GET",
		success: function(result) {

			sessionStorage.setItem("jwt", result.getResponseHeader("auth"));

			var likedImages = result;

			console.log("liked images: ");
			console.log(likedImages);

			populateImages(likedImages);

			

		},
		failure: function(result) {
			console.log("Couldn't retrieve liked images");
		}
	});

}

//Appends each image of the passed in array to the picture grid
function populateImages(images) {
    for (i = 0; i < images.length; i++) {
        $('#pic-grid-container').append("<div class='fav-pic-container'><img class='fav-pic' src='/media/catPic.jpg' alt='Picture of Shasta taking a drink'></img></div>");
    }
}