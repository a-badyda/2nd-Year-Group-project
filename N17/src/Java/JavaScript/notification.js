/*
Notification.js
----------------
Javascript for the notification HTML.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

$(document).ready(function() {

	//get all notifications on load.
	$.post(SERVLET_LOCATION, {action: 'getAllNotifications'}, function(response) {
		var obj = $.parseJSON(response);
		var outputStr = outputList(writeNotification, obj.Notifications);
		$('.notifications_list').append(outputStr);
		addResponseEvents();
	});
	
	//user clicks accept
	function addResponseEvents() {
		$(".accept").submit(function() {
			var parent = $(this).parent();
			var notification_id = $(parent + " .id").val();
			$.post(SERVLET_LOCATION, {action: "acceptRequest", id: notification_id}, writeResponse);
			return false;
		});
		
		//user clicks decline
		$(".decline").submit(function() {
			var parent = $(this).parent();
			var notification_id = $(parent + " .id").val();
			$.post(SERVLET_LOCATION, {action: "declineRequest", id: notification_id}, writeResponse);
			return false;
		});
	}

	function viewResult(response) {

	}

	function writeNotification(key, val) {
		var current = val;
		var type = current["Type"];
		var vtype = verboseType(type);
		var description = current.From + vtype;
		
		var outputStr = '<div id="request_'+current.id+'" class="notification_request"><p>'+description+'</p>';
		outputStr += '<input type="hidden" id="' + current.id + '"class="id" value="'+current.id+'"></input>';

		outputStr += '<input type="submit" class="accept" value="accept"></input>';
		outputStr += '<input type="submit" class="decline" value="decline"></input>';

		outputStr += '</div>';
		return outputStr;
	}

	function writeResponse(response) {
		alert("Sent!");
		$('#request_' + notification_id).html("<span>"+response+"</span>");
	}

	function verboseType(type) {
		var out = '';

		if(type == "friend_request") {
			out == " has requested to be friends with you.";
		} else if (type == "battle_request") {
			out == " has requested to battle with you.";
		}

		return out;
	}
});
