
// update user info in status bar
initBar();

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

		if (page.name === 'edit_exercise') {
			var exercise_id = page.query.exercise_id;
			if (exercise_id == -1) {
				$('.pagetitle').html('新增试题');
			} else {
				$('.pagetitle').html('编辑试题');
			}
		}
	}
});

// handler for all button
$$(document).on(
		'click',
		function(e) {
			// check for logging in
			var element = e.srcElement;
			if (element.id === 'log-in-button') {
				// log in
				login($$("#username").val(), $$("#password").val(), $$(
						'#userType').val());
				if (userId > 0) {
					myApp.closeModal('.login-screen');
				}
			}

			// check for registering
			if (element.id === 'register-button') {
				register();
			}

			if (element.id === 'logout') {
				// log out
				userId = -1;
				storeUserIdentification(null, userId, "");
				initBar();
			}

			if (element.id === 'open-popup-edit-quesition') {
				//edit quesition
				initEditQuestion(element.title);
			}

			if (element.id === 'save-quesition') {
				// save quesition
				saveQuestion();
			}
		});

// if logged, close the log in modal
$('#login-screen').on('open', function() {
	if (userId >= 0) {
		myApp.closeModal('.login-screen');
	}
});

// if register open, close log in
$('.popup-register').on('open', function() {
	myApp.closeModal('.login-screen');
});

// switch input content between teacher and student in register page
$('#type').on('change', function() {
	if ($('#type').val() == 'student') {
		$('.stu_info').show();
		$('.tea_info').hide();
	} else {
		$('.stu_info').hide();
		$('.tea_info').show();
	}
});




