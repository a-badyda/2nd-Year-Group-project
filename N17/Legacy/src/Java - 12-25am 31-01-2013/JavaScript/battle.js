/*
Battle.js
----------------
Javascript to deal with both battling other monsters and
breeding with other monsters.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

$(document).ready(function() {
	//get a list of the users monsters
	$monsters = $.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
		var obj =  $.parseJSON(response);
		var outputStr = '<form><table>';
		if(obj.Monsters.length > 0) {
			outputStr += '<tr><th>Monster Name</th><th>Strength</th><th>Aggression</th><th>Defense</th><th>Health</th><th>Fertility</th><th>Selected</th></tr>';
		}
		outputStr += outputList(buildMonsterSelectHTML, obj.Monsters, true);
		outputStr += '</table></form>';
		
		$("#select_monster_form").html(outputStr);
		$(".select_monster").first().attr('checked',true);
	});
	
	$monsters.done(function() {
		//get a list of the friends and there monsters
		$.post(SERVLET_LOCATION, {action: "getFriends"}, function(response) {
			var obj =  $.parseJSON(response);
			output = outputList(buildFriendHTML, obj.Friends);
			$("#friends_list").html(output);
			addMonsterViewerEvents();
		});
	});

	function addMonsterViewerEvents() {
		$(".view_monster").on("click", function(e){
			//show monsters click on
			var friend = getParentId(this, '.friend');

			$.post(SERVLET_LOCATION, {action: "getFriendsMonsters", friend_id: friend}, function(response) {
				//get an display the friends monsters
				var obj =  $.parseJSON(response);
				var outputStr = '<form><table>';
				if(obj.Monsters.length > 0) {
					outputStr += '<tr><th>Monster Name</th><th>Strength</th><th>Aggression</th><th>Defense</th><th>Health</th><th>Fertility</th><th>Selected</th></tr>';
				}
				outputStr += outputList(buildMonsterSelectHTML, obj.Monsters, false);
				outputStr += '</table></form>';
				$('#friend_'+obj.friend_id+' .monster_list').html(outputStr);
				addBattleRequestEvents();
			});
		});
	}

	//handle clicking the battle request button
	function addBattleRequestEvents() {
		$("#friends_list .battle_request").on("click", function() {
			newMonsterRequest("Battle", this);
		});
	}
	
	//function to send a battle/breed request
	function newMonsterRequest(type, obj){
		var friend_id = getParentId(obj,'.friend');
		var mon_id = $(obj).attr('id');
		mon_id = mon_id.replace(/[A-Za-z_$]/g, "");
		var user_mon_id = $('input[name=select_monster]:checked').val();

		$.post(SERVLET_LOCATION, {action: "new" + type +"Request", userMonsterId: user_mon_id, friendId: friend_id, monsterId: mon_id},
		function(response) {
	
		});
	}

	function buildMonsterSelectHTML(key, mon, user) {
		outputStr = '<tr id="monster_row_'+mon.ID+'">';
		outputStr += buildMonsterHTML(key, mon);
		
		if(user) {
			outputStr += '<td><input type="radio" name="select_monster" class="select_monster" value="'+mon.ID+'"></input></td>';
		} else {
			outputStr += '<td><input id="'+mon.ID+'" type="button" class="battle_request" value="battle"></input></td>';
		}

		return outputStr + '</tr>';
	}
});
