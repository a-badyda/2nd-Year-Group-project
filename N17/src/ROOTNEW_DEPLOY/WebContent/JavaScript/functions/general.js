/*
General.js
----------------
General JavaScript functions used throughout the program.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

//Get a list of GET variables for the url
function getGETvars() {
	$_GET = {};
	document.location.search.replace(/\??(?:([^=]+)=([^&]*)&?)/g, function () {
		function decode(s) {
			return decodeURIComponent(s.split("+").join(" "));
		}

		$_GET[decode(arguments[1])] = decode(arguments[2]);
	});
	
	return $_GET;
}

//generic function to cycle through a JSON list from the server.
//Returns string of HTML output.
function outputList(func, list, args) {
	var output = '';
	if(list.length != 0) {
		$.each(list, function(key, val) {
			output += func(key, val, args);
		});
	} else {
		output += '<h4>Oops! Looks like there\'s nothing here!</h4>';
	}

	return output;
}

//strip an ID off an selector
function getParentId(obj, selector){
	var parent = $(obj).closest(selector).attr('id');
	var id = parent.replace(/[A-Za-z_$]/g, "");
	return id;
}

//Check if the value is an Int
function is_int(value){ 
  	return ((parseFloat(value) == parseInt(value)) && !isNaN(value));
}

//////////////////////////////////////////////////////
// Generic Dynamic HTML Builder Functions.
//////////////////////////////////////////////////////

//build data for one monster in a monster list.
function buildMonsterHTML(key, mon) {
	outputStr = '';
	outputStr += '<div id="monster_'+mon.ID+'" class="monster">';
	outputStr += '<td id="'+mon.ID+'" class="monster_name">'+mon.monstername+'</td>';
	outputStr += '<div id="stats">';
	outputStr += '<td class="strength">'+mon.strength+'</td>';
	outputStr += '<td class="aggression">'+mon.aggression+'</td>';
	outputStr += '<td class="defense">'+mon.defence+'</td>';
	outputStr += '<td class="health">'+mon.health+'</td>';
	outputStr += '<td class="fertility">'+mon.fertility+'</td>';
	outputStr += '</div></div>';
	return outputStr;
}

//build the data for displaying a single friends data
function buildFriendHTML(key, friend){
	output = '';
	output += '<div id="friend_'+friend.id+'" class="friend">';
	output += '<td id="'+friend.id+'" class="friend_name">'+friend.username+'</td>';
	output += '<td><a class="view_monster" href="#">View Monsters</a></td>';
	output += '<td><div class="monster_list"></div></td>';
	output += '</div>';
	return output;
}

//convert the type of a request/result to a more verbose description
function verboseType(type) {
	var out = '';
	if(type == "friend_accepted") {
		out = " is now friends with you.";
	} else if (type == "friend_request") {
		out = " has requested to be friends with you.";
	} else if (type == "battle_request") {
		out = " has requested to battle with you.";
	} else if (type == "battle_results") {
		out = " had a battle with you.";
	} else if (type == "buy_result") {
		out = " bought your monster.";
	} else if (type == "breed_result") {
		out = " bred with your monster.";
	}

	return out;
}

//generic function to write out a server response
function writeServerResponse(response) {
	$('#server_response').html(response);
}