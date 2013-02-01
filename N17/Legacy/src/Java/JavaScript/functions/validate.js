/*
Validate.js
----------------
Utitlity functions used to validate user input from HMTL forms.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

//check that a given email is in a valid email address format
function validateEmail(email) {
	var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	return regex.test(email);
}

//Check that the new password is valid. Including whether it's between a specified length
function validatePassword(password) {
	if(password.length >= MIN_PASSWORD_LENGTH && password.length <= MAX_PASSWORD_LENGTH) {
		return true;
	}
	return false;
}

//check that the newly given passwords are valid.
function validateNewPassword(password, password_check) {
	if(password === password_check) {
		return validatePassword(password);
	}
	
	return false;
}


