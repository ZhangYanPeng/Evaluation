// update user info in status bar


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

		// Code for test page
		if (page.name === 'test_list') {
			loadTest(0,"#test_ul");
		}

		if (page.name === 'test_i_list') {
			loadTest(1,"#test_i_ul");
		}
		
		if (page.name === 'test') {
			initTest(page.query.id,page.query.type);
		}

		if (page.name === 'load_test') {
			c_type = page.query.type;
			startTest(page.query.tid);
		}
		
		if (page.name === 'test_content') {
			c_pno=0;
			c_eno=0;
			c_qno=0;
			c_pro=0;
			test_start_time = new Date();
			presentQuestion();
		}
		
		if (page.name === 'test-result') {
			calculate_result(page.query.tid, page.query.type);
		}
		
		if (page.name === 'test_rlist') {
			loadAnswers(page.query.type);
		}
		
		if( page.name === 'info' ){
			loadAccountInfo();
		}

		if( page.name === 'test_report' ){
			init_report(userId, page.query.tid);
		}

		if( page.name === 'evaluation_report' ){
			init_evaluation(userId, page.query.tid);
		}

		if( page.name === 'overall_report' ){
			init_overall(userId);
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

			if (element.id === 'logout') {
				// log out
				logout();
			}

			if (element.id === 'register-button') {
				// log out
				myApp.closeModal('.login-screen');
				initBar();

				//初始化学校
				$$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					crossDomain : true,
					url : baseUrl + "student/list_university",
					data : {
					},
					dataType : "json",
					error : function(e) {
					},
					success : function(data) {
						$$.each(data,function(index,value){
							var op = $$("<option></option>").attr("value",value.id).append(value.name);
							$$("#r_university").append(op);
						});
					}
				});
				myApp.popup('.register-popup'); 
			}

			if (element.id === 'register') {
				register();
			}
		});

// if logged, close the log in modal
$('#login-screen').on('open', function() {
	if (userId >= 0) {
		myApp.closeModal('.login-screen');
	}
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
