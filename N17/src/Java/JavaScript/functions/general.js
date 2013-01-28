function buildFriendHTML(friend){
	output = '';
	output += '<div id="friend_'+friend.id+'" class="friend">';
	output += '<p class="friend_name">'+friend.username+'</p>';
	output += '<a class="view_monster" href="#">View Monsters</a>';
	output += '<div class="monster_list"></div>';
	output += '</div>';
	return output;
}

//strip an ID off an selector
function getParentId(obj, selector){
	var parent = $(obj).parent(selector).id();
	var id = parent.substring(parent.length-2);
	return id;
}

function outputMonsters(obj, user, func) {
	var outputStr = ''; 
	if(obj.Monsters.length != 0) {
		$.each(obj.Monsters, function(key, mon) {
			outputStr += func(mon, user);
		});
	} else {
		outputStr += '<h4>Looks like you have no monsters!</h4>';
	}
	return outputStr;
}

function is_int(value){ 
  	return ((parseFloat(value) == parseInt(value)) && !isNaN(value)); 
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