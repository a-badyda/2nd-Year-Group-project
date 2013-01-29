/*
Results.js
----------------
JavaScript for the results page.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

$(document).ready(function() {
	//get all notifications on load.
	$.post(SERVLET_LOCATION, {action: 'getAllResults'}, function(response) {
		var obj = $.parseJSON(response);
		var outputStr = outputList(writeResult, obj.Results);
		$('.results_list').append(outputStr);
		addResponseEvents();
	});

	//Add event handlers after load.
	function addResponseEvents() {
		$(".decline").on('submit', function() {
			var parent = $(this).parent().attr('id');
			var results_id = $(parent + " .id").val();
			$.post(SERVLET_LOCATION, {action: "declineRequest", id: results_id}, writeResponse);
			return false;
		});
	}

	/////////////////////////////////////////
	// Dynamic HTML writer functions
	/////////////////////////////////////////
	function writeResult(key, val) {
		var current = val;
		var type = current["Type"];
		var vtype = verboseType(type);
		var description = current.From + vtype;
		
		var outputStr = '<div id="result_'+current.ID+'" class="result"><p>'+description+'</p>';
		outputStr += '<input type="hidden" id="' + current.ID + '"class="id" value="'+current.ID+'"></input>';

		if (type == "battle_results") {
			writeBattleResult(key, val);
		} else if (type == "breed_result") {
			outputStr += '<div class="breed_results">';
			outputStr += outputList(writeBaby, obj.babies);
			outputStr += '</div>';
		} else if (type == "buy_result") {
			writeBuyResult(key, obj.);
		}
		

		outputStr += '<input type="submit" class="decline" value="OK"></input>';

		outputStr += '</div>';
		return outputStr;
	}

	function writeBattleResult(key, val) {
		var output = ''; 
		output += '<div class="battle_results>"';
		output += buildMonsterHTML(key, val.my_monster);
		output += buildMonsterHTML(key, val.friend_monster);
		output += '<p>'+val.message+'</p>';
		output += '<p>Money Exchanged: '+val.cost+'</p>';
		output += '</div>'
	}

	function writeBaby(key, val) {
		var output ='';
		output += '<div class="baby">';
		buildMons
		output += '</div>';
		return output;
	}

	function writeBuyResult(key, val) {
		var output = ''; 
		output += '<div class="buy_results>"';
		output += buildMonsterHTML(key, val.friend_monster);
		output += '<p>'+val.message+'</p>';
		output += '<p>Money Exchanged: '+val.cost+'</p>';
		output += '</div>'
	}

	function writeResponse(response) {
		$('#result_' + notification_id).html("<span>"+response+"</span>");
	}
});
	