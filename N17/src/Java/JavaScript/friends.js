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
		var obj = $.parseJSON(response);
		var output;
		
		if(obj.Friends.length != 0) {
			$.each(obj.Friends, function(key, val) {
				output += '<div class="current_friend">';
				output += '<input class="friend_id" type="hidden" value="' + val.id + '"></input>';
				output += '<h4>' + val.username + '</h4>';
				output += '</div>';
			});
		} else {
			output += '<h4>Looks like you have no friends!</h4>';
		}
		$(".friends_list").html(output);
	});
	
	
	//accept a friend request
	$(".button_accept").submit(function() {
		var parent = $(this).parent();
		var id = $(parent + " .pending_friend_id").val();
		$.post(SERVLET_LOCATION, {action: 'acceptPendingFriend', friendid: id}, 
		function(response) {
			$('#friends_response').html(response);
		});
	});
	
	//decline a friend request
	$(".button_decline").submit(function() {
		var parent = $(this).parent();
		var id = (parent + " .pending_friend_id").val();
		$.post(SERVLET_LOCATION, {action: 'declinePendingFriend', friendid: id}, 
		function(response) {
			$('#friends_response').html(response);
		});
	});
	
	//add a new friend
	$(".send_request #button_add").submit(function() {
		var email = $(".friend_email").val();
		$.post(SERVLET_LOCATION, {action: "addFriend", username: email}, function(response) {
			$('#friends_response').html(response);
		});
	});
	
});
