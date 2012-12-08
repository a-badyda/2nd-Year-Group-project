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
	$.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
		
	});
	
	//get a list of the friends and there monsters
	$.post(SERVLET_LOCATION, {action: "getFriends"}, function(response) {
	
	});
	
	$(".current_friend").click(function(){
		//show monsters on click
		$.post(SERVLET_LOCATION, {action: "getFriendsMonsters"}, function(response) {
			//get an display the friends monsters
		});
	});
	
	//handle clicking the battle request button
	$("#friends_list .battle_request").submit(function() {
		newMonsterRequest("Battle");
	});
	
	//handle clicking the breed request button
	$("#friends_list .breed_request").submit(function() {
		newMonsterRequest("Breed");
	});
	
	
	//function to send a battle/breed request
	function newMonsterRequest(type){
		$.post(SERVLET_LOCATION, {action: "new" + type +"Request"}, function(response) {
			$('#response').val(response);
		});
	}
	
});
