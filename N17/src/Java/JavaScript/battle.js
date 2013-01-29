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
		var outputStr = outputList(buildMonsterSelectHTML, obj.Monsters, true);
		$("#select_monster_form").html(outputStr);
	});
	
	$monsters.done(function() {
		//get a list of the friends and there monsters
		$.post(SERVLET_LOCATION, {action: "getFriends"}, function(response) {
			var obj =  $.parseJSON(response);
			var output = outputList(buildFriendHTML, obj.Friends);
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
				var outputStr = outputList(buildMonsterSelectHTML, obj.Monsters, true);
				$('#friend_'+obj.friend_id+' .monster_list').html(outputStr);
				addBattleRequestEvents();
			});
		});
	}

	//handle clicking the battle request button
	function addBattleRequestsEvents() {
		$("#friends_list .battle_request").on("submit", function() {
			newMonsterRequest("Battle", this);
			return false;
		});
	}
	
	//function to send a battle/breed request
	function newMonsterRequest(type, obj){
		var friend_id = getParentId(obj,'.friend');
		var mon_id = getParentId(obj, '.friend_monster');
		var user_mon_id = $('input[name=radioName]:checked', '#select_monster_form').val();

		$.post(SERVLET_LOCATION, {action: "new" + type +"Request", userMonsterId: user_mon_id, friendId: friend_id, monsterId: mon_id},
		function(response) {
			$('#response').val(response);
		});
	}

	function buildMonsterSelectHTML(key, mon) {
		outputStr += buildMonsterHTML(key, mon);
		
		if(user) {
			outputStr += '<input type="radio" name="select_monster" class="select_monster" value="'+mon.id+'"></input>';
		} else {
			outputStr += '<input type="submit" class="battle_request" value="battle"></input>';
		}

		outputStr +='</div>';
		return outputStr;
	}
});
