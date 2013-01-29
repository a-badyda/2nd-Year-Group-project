$(document).ready(function() {
	//get all notifications on load.
	$.post(SERVLET_LOCATION, {action: 'getAllResults'}, function(response) {
		var obj = $.parseJSON(response);
		var outputStr = outputList(writeNotification, obj.Notifications);
		$('.results_list').append(outputStr);
		addResponseEvents();
	});

	function addResponseEvents() {
		$(".decline").submit(function() {
			var parent = $(this).parent();
			var notification_id = $(parent + " .id").val();
			$.post(SERVLET_LOCATION, {action: "declineRequest", id: notification_id}, writeResponse);
			return false;
		});
	}

	function writeResult(key, val) {
		var current = val;
		var type = current["Type"];
		var vtype = verboseType(type);
		var description = current.From + vtype;
		
		var outputStr = '<div id="result_'+current.id+'" class="result"><p>'+description+'</p>';
		outputStr += '<input type="hidden" id="' + current.id + '"class="id" value="'+current.id+'"></input>';

		if (type == "battle_results") {
			//todo
		} else if (type == "breed_result") {
			//todo
		} else if (type == "buy_result") {
			//todo
		} else if (type == "friend_accepted" ||) {
			//todo
		}

		outputStr += '<input type="submit" class="decline" value="OK"></input>';

		outputStr += '</div>';
		return outputStr;
	}

	function writeResponse(response) {
		$('#result_' + notification_id).html("<span>"+response+"</span>");
	}
});
	