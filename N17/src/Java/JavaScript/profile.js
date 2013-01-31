/*
Profile.js
----------------
JavaScript for loading data into the profile page.
Gets user data and a list of monsters from the server
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk
*/

$(document).ready(function() {
	//request user data on page load.
	var $user_data = $.post(SERVLET_LOCATION, {action: "getUserData"}, function(response) {
		var obj = $.parseJSON(response);
		$('#username').append(obj.username);
		$('#cash').append(obj.cash);
	});

	//once user data is loaded, load users monsters, add them to the DOM
	$user_data.done(function() {
		$.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
			var obj = $.parseJSON(response);
			var outputStr = '<table>';
			if(obj.Monsters.length != 0) {
				outputStr += '<tr><th>Monster Name</th><th>Strength</th><th>Aggression</th><th>Defense</th><th>Health</th><th>Fertility</th><th>Change Name</th></tr>';
			}
			outputStr += '<tr>' + outputList(buildProfileHTML, obj.Monsters, true) + '</tr>';
			outputStr += '</table>';
			$('.monster_list').append(outputStr);
			addChangeNameEvents();
		});
	});

	//add event handlers for clicking to change the name of a monster.
	function addChangeNameEvents() {
		$('.change_name_button').on('click', function() {
			var ID = $(this).attr('id');
			var name =  $(this).siblings('.change_name').val();
			$.post(SERVLET_LOCATION, {action: "changeName", monster_id: ID, new_name: name}, function(response) {
				$(".monster_name[id='"+ID+"']").html(name);
			});
		});
	}

	//build a list of the users monsters in HTML
	function buildProfileHTML(key, mon, user) {
		outputStr = '<tr>';
		outputStr += buildMonsterHTML(key, mon, user);
		outputStr += '<td><input type="text" id="'+mon.ID+'" class="change_name" value="" ></input>';
		outputStr += '<input type="button" id="'+mon.ID+'" class="change_name_button" value="Change Name"></input></td>';
		outputStr += '</tr>';
		return outputStr;
	}
});
