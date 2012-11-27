/*
Friends.js
----------------
Javascript to deal with user input from the friends page.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

$(document).ready(function() {

	//load a list of all friends
	$.post(SERVLET_LOCATION, {action: 'getFriends'}, function(response) {
		
	});
	
	//load a list of all pending friends.
	$.post(SERVLET_LOCATION, {action: 'getPending', type: 'friend'}, function(response) {
		
	});
	
	
	//accept a friend request
	$(".pending_friend #button_accept").submit(function() {
		var id = (".pending_friend #pending_friend_id").val();
		$.post(SERVLET_LOCATION, {action: 'acceptPendingFriend', friendid: id}, 
		function(response) {
			
		});
	});
	
	//decline a friend request
	$(".pending_friend #button_decline").submit(function() {
		var id = (".pending_friend #pending_friend_id").val();
		$.post(SERVLET_LOCATION, {action: 'declinePendingFriend', friendid: id}, 
		function(response) {
			
		});
	});
	
	
	//add a new friend
	$("#send_request #button_add").submit(function() {
		var email = $("friend_email").val();
		$.post(SERVLET_LOCATION, {action: "addFriend", username: email}, function(response) {
			
		});
	});
	
});
