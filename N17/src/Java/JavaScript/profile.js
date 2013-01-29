/*
Profile.js
----------------
JavaScript for loading data into the profile page.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk
*/

$(document).ready(function() {
	//request data on page load.
	$.post(SERVLET_LOCATION, {action: "getUserData"}, function(response) {
		var obj = $.parseJSON(response);
		$('#username').html(obj.username);
	});

	$.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
		var obj = $.parseJSON(response);
		var outputStr = outputList(buildProfileHTML, obj.Monsters, true);
		$('.monster_list').html(outputStr);
	});

	function buildProfileHTML(key, mon) {
		outputStr = '';
		outputStr += '<div id="monster_'+mon.id+'" class="monster">';
		outputStr += '<p class="monster_name">'+mon.monstername+'</p>';
		outputStr += '<div id="stats">';
		outputStr += '<p class="strength">'+mon.strength+'</p>';
		outputStr += '<p class="aggression">'+mon.aggression+'</p>';
		outputStr += '<p class="defense">'+mon.defense+'</p>';
		outputStr += '<p class="health">'+mon.health+'</p>';
		outputStr += '<p class="fertility">'+mon.fertility+'</p>';
		outputStr += '</div>';
		return outputStr;
	}
});
