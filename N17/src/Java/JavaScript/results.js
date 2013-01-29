$(document).ready(function() {
	//get all notifications on load.
	$.post(SERVLET_LOCATION, {action: 'getAllResults'}, function(response) {
		var obj = $.parseJSON(response);
		var outputStr = outputList(writeResult, obj.Results);
		$('.results_list').append(outputStr);
		addResponseEvents();
	});

	function addResponseEvents() {
		$(".decline").submit(function() {
			var parent = $(this).parent().attr('id');
			var results_id = $(parent + " .id").val();
			$.post(SERVLET_LOCATION, {action: "declineRequest", id: results_id}, writeResponse);
			return false;
		});
	}

	function writeResult(key, val) {
		var current = val;
		var type = current["Type"];
		var vtype = verboseType(type);
		var description = current.From + vtype;
		
		var outputStr = '<div id="result_'+current.ID+'" class="result"><p>'+description+'</p>';
		outputStr += '<input type="hidden" id="' + current.ID + '"class="id" value="'+current.ID+'"></input>';

		if (type == "battle_results") {
			//todo
		} else if (type == "breed_result") {
			//todo
		} else if (type == "buy_result") {
			//todo
		} else if (type == "friend_accepted") {
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
	