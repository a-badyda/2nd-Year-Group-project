/*
Auction.js
----------------
Javascript to deal with both battling other monsters and
breeding with other monsters.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

$(document).ready(function(){
	//get a list of the users monsters
	$monsters = $.post(SERVLET_LOCATION, {action: "getMonsters"}, function(response) {
		var obj =  $.parseJSON(response);
		var outputStr = '<form><table>';
		if(obj.Monsters.length > 0) {
			outputStr += '<tr><th>Monster Name</th><th>Strength</th><th>Aggression</th><th>Defense</th><th>Health</th><th>Fertility</th><th>Options</th></tr>';
		}
		outputStr += outputList(buildMonsterAuctionHTML, obj.Monsters, true);
		outputStr += '</table></form>';
		$("#select_monster_form").html(outputStr);

		//add checkbox enabling
		checkboxEvent('breed');
		checkboxEvent('sell');
		addSetCostEvents();
		$(".select_monster").first().attr('checked',true);
	});
	
	//when we have a list of user monsters, get a list of users friends
	$monsters.done(function() {
		//get a list of the friends and there monsters
		$.post(SERVLET_LOCATION, {action: "getFriends"}, function(response) {
			var obj =  $.parseJSON(response);
			var output = outputList(buildFriendHTML, obj.Friends);
			$("#friends_list").html(output);
			addMonsterViewerEvents();
		});
	});


	//add events for selecting to change a monsters breed/buy cost.
	function checkboxEvent(name) {
		$('input[type="checkbox"][name="select_monster_'+name+'"]').on('change', function() {
			var id = $(this).attr('id');
			$(this).siblings('.set_'+name+'_cost[id='+id+']').attr('disabled',!this.checked);
			$(this).siblings('.monster_'+name+'_cost[id='+id+']').attr('disabled',!this.checked);
		});
	}

	//add event handlers for viewing a list of friends monsters
	function addMonsterViewerEvents() {
		$(".view_monster").on("click", function(){
			//show monsters click on
			var friend = getParentId(this, '.friend');

			$.post(SERVLET_LOCATION, {action: "getFriendsMonsters", friend_id: friend}, function(response) {
				//get an display the friends monsters
				var obj =  $.parseJSON(response);
				var outputStr = outputList(buildMonsterAuctionHTML, obj.Monsters, false);
				$('#friend_'+obj.friend_id+' .monster_list').html(outputStr);
				addAuctionEvents();
			});
		});
	}
 
 	//add event handlers for setting the cost of buy/breeding a monster
	function addSetCostEvents() {
		//clicking to set the cost of selling a monster
		$(".set_sell_cost").on("click", function() {
			var cost = $(this).siblings('.monster_sell_cost').val();
			if (is_int(cost) && cost >=0) {
				changeCost("Buy", this, cost);
			} else {
				alert("Please enter a valid number.");
			}
		});

		//clicking to set the cost of breeding with a monster
		$(".set_breed_cost").on("click", function() {
			var cost = $(this).siblings('.monster_breed_cost').val();
			if (is_int(cost) && cost >=0) {
				changeCost("Breed", this, cost);
			} else {
				alert("Please enter a valid number.");
			}
		});
	}

	//change the cost for breeding/buying
	function changeCost(type, obj, new_cost){
		var mon_id = $(obj).attr('id');
		$.post(SERVLET_LOCATION, {action: "set" + type +"Cost", cost: new_cost, id: mon_id},
		function(response) {
			$('#change_response').html(response);
		});
	}

	//Add event handlers for clicking to buy or breed with a friends monster
	function addAuctionEvents() {
		//handle clicking the breed request button
		$("#friends_list .breed_request").on("click", function() {
			newBreedRequest(this);
			$(this).attr('disabled', true);
		});

		//handle clicking the buy request button
		$("#friends_list .buy_request").on("click", function() {
			newBuyRequest(this);
			$(this).attr('disabled', true);
		});
	}
	
	//function to send a breed request
	function newBreedRequest(obj){
		var friend_id = getParentId(obj,'.friend');
		var mon_id = $(obj).attr('id');
		var user_mon_id = $('input[name=select_monster]:checked').val();

		$.post(SERVLET_LOCATION, {action: "newBreedRequest", userMonsterId: user_mon_id, friendId: friend_id, monsterId: mon_id},
		function(response) {
			writeServerResponse(response);
		});
	}

	//function to send a buy request
	function newBuyRequest(obj){
		var friend_id = getParentId(obj,'.friend');
		var mon_id = $(obj).attr('id');

		$.post(SERVLET_LOCATION, {action: "newBuyRequest", friendId: friend_id, monsterId: mon_id},
		function(response) {
			writeServerResponse(response);
		});
	}

	//Convert lis of monsters to HTML for outputting
	function buildMonsterAuctionHTML(key, mon, user) {
		var outputStr = buildMonsterHTML(key, mon);

		//if user output this
		if(user == true) {
			//select monster for breeding
			outputStr += '<td><table><tr><td>Select For Breeding: <input type="radio" name="select_monster" class="select_monster" value="'+mon.ID+'"></input></td></tr>';

			//input form for setting selling
			outputStr += '<tr><td><input id="'+mon.ID+'" type="checkbox" name="select_monster_sell" class="select_monster_sell" value=""></input>';
			outputStr += '<input id="'+mon.ID+'" type="text" name="sell_cost" class="monster_sell_cost" value="'+mon.cost_buy+'" disabled="true"></input>';
			outputStr += '<input id="'+mon.ID+'" type="button" name="set_sell_cost" class="set_sell_cost" value="Set Sell Cost" disabled="true"></input></td></tr><br />';

			//input form for setting breeding
			outputStr += '<tr><td><input id="'+mon.ID+'" type="checkbox" name="select_monster_breed" class="select_monster_breed" value=""></input>';
			outputStr += '<input id="'+mon.ID+'" type="text" name="breed_cost" class="monster_breed_cost" value="'+mon.cost_breed+'" disabled="true"></input><br />';
			outputStr += '<input id="'+mon.ID+'" type="button" name="set_breed_cost" class="set_breed_cost" value="Set Breed Cost" disabled="true"></input></td></tr></table></td>';

		//else output this if friend
		} else {
			
			//Show breed button if monster is avalible for breeding
			if(mon.breed) {
				outputStr += '<td>Breeding Cost: '+mon.cost_breed+'<input id="'+mon.ID+'" type="button" class="breed_request" value="Breed"></input></td>';
			}

			//show buy button if monster is avalible for buying
			if(mon.buy) {
				outputStr += '<td>Buying Cost: '+mon.cost_buy+'<input id="'+mon.ID+'" type="button" class="buy_request" value="Buy"></input></td>';
			}
		}

		outputStr += '</tr><div class="auction_result"></div>';
		outputStr += "</div>";
		return outputStr;
	}
});