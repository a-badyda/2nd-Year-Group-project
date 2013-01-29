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
	var $user_data = $.post(SERVLET_LOCATION, {action: "getUserData"}, function(response) {
		var obj = $.parseJSON(response);
		$('#username').html(obj.username);
	});

	$user_data.done(function() {
		$.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
			var obj = $.parseJSON(response);
			var outputStr = outputList(buildProfileHTML, obj.Monsters, true);
			$('.monster_list').html(outputStr);
		});
	});


	function buildProfileHTML(key, mon) {
		outputStr = '';
		outputStr += '<div id="monster_'+mon.id+'" class="monster">';
		outputStr += '<p class="monster_name">Monster Name: '+mon.monstername+'</p>';
		outputStr += '<div id="stats">';
		outputStr += '<p class="strength">Strength: '+mon.strength+'</p>';
		outputStr += '<p class="aggression">Aggression: '+mon.aggression+'</p>';
		outputStr += '<p class="defense">Defence: '+mon.defense+'</p>';
		outputStr += '<p class="health">Health: '+mon.health+'</p>';
		outputStr += '<p class="fertility">Fertility: '+mon.fertility+'</p>';
		outputStr += '</div>';
		return outputStr;
	}
});
