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
	$.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
		var obj =  $.parseJSON(response);
		outputStr = '<div></div>';
		outputStr += outputMonsters(obj, true, buildMonsterHTML);
		$("#select_monster_form").html(outputStr);
	});
	
	//get a list of the friends and there monsters
	$.post(SERVLET_LOCATION, {action: "getFriends"}, function(response) {
		var obj =  $.parseJSON(response);
		var output = '';

		if(obj.Friends.length != 0) {
			$.each(obj.Friends, function(key, friend) {
				output += buildFriendHTML(friend);
			});
		} else {
			output += '<h4>Looks like you have no friends!</h4>';
		}

		$("#friends_list").html(output);
	});
	
	$(".view_monster").on("click", function(){
		//show monsters click on
		var friend = getParentId(this, '.friend');

		$.post(SERVLET_LOCATION, {action: "getFriendsMonsters", friend_id: friend}, function(response) {
			//get an display the friends monsters
			var obj =  $.parseJSON(response);
			outputStr = outputMonsters(obj, false, buildMonsterHTML);
			$('#friend_'+obj.friend_id+' .monster_list').html(outputStr);
		});
	});

	//handle clicking the battle request button
	$("#friends_list .battle_request").on("submit", function() {
		newMonsterRequest("Battle", this);
		return false;
	});
	
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

	function buildMonsterHTML(mon, user) {
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

		if(user) {
			outputStr += '<input type="radio" name="select_monster" class="select_monster" value="'+mon.id+'"></input>';
		} else {
			outputStr += '<input type="submit" class="battle_request" value="battle"></input>';
		}

		outputStr +='</div>';
		return outputStr;
	}

});
