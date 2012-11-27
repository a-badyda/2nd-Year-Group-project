/*
Battle.js
----------------
Javascript to deal with both battling other monsters and
breeding with other monsters.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

$(document).ready(function() {
	//get a list of the users monsters
	$.post(SERVLET_LOCATION, {action: "getMonsters"}, function() {
		
	});
	
	//get a list of the friends and there monsters
	$.post(SERVLET_LOCATION, {action: "getFriendsMonsters"}, function() {
	
	});
	
	$(".current_friend").click(function(){
		//show monsters on click
	});
	
	
});
