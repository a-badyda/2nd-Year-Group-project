/*
Login.js
----------------
Handle input from the login form
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/


$(document).ready(function(){
	$("#home_login").submit(function() {

		$.post('login', { 'username' : $('#username').val(), 'password' : $('#password').val() }, function(response){
			
			alert("Done.");
		});
		
		return false;
	});
});
