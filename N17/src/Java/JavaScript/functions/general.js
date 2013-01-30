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
function outputList(func, list, args) {
	var output = '';
	if(list.length != 0) {
		$.each(list, function(key, val) {
			output += func(key, val, args);
		});
	} else {
		output = '<h4>Oops! Looks like there\'s nothing here!</h4>';
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
function buildMonsterHTML(key, mon) {
	outputStr = '';
	outputStr += '<div id="monster_'+mon.ID+'" class="monster">';
	outputStr += '<p class="monster_name">'+mon.monstername+'</p>';
	outputStr += '<div id="stats">';
	outputStr += '<p class="strength">'+mon.strength+'</p>';
	outputStr += '<p class="aggression">'+mon.aggression+'</p>';
	outputStr += '<p class="defense">'+mon.defense+'</p>';
	outputStr += '<p class="health">'+mon.health+'</p>';
	outputStr += '<p class="fertility">'+mon.fertility+'</p>';
	outputStr += '</div></div>';
	return outputStr;
}

function buildFriendHTML(key, friend){
	output = '';
	output += '<div id="friend_'+friend.id+'" class="friend">';
	output += '<p class="friend_name">'+friend.username+'</p>';
	output += '<a class="view_monster" href="#">View Monsters</a>';
	output += '<div class="monster_list"></div>';
	output += '</div>';
	return output;
}

//convert the type of a request/result to a more verbose description
function verboseType(type) {
	var out = '';
	if(type == "friend_accepted") {
		out = " accepted your friend request.";
	} else if (type == "friend_request") {
		out = " has requested to be friends with you.";
	} else if (type == "battle_request") {
		out = " has requested to battle with you.";
	} else if (type == "battle_results") {
		out = " accepted your battle request.";
	} else if (type == "buy_result") {
		out = " bought your monster.";
	} else if (type == "breed_result") {
		out = " bred with your monster.";
	}

	return out;
}