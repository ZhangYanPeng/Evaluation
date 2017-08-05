

// handler for all initiations fo pages
$$(document).on('pageInit', function(e) {
	var page = e.detail.page;

	// check for logging in except registering
	if (userId < 0 && page.name != 'register') {
		myApp.loginScreen();
	} else {
		// prevent from the logging modal
		myApp.closeModal($('#login-screen'));

		// Code for index page
		if (page.name === 'index') {
		}
	}
});

// handler for all button
$$(document).on('click', function(e) {
	// check for logging in
	var element = e.srcElement;
	if (element.id === 'log-in-button') {
		// log in
		login($$("#username").val(), $$("#password").val());
	}

	if (element.id === 'logout') {
		// log out
		adminId = -1;
		storeAdmin(null, adminId);
	}

});

// if logged, close the log in modal
$('#login-screen').on('open', function() {
	if (adminId >= 0) {
		myApp.closeModal('.login-screen');
	}
});



