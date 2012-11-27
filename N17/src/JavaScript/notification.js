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
		
	});
	
	//user clicks accept
	$(".notification_request #accept").submit(function() {
		var notification_id = $(".notifications_request #id").val();
		$.post(SERVLET_LOCATION, {action: "acceptRequest", id: notification_id}, 
		function(response) {
			
		});
	});
	
	//user clicks decline
	$(".notification_request #decline").submit(function() {
		var notification_id = $(".notifications_request #id").val();
		$.post(SERVLET_LOCATION, {action: "declineRequest", id: notification_id}, 
		function(response) {
			
		});
	});
});
