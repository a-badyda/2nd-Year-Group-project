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
	
	function addResponseEvents() {
		//user clicks accept.
		$(".accept").on('submit', function() {
			var parent = $(this).parent().attr('id');
			var notification_id = $(parent + " .id").val();
			$.post(SERVLET_LOCATION, {action: "acceptRequest", id: notification_id}, writeResponse);
			return false;
		});
		
		//user clicks decline.
		$(".decline").on('submit', function() {
			var parent = $(this).parent().attr('id');
			var notification_id = $(parent + " .id").val();
			$.post(SERVLET_LOCATION, {action: "declineRequest", id: notification_id}, writeResponse);
			return false;
		});

		//user clicks to view a result.
		$(".view").on('submit', function() {
			window.location.replace("view_results.html");
			return false
		});
	}

	function writeNotification(key, val) {
		var current = val;
		var type = current["Type"];
		var vtype = verboseType(type);
		var description = current.From + vtype;
		
		var outputStr = '<div id="request_'+current.ID+'" class="notification_request"><p>'+description+'</p>';
		outputStr += '<input type="hidden" id="' + current.ID + '"class="id" value="'+current.ID+'"></input>';

		//output different buttons depending on results.
		if (type == "friend_request" || type == "battle_request") {
			//accept or decline the offer.
			outputStr += '<input type="submit" class="accept" value="accept"></input>';
			outputStr += '<input type="submit" class="decline" value="decline"></input>';
		} else if (type == "battle_results" || type == "breed_result" || type == "buy_result") {
			//select to view event.
			outputStr += '<input type="submit" class="view" value="view"></input>';
		} else if (type == "friend_accepted") {
			//mark as seen.
			outputStr += '<input type="submit" class="decline" value="OK"></input>';
		}

		outputStr += '</div>';
		return outputStr;
	}

	function writeResponse(response) {
		$('#request_' + notification_id).html("<span>"+response+"</span>");
	}
});
