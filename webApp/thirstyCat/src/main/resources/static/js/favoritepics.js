var apiURL = "http://localhost:8080/api";
// var apiURL = "http://thirstycat.us-east-1.elasticbeanstalk.com/";
var apiPathLikedImages = "/image/favorites/";
var apiPathJWT = "/authenticateJWT";
var username;

$( document ).ready(function() {

    //Load user's liked images
    username = $('.username-holder').text();
    getJWT(getLikedImages);

	//Replace featured favorite picture with clicked favorite picture
	$('.fav-pic').click(function() {
        //Get clicked image src
        var src = $(this).attr('src');
        //Set clicked image as featured imaged
        $('#featured-pic').attr('src', src);
	});

});

function getJWT(nextFunction) {
	console.log("In ajax POST - get JWT");
	var payload = {username: JWTuser, password: JWTpass};
	$.ajax({
		url: apiURL + apiPathJWT,
		dataType: 'json',
		type: 'post',
		contentType: 'application/json',
		data: JSON.stringify(payload),
		success: function(result){
			nextFunction(result.token);
		},
		error: function(result){
            console.log("Couldn't retrieve JWT");
		}
	})
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
			likedImages = result;
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