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
	$.post(SERVLET_LOCATION, {action: 'getAllNotifications'},function(response) {
		$.each(response['Notifications'], function(key, val) {
		
			var description = "New ";
			description += val["Type"].toLowerCase();
			description += " request from ";
			description += val["From"];
			
			var outputStr = '<div class="notification_request"><p>'+description+'</p>';
			outputStr += '<input type="hidden" class="id" value="'+val["ID"]+'"></input>';
			outputStr += '<input type="submit" class="accept" value="accept notification">';
			outputStr += '<input type="submit" class="decline" value="decline notification">';
			outputStr += '</div>';
			
			$('#notifications_list').append(outputStr);
		
		});
	});
	
	//user clicks accept
	$(".accept").submit(function() {
		var parent = $(this).parent();
		var notification_id = $(parent + " .id").val();
		$.post(SERVLET_LOCATION, {action: "acceptRequest", id: notification_id}, 
		function(response) {
			$("#response").val(response);
		});
	});
	
	//user clicks decline
	$(".decline").submit(function() {
		var parent = $(this).parent();
		var notification_id = $(parent + " .id").val();
		$.post(SERVLET_LOCATION, {action: "declineRequest", id: notification_id});
	});
});
