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
	
	document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {
		function decode(s) {
			return decodeURIComponent(s.split("+").join(" "));
		}

		$_GET[decode(arguments[1])] = decode(arguments[2]);
	});

	var page = $_GET["page"];
	
	if(page == "profile" || page == "battle" || page == "friends" || page == "help") {
		$.get(page + '.html', function (response) {
			$('body').append(response);
		});
	} else {
		window.location.replace("login.html");
	}
});

