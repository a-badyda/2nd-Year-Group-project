/*
Auth.js
----------------
Dealing with user authentication.
----------------
Author:	Samuel Jackson
Email:	slj11@aber.ac.uk	
*/

//function to login a user into the system.
function loginUser(email, password) {
	$.post(USER_LOGIN, {email: email, password: password}, 
	function (response) {
		//deal with correct/incorrect login here.
	});
}

//function to logout the user from the system.
function logoutUser(email) {
	$.post(USER_LOGOUT, {email: email},
	function(response) {
		//deal with logging the user out.
	});
}

//Register a new user with the system.
function registerUser(username, email, password, password_check) {
	$.post(USER_REGISTER, {username, email, password, password_check}, 
	function (response) {
		//user registered.
	});
}
