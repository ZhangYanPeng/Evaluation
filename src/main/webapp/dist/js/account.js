// check if logged before
getUserIdentification();

// register
function register() {
	if (validate_register_info() == 1) {
		myApp.alert("两次输入的密码不一致", "错误");
		return;
	}
	var url = baseUrl;
	var data_input = null;
	if ($('#type').val() == "student") {
		url += "student/register";
		data_input = {
			username : $('#r-username').val(),
			password : $('#r-password').val(),
			name : $('#name').val(),
			gender : $('#gender').val(),
			school : $('#school').val(),
			major : $('#major').val(),
			grade : $('#grade').val(),
			student_no : $('#student_no').val(),
			english_level : $('#english_level').val(),
			father_level : $('#father_level').val(),
			mother_level : $('#mother_level').val()
		};
	} else {
		url += "teacher/register";
		data_input = {
			username : $('#r-username').val(),
			password : $('#r-password').val(),
			name : $('#name').val(),
			gender : $('#gender').val(),
			school : $('#school').val(),
			major : $('#major').val(),
			title : $('#title').val()
		};
	}
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : url,
		data : data_input,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		error : function(e) {
			console.log(e);
			myApp.alert("注册失败，请重试", "抱歉");
		},
		success : function(data) {
			if (data.id >= 0) {
				storeUserIdentification(data, data.id, type)
				userId = data.id;
				user = data;
				userType = $('#type').val();
				myApp.closeModal('.login-screen');
				myApp.closeModal($('.popup-register'));
				initBar();
			} else {
				myApp.alert("注册失败，请检查后重试", "抱歉");
			}
		}
	});
}

function validate_register_info() {
	if ($('#repassword').val() != $('#r-password').val())
		return 1;
	return 0;
}

// for student logging in
function login(username, password, type) {
	var url = baseUrl;
	if (type === "student") {
		url += "student/login";
	} else {
		url += "teacher/login";
	}
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : url,
		data : {
			username : username,
			password : password
		},
		dataType : "json",
		error : function(e) {
			myApp.alert("登陆失败，请重试", "抱歉");
		},
		success : function(data) {
			if (data.id >= 0) {
				storeUserIdentification(data, data.id, type)
				userId = data.id;
				user = data;
				userType = type;
				if (userId > 0) {
					myApp.closeModal('.login-screen');
				}
				initBar();
			} else {
				myApp.alert("用户名或密码错误，请检查后重试", "抱歉");
			}
		}
	});
}

// local storage the identification
function storeUserIdentification(userinfo, id, type) {
	var storage = window.localStorage;
	var identification = JSON.stringify(userinfo);
	storage["userId"] = id;
	storage["identification"] = identification;
	storage["userType"] = type;
}

// get the identification from local storage or set it null
function getUserIdentification() {
	var storage = window.localStorage;
	userId = storage["userId"];
	if (userId != null) {
		user = null;
		if (userId >= 0) {
			user = JSON.parse(storage["identification"]);
			userType = storage["userType"];
			myApp.closeModal('.login-screen');
		}
	} else {
		userId = -1;
		storage["userId"] = userId;
		user = null;
	}
}

//init left view and top bar information
function initBar() {
	var barString = "你好,";
	if (userId < 0) {
		barString += "<a href='#' class='open-login-screen' >请先登录</a>";
	} else {
		barString += "<a href='#' >" + user.name + "</a>";
	}
	$$('#bar_right').html(barString);

	// left view init
	if (userId < 0) {
		$$('#user_name').html(
				"你好,<a href='#'  class='open-login-screen' >请先登录</a>");
		$$('#user_type').text("");
		$$("#stu_func_list").hide();
		$$("#tea_func_list").hide();
		$$("#personal_func").hide();
	} else {
		$$('#user_name').html("你好！    " + user.name);
		var tmpstr = "身份：";
		if (userType === "student") {
			tmpstr += "学生";
			$$("#personal_func").show();
			$$("#stu_func_list").show();
			$$("#tea_func_list").hide();
		} else {
			tmpstr += "教师";
			$$("#personal_func").show();
			$$("#tea_func_list").show();
			$$("#stu_func_list").hide();
		}
		$$('#user_type').html(tmpstr);
	}
}
