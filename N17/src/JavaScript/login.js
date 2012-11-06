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
		var email = $('#home_login #username').val();
		var password = $('#home_login #password').val();
		var $errorBox = $('#home_login .error');
		
		if(!validateEmail(email)) {
			$errorBox.text("Invalid email address!");
		} else if (!validatePassword(password)) {
			$errorBox.text("Invalid password size!");
		} else {
			//send data to server
			$.post(USER_LOGIN, {username: email, password: password}, 
			function(response) {
				$errorBox.text(response);
			});
		}
		
		return false;
	});
	
	$("#home_create_user").submit(function() {
		var email = $('#home_create_user #new_username').val();
		var password = $('#home_create_user #new_password').val();
		var password_confirm = $('#home_create_user #confirm').val();
		var $errorBox = $('#home_create_user .error');
		
		if(!validateEmail(email)) {
			$errorBox.text("Invalid email address!");
		} else if (!validatePassword(password)) {
			$errorBox.text("Invalid password size!");
		} else if (password != password_confirm) {
			$errorBox.text("Passwords do not match!");
		} else {
			$.post(USER_REGISTER, {username: email, password: password}, 
			function(response) {
				$errorBox.text(response);
			});
		}
		return false;
	});
});
