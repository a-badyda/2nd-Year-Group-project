/*
Auction.js
----------------
Javascript to deal with both battling other monsters and
breeding with other monsters.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

$(document).read(function(){
	//get a list of the users monsters
	$.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
		var obj =  $.parseJSON(response);
		var outputStr = outputList(obj.Monsters, buildMonsterAuctionHTML, true);
		$("#select_monster_form").html(outputStr);
	});
	
	//get a list of the friends and there monsters
	$.post(SERVLET_LOCATION, {action: "getFriends"}, function(response) {
		var obj =  $.parseJSON(response);
		var output = outputList(buildFriendHTML, obj.Friends);
		$("#friends_list").html(output);
	});

	//add checkbox enabling
	checkboxEvent('select_monster_sell');
	checkboxEvent('select_breed_sell');

	//enable the monster for breeding/selling
	function checkboxEvent(name) {
		$('input[type="checkbox"][name="'+name+'"]').on('change', function() {
			$(this).siblings('set_sell_cost').toggle();
			$(this).siblings('monster_sell_cost').toggle();
		});
	}

	$(".view_monster").on("click", function(){
		//show monsters click on
		var friend = getParentId(this, '.friend');

		$.post(SERVLET_LOCATION, {action: "getFriendsMonsters", friend_id: friend}, function(response) {
			//get an display the friends monsters
			var obj =  $.parseJSON(response);
			var outputStr = outputList(obj.Monsters, buildMonsterAuctionHTML, true);
			$('#friend_'+obj.friend_id+' .monster_list').html(outputStr);
		});
	});

	$("#friends_list .set_sell_cost").on("click", function() {
		var cost = obj.siblings('.monster_breed_cost').val();
		changeCost("Buy", this, cost);
	});

	$("#friends_list .set_breed_cost").on("click", function() {
		var cost = obj.siblings('.monster_sell_cost').val();
		changeCost("Breed", this, cost);
	});

	//change the cost for breeding/buying
	function changeCost(type, obj, cost){
		if (is_int(cost)) {
			var mon_id = getParentId(obj,'.monster');
			$.post(SERVLET_LOCATION, {action: "set" + type +"Cost", cost: new_cost, id: mon_id},
			function(response) {
				$('#change_response').html(response);
			});
		}
	}

	//handle clicking the breed request button
	$("#friends_list .breed_request").on("submit", function() {
		newBuyRequest(this);
		return false;
	});

	//handle clicking the buy request button
	$("#friends_list .buy_request").on("submit", function() {
		newBreedRequest(this);
		return false;
	});
	
	//function to send a breed request
	function newBreedRequest(obj){
		var friend_id = getParentId(obj,'.friend');
		var mon_id = getParentId(obj, '.friend_monster');
		var user_mon_id = $('input[name=radioName]:checked', '#select_monster_form').val();

		$.post(SERVLET_LOCATION, {action: "newBreedRequest", userMonsterId: user_mon_id, friendId: friend_id, monsterId: mon_id},
		function(response) {
			$('friend_'+friend_id+' monster_' +mon_id+'  .auction_result').html(response);
		});
	}

	//function to send a buy request
	function newBuyRequest(obj){
		var friend_id = getParentId(obj,'.friend');
		var mon_id = getParentId(obj, '.friend_monster');

		$.post(SERVLET_LOCATION, {action: "newBuyRequest", friendId: friend_id, monsterId: mon_id},
		function(response) {
			$('friend_'+friend_id+' monster_' +mon_id+'  .auction_result').html(response);
		});
	}

	//////////////////////////////////////////
	// HTML to show a monster
	//////////////////////////////////////////
	function buildMonsterAuctionHTML(mon, user) {
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

		//if user output this
		if(user) {
			//select monster for breeding
			outputStr += '<input type="radio" name="select_monster" class="select_monster" value="'+mon.id+'"></input>';

			//input form for setting selling
			outputStr += '<input type="checkbox" name="select_monster_sell" class="select_monster_sell" value="'+mon.id+'"></input>';
			outputStr += '<input type="text" name="sell_cost" class="monster_sell_cost" value="" disabled="true"></input>';
			outputStr += '<input type="button" name="set_sell_cost" class="set_sell_cost" value="Set Sell Cost" disabled="true"></input>';

			//input form for setting breeding
			outputStr += '<input type="checkbox" name="select_monster_breed" class="select_monster_breed" value="'+mon.id+'"></input>';
			outputStr += '<input type="text" name="breed_cost" class="monster_breed_cost" value="" disabled="true"></input>';
			outputStr += '<input type="button" name="set_breed_cost" class="set_breed_cost" value="Set Breed Cost" disabled="true"></input>';

		//else output this if friend
		} else {
			if(mon.avalible_buy) {
				outputStr += '<input type="submit" class="breed_request" value="Breed"></input>';
			}
			if(mon.avalible_breed) {
				outputStr += '<input type="submit" class="buy_request" value="Buy"></input>';
			}
		}

		outputStr += '<div class="auction_result"></div>';
		outputStr += "</div>";
		return outputStr;
	}
});