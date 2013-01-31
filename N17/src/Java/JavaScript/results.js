/*
Results.js
----------------
JavaScript for the results page.
Loads and displays data from the server about result notifications
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

//load data when ready
$(document).ready(function() {
	//get all notifications on load.
	$.post(SERVLET_LOCATION, {action: 'getAllResults'}, function(response) {
		var obj = $.parseJSON(response);
		var outputStr = '';
		outputStr += outputList(writeResult, obj.Results);
		$('.results_list').append(outputStr);
		addResponseEvents();
	});

	//Add event handlers for clicking ok on the results.
	function addResponseEvents() {
		$(".decline").on('click', function() {
			var results_id = $(this).attr('id');
			$.post(SERVLET_LOCATION, {action: "declineRequest", id: results_id, result: true}, writeResponse);
		});
	}

	/////////////////////////////////////////
	// Dynamic HTML writer functions
	/////////////////////////////////////////

	//write a single result to the DOM
	function writeResult(key, val) {
		var current = val;
		var type = current["Type"];
		var vtype = verboseType(type);
		var description = current.From + vtype;
		
		var outputStr = '<div id="result_'+current.ID+'" class="result"><p>'+description+'</p>';
		outputStr += '<input type="hidden" id="' + current.ID + '"class="id" value="'+current.ID+'"></input>';

		if (type == "battle_results") {
			outputStr +=writeBattleResult(key, val);
		} else if (type == "breed_result") {
			outputStr += '<div class="breed_results">';
			outputStr += outputList(writeBaby, val.babies);
			outputStr += '</div>';
		} else if (type == "buy_result") {
			outputStr += writeBuyResult(key, val);
		}
		
		outputStr += '<input id="'+current.ID+'" type="button" class="decline" value="OK"></input>';
		outputStr += '</div>';
		return outputStr;
	}

	//write a single battle result to the DOM
	function writeBattleResult(key, val) {
		var output = ''; 
		output += '<table class="battle_results>"';
		output += '<tr>' + buildMonsterHTML(key, val.my_monster) + '</tr>';
		output += '<tr>' + buildMonsterHTML(key, val.friend_monster) + '</tr>'; 
		output += '<tr><td>'+val.message+'</td></tr>';
		output += '</tr><td>Money Exchanged: '+val.cash+'</td></tr>';
		output += '</table>';
		return output;
	}

	//write a bady to the DOM
	function writeBaby(key, val) {
		var output ='';
		output += '<div class="baby">';
		output += buildMonsterHTML(key, val);
		output += '</div>';
		return output;
	}

	//write a buy result to the DOM
	function writeBuyResult(key, val) {
		var output = ''; 
		output += '<table class="buy_results>"';
		output += '<tr>' + buildMonsterHTML(key, val.friend_monster) + '</tr>';
		output += '<tr><td>'+val.message+'</td></tr>';
		output += '<tr><td>Money Exchanged: '+val.cash+'</td></tr>';
		output += '</table>';
		return output;
	}

	//hide the result when the server has responded deleting it.
	function writeResponse(response) {
		$(this).closest('.result').fadeOut();
	}
});
	