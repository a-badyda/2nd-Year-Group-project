/*
Login.js
----------------
Handle input from the login form.
Validates input for an existing user.
Also validates details for new users.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/


$(document).ready(function(){

	//Validate the existing user form and sent it to the server
	$("#home_login").submit(function() {
		var email = $('#home_login #username').val();
		var password = $('#home_login #password').val();
		var $errorBox = $('#home_login .error');
		
		if (validateUserDetails($errorBox, email, password)) {
			$.post(USER_LOGIN, {username: email, password: password,action:'login'}, 
			function(response) {
				$errorBox.text(response);
			});
		}
		
		return false;
	});
	
	//Validate the register new user form and send it to the server
	$("#home_create_user").submit(function() {
		var email = $('#home_create_user #new_username').val();
		var password = $('#home_create_user #new_password').val();
		var password_confirm = $('#home_create_user #confirm').val();
		var $errorBox = $('#home_create_user .error');
		
		if (validateUserDetails($errorBox, email, password, password_confirm)) {
			$.post(USER_REGISTER, {username: email, password: password,action:'newuser'}, 
			function(response) {
				$errorBox.text(response);
			});
		}
		return false;
	});
	
	//Output error messages if we have a problem with the details
	//otherwise return true.
	function validateUserDetails($errorBox, email, password, password_confirm) {
		var result = false;
		
		if(!validateEmail(email)) {
			$errorBox.text("Invalid email address!");
		} else if (!validatePassword(password)) {
			$errorBox.text("Invalid password size! Password must be between " + MIN_PASSWORD_LENGTH
			+ " and " + MAX_PASSWORD_LENGTH + ".");
		} else if (password_confirm != undefined && password != password_confirm) {
			$errorBox.text("Passwords do not match!");
		} else {
			result = true;
		}
		
		return result;
	}
});
