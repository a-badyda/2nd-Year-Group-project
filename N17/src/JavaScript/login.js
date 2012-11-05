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
			
			if(response == "true"){
				document.location.href="http://www.monstermashgame.co.uk/TEST/profile.html"
			}else{
				$('#password').val('');
				$('#error').html('Incorrect Login Details');
			}
			
		});
		
		return false;
	});
});
