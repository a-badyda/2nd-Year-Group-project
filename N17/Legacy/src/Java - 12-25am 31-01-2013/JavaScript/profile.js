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
			var outputStr = '<table><tr><th>Monster Name</th><th>Strength</th><th>Aggression</th><th>Defense</th><th>Health</th><th>Fertility</th><th>Change Name</th></tr>';
			outputStr += outputList(buildProfileHTML, obj.Monsters, true);
			$('.monster_list').html(outputStr);
			addChangeNameEvents();
		});
	});

	function addChangeNameEvents() {
		$('.change_name_button').on('click', function() {
			var ID = $(this).attr('id');
			var name =  $(this).siblings('.change_name').val();
			$.post(SERVLET_LOCATION, {action: "changeName", monster_id: ID, new_name: name}, function(response) {
				$("td[id='"+ID+"'][class='monster_name']").html(name);
			});
		});
	}


	function buildProfileHTML(key, mon, user) {
		outputStr = '<tr>';
		outputStr += buildMonsterHTML(key, mon, user);
		outputStr += '<td><input type="text" id="'+mon.ID+'" class="change_name" value="" ></input>';
		outputStr += '<input type="button" id="'+mon.ID+'" class="change_name_button" value="Change Name"></input></td>';
		outputStr += '</tr>';
		return outputStr;
	}
});
