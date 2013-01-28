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
	$.post(SERVLET_LOCATION, {action: "getUserData"}, function(response) {
		var obj = $.parseJSON(response);
		$('#username').html(obj.username);
	});

	$.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
		var obj = $.parseJSON(response);
		var outputStr = outputMonsters(obj, true, buildMonsterHTML);
		$('#monster_list').html(outputStr);
	});
});
