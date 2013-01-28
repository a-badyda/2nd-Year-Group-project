/*
Profile.js
----------------
JavaScript for loading data into the profile page.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk
*/

$(document).ready(function() {
	//request data on page load.
	$.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
		
	});
});
