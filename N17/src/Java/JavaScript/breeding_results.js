$(document).read(function() {
	//get the battle_id
	$_GET = getGETvars();
	var id = $_GET['id'];

	//get the battle results from the server
	$.post(SERVLET_LOCATION, {action: "getBreedingResults"}, function(response) {
		var obj = $.parseJSON(response);

		//output result data here
		//try to use static rather than dynamic HTML.
	});
});