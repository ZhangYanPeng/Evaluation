// Initialize your app
var myApp = new Framework7();

// Export selectors engine
var $$ = Dom7;

//cache user's identification
var userId; //equals -1 before logging
var user;
var userType;
//check if logged before
getUserIdentification();
//base url
var baseUrl=getRootPath();

// Add view
var leftView = myApp.addView('.view-left', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});
//update user info in status bar
initBar();


//handler for all initiations fo pages
$$(document).on('pageInit', function (e) {
	var page = e.detail.page;
	initBar();
	
	//check for logging in except registering
	if( userId < 0 && page.name != 'register'){
		myApp.loginScreen();
	}
	else{
		//prevent from the logging modal
		myApp.closeModal($('#login-screen'));
	    
	    // Code for index page
	    if (page.name === 'index') {

	    }
	    
	    //register
	    if (page.name === 'register') {
	        
	    }
	}
});

//handler for all button
$$(document).on('click', function (e) {
	//check for logging in
	var element = e.srcElement;
	if (element.id === 'log-in-button'){
		//log in
		login($$("#username").val(),$$("#password").val(),$$('#userType').val());
		if( userId > 0 ){
			myApp.closeModal('.login-screen');
		}
	}
	
	//check for registering
	if (element.id === 'register-button'){
		//log in
		register();
		login("zhang","123","student");
		mainView.router.loadPage("index.html");
	}
	
	if (element.id === 'logout'){
		//log in
		userId=-1;
		storeUserIdentification(null,userId,"");
		console.log("logout");
		initBar();
	}
});


//if logged, close the log in modal
$('#login-screen').on('open',function(){
	if(userId >=0){
		myApp.closeModal($('#login-screen'));
	}
});



//register
function register(){
	
}

//for student logging in
function login(username,password,type){
	var url = baseUrl;
	if( type === "student"){
		url += "student/login";
	}else{
		url += "teacher/login";
	}
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
        url: url,
        data: {username:username, password:password},
        dataType: "json",
        error: function(e){
        	console.log(e);
        	myApp.alert("登陆失败，请重试");
        },
        success: function(data){
        	if(data.id>=0){
        		storeUserIdentification(data,data.id,type)
        		userId = data.id;
        		user = data;
        		userType = type;
        		initBar();
        	}else{
        		myApp.alert("用户名或密码错误，请检查后重试");
        	}
        }
    });
}

//local storage the identification
function storeUserIdentification(userinfo,id,type){
	var storage = window.localStorage;
	var identification = JSON.stringify(userinfo);
	storage["userId"] = id;
	storage["identification"] = identification;
	storage["userType"] = type;
}

//get the identification from local storage or set it null
function getUserIdentification(){
	var storage = window.localStorage;
	userId = storage["userId"];
	if(userId != null){
		user = null;
		if( userId >= 0 ){
			user = JSON.parse(storage["identification"]);
			userType = storage["userType"];
		}
	}else{
		userId=-1;
		storage["userId"] = userId;
		user = null;
	}
}

function initBar() {
	var barString = "你好,";
	if( userId <0 ){
		barString += "<a href='index.html'>请先登录</a>";
	}else{
		barString += "<a href='#' >"+
			user.name +
			"</a>";
	}
	$$('#bar_right').html(barString);
	
	//left view init
	if(userId <0){
		$$('#user_name').html("你好,<a href='index.html'>请先登录</a>");
	}
	else{
		$$('#user_name').text("你好！    "+user.name);
		var tmpstr = "身份：";
		if(userType === "student"){
			tmpstr += "学生";
			$$("#stu_func_list").show(); 
			$$("#tea_func_list").hide();
		}
		else{
			tmpstr += "教师";
			$$("#tea_func_list").hide(); 
			$$("#stu_func_list").show(); 
		}
		$$('#user_type').text(tmpstr);
	}
}

function getRootPath() {
    //获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
    var curWwwPath = window.document.location.href;
    //获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
    var pathName = window.document.location.pathname;
    var pos = curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8080
    var localhostPath = curWwwPath.substring(0, pos);
    //获取带"/"的项目名，如：/ems
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return(localhostPath + projectName+"/");
}