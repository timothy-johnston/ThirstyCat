var apiURL = "http://localhost:8080/api";
// var apiURL = "http://thirstycat.us-east-1.elasticbeanstalk.com/";
var apiPathLikedImages = "/image/favorites/";
var apiPathJWT = "/authenticateJWT";
var username;


$( document ).ready(function() {

    //Load user's liked images
    username = $('.username-holder').text();
    getJWT(getLikedImages);

	//Expand picture when clicked on
	$('.fav-pic').click(function() {
		//Reset size of currently expanded image (if any)
//		$('.fav-pic-container').css({"width":"200px","height":"150px"});
//		$('.fav-pic').css({"width":"200px","height":"150px"});
//		$(this).css({"width":"400px","height":"300px"});
//		$(this).find("img").css({"width":"800px","height":"600px"});
        
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
			//Call the next function, passing the jwt token
			console.log("JWT is: " + result.token);
			nextFunction(result.token);
		},
		error: function(result){
            console.log(result);
			console.log( "jwt fail" );
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

			console.log("liked images: ");
            console.log(likedImages);
            
            populateImages(likedImages);

		},
		failure: function(result) {
			console.log("Couldn't retrieve liked images");
		}
	});

}

function populateImages(images) {

    for (i = 0; i < images.length; i++) {
        console.log("trying a thing");
        $('#pic-grid-container').append("<div class='fav-pic-container'><img class='fav-pic' src='/media/catPic.jpg' alt='Picture of Shasta taking a drink'></img></div>");
        // $('#pic-grid-container').append("<p>test </p>");
    }

}