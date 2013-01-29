/*
Base.js
----------------
JavaScript for loading HTML data into the base.html page.
Should grab the requested HTML file and output it here.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk
*/

$(document).ready(function () {	

	//logout button.
	$("#logout").click(function() {
		$.post(SERVLET_LOCATION, {action: "logout"}, function(response) {
			window.location.replace("login.html");
		});
	});

	//Request to check if the user is logged in.
	$.post(SERVLET_LOCATION, {action: "isLoggedIn"}, function(response) {
		var obj = $.parseJSON(response);
		if(obj.login) {
			//extract the GET variable from the url.
			var $_GET = getGETvars();
			var page = $_GET["page"]; //get what page we are on.

			//select if it is a valid page.
			if(page == "profile" || page == "battle" || page == "friends" || page == "help" || page == "notification" || page == "auction") {
				$.get(page + '.html', function (response) {
					$('#response').html(response);
				});
				
			} else {
				window.location.replace("login.html");
			}
		} else {
			window.location.replace("login.html");
		}
	});
});

