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