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
		var obj = $.parseJSON(response);
		$.each(obj.Notifications, function(key, val) {
			var current = $.parseJSON(val);
			var description = "New ";
			description += current.type.toLowerCase();
			description += " request from ";
			description += current.from;
			
			var outputStr = '<div class="notification_request"><p>'+description+'</p>';
			outputStr += '<input type="hidden" class="id" value="'+current.id+'"></input>';
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
