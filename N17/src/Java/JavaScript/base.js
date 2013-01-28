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
	var $_GET = {};
		
	//logout button.
	$(".logout").submit(function() {
		alert('pressed');
		$.post(SERVLET_LOCATION, {action: "logout"}, function(response) {
			alert("sent");
			window.location.replace("login.html");
		});
		return false;
	});

	//Request to check if the user is logged in.
	$.post(SERVLET_LOCATION, {action: "isLoggedIn"}, function(response) {
		var obj = $.parseJSON(response);
		if(obj.login) {
			//extract the GET variable from the url.
			document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {
				function decode(s) {
					return decodeURIComponent(s.split("+").join(" "));
				}

				$_GET[decode(arguments[1])] = decode(arguments[2]);
			});

			var page = $_GET["page"]; //get what page we are on.

			//select if it is a valid page.
			if(page == "profile" || page == "battle" || page == "friends" || page == "help" || page == "notification") {
				$.get(page + '.html', function (response) {
					$('body').append(response);
				});
				
			} else {
				window.location.replace("login.html");
			}
		} else {
			window.location.replace("login.html");
		}
	});
});

