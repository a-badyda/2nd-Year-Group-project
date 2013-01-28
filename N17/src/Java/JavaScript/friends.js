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
		var output = outputList(obj.Friends, outputFriendsList);
		$('#friends_list').html(output);
	});

	$.post(SERVLET_LOCATION, {action: 'getRichList'}, function(response) {
		var obj = $.parseJSON(response);
		var output = outputList(obj.RichList, outputRichList);
		$('#rich_list').html(output);
	});
	
	//add a new friend
	$(".send_request #button_add").submit(function() {
		var email = $(".friend_email").val();
		$.post(SERVLET_LOCATION, {action: "addFriend", username: email}, function(response) {
			//write back reponse
		});
		return false;
	});
	
});

function outputFriendsList(key, list) {
	var output = '';
	output += '<div class="friend">';
	output += '<input class="friend_id" type="hidden" value="' + val.id + '"></input>';
	output += '<h4>' + val.username + '</h4>';
	output += '</div>';
}

function outputRichList(key, list) {
	var output = '';
	output += '<div class="friend">';
	output += '<h4>' + val.username + ': '+ val.cash +'</h4>';
	output += '</div>';
	return output;
}
