/*
Friends.js
----------------
Javascript to deal with user input from the friends page.
Also loads a rich list.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

//when document is ready
$(document).ready(function() {

	//load a list of all friends
	$friend = $.post(SERVLET_LOCATION, {action: 'getFriends'}, function(response) {
	 	var obj = $.parseJSON(response);
	 	var output = '<table><tr><th>Name</th></tr>';
	 	output += '<tr>' + outputList(outputFriendsList, obj.Friends) + '</tr>';
	 	output += '</table>';
	 	$('#friends_list').append(output);
	 });

	//when the list of friends is loaded, load a list of the richest friends
	 $friend.done(function() {
	 	$.post(SERVLET_LOCATION, {action: 'getRichList'}, function(response) {
	 		var obj = $.parseJSON(response);
	 		var output = '<br /><table><tr><th>Name</th><th>Money</th></tr>';
	 		output += outputList(outputRichList, obj.RichList);
	 		output += '</table>';
	 		$('#rich_list').append(output);
	 	});
	 });
	
	//event handler to send a friend request.
	$(".send_request .button_add").click(function() {
		var email = $(".friend_email").val();
		$.post(SERVLET_LOCATION, {action: "addFriend", username: email}, function(response) {
			$('.email_response').html(response);
		});
		return false;
	});
	
});

//Write the friend list data to a HTML string
function outputFriendsList(key, val) {
	var output = '<tr>';
	output += '<div class="friend">';
	output += '<input class="friend_id" type="hidden" ' + val.id + '"></input>';
	output += '<td><h4>' + val.username + '</h4></td>';
	output += '</div></tr><br />';
	return output;
}

//Write the rich list data to a HTML string
function outputRichList(key, val) {
	var output = '<tr>';
	output += '<div class="friend">';
	output += '<td><h4>' + val.username + '</h4></td><td>'+ val.cash +'</td>';
	output += '</div></tr><br />';
	return output;
}
