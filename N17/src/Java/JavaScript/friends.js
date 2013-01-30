/*
Friends.js
----------------
Javascript to deal with user input from the friends page.
Also loads a rich list.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

$(document).ready(function() {

	//load a list of all friends
	// $friend = $.post(SERVLET_LOCATION, {action: 'getFriends'}, function(response) {
	// 	var obj = $.parseJSON(response);
	// 	var output = '<table><tr><th>Name</th></tr>';
	// 	output += '<tr>' + outputList(outputFriendsList, obj.Friends) + '</tr>';
	// 	output += '</table>';
	// 	$('#friends_list').append(output);
	// });

	// $friend.done(function() {
	// 	$.post(SERVLET_LOCATION, {action: 'getRichList'}, function(response) {
	// 		var obj = $.parseJSON(response);
	// 		var output = '<table><tr><th>Name</th><th>Money</th></tr>';
	// 		output += outputList(outputRichList, obj.RichList);
	// 		output += '</table>';
	// 		$('#rich_list').append(output);
	// 	});
	// });
	
	//add a new friend
	$(".send_request .button_add").click(function() {
		var email = $(".friend_email").val();
		$.post(SERVLET_LOCATION, {action: "addFriend", username: email}, function(response) {
			$('email_response').html(response);
		});
		return false;
	});
	
});

function outputFriendsList(key, val) {
	var output = '<tr>';
	output += '<div class="friend">';
	output += '<input class="friend_id" type="hidden" value="' + val.id + '"></input>';
	output += '<td><h4>' + val.username + '</h4></td>';
	output += '</div></tr>';
	return output;
}

function outputRichList(key, val) {
	var output = '<tr>';
	output += '<div class="friend">';
	output += '<td><h4>' + val.username + '</h4></td><td>'+ val.cash +'</td>';
	output += '</div></tr>';
	return output;
}
