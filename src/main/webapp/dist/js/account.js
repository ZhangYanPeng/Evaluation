// check if logged before
getUserIdentification();

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
			myApp.loginScreen();
		},
		success : function(data) {
			if (data.id >= 0) {
				storeUserIdentification(data, data.id, type)
				userId = data.id;
				user = data;
				userType = type;
				if (userId > 0) {
					myApp.closeModal();
					initBar();
					mainView.router.loadPage("welcome.html");
				}
			} else {
				myApp.alert("用户名或密码错误，请检查后重试", "抱歉");
				myApp.loginScreen();
			}
		}
	});
}

//logout
function logout(){
	storeUserIdentification("", "-1", "")
	userId = "-1";
	user = "";
	userType = "";
	mainView.router.loadPage("welcome.html");
	myApp.loginScreen();
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
			login(user.username, user.password, 'student');
		}
		else {
			userId = -1;
			storage["userId"] = userId;
			user = null;
			myApp.loginScreen();
		}
	} else {
		userId = -1;
		storage["userId"] = userId;
		user = null;
		myApp.loginScreen();
	}
}

//init left view and top bar information
function initBar() {
	var barString = "你好!";
	if (userId < 0) {
		barString += "<a href='#' class='open-login-screen' >请先登录</a>";
	} else {
		barString += user.name ;
		barString += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	$$('#bar_right').html(barString);

	// left view init
	if (userId < 0) {
		$$('#user_name').html("你好,<a href='#'  class='open-login-screen' >请先登录</a>");
		$$('#user_type').text("");
		$$("#stu_func_list").hide();
		$$("#tea_func_list").hide();
		$$("#personal_func").hide();
	} else {
		$$('#user_name').html("你好！    " + user.name);
		if(user.questionaire == null){
			myApp.popup('.popup-questionaire');
		}
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

function loadAccountInfo(){
	$("#name").val(user.name);
	$("#stu_no").val(user.student_no);
	$("#password").val(user.password);
	$("#repassword").val(user.password);
}

function saveinfo(){
	if($("#password").val()!=$("#repassword").val()){
		myApp.alert("两次密码不一致","错误");
		return;
	}
	var url = baseUrl;
	if (userType === "student") {
		url += "student/modifypassword";
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
			id : user.id,
			password : $("#password").val()
		},
		dataType : "json",
		error : function(e) {
			myApp.alert("登陆失败，请重试", "抱歉");
		},
		success : function(data) {
			if (data > 0) {
				login(user.username,  $("#password").val(), userType)
			}
		}
	});
}

function register(){
	var regdata = myApp.formToJSON("#register-form");
	console.log(regdata);
	if(regdata.r_password != regdata.r_repassword){
		myApp.alert("两次输入密码不一致！","提示");
		return;
	}else if(regdata.r_university == "" || regdata.r_university == null){
		myApp.alert("请选择学校！","提示");
		return;
	}else if(regdata.r_username == "" || regdata.r_username == null){
		myApp.alert("请输入学号！","提示");
		return;
	}else if(regdata.r_password == "" || regdata.r_password == null){
		myApp.alert("请输入密码！","提示");
		return;
	}else if(regdata.r_b_engclass == "" || regdata.r_b_engclass == null){
		myApp.alert("请输入班级号！","提示");
		return;
	}else if(regdata.r_name == "" || regdata.r_name == null){
		myApp.alert("请输入真实姓名！","提示");
		return;
	}
	//初始化学校
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		contentType: "application/x-www-form-urlencoded; charset=utf-8",
		url : baseUrl + "student/register",
		data : regdata,
		dataType : "json",
		error : function(e) {
		},
		success : function(data) {
			if(data.student_no == "-1"){
				myApp.alert("该用户已经注册过！","提示");
			}else{
				login(data.username, data.password, 'student');
			}
		}
	});
}
