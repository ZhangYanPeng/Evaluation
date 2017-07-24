// Initialize your app
var myApp = new Framework7();

// Export selectors engine
var $$ = Dom7;

//cache user's identification
var userId; //equals -1 before logging
var user;
//check if logged before
getUserIdentification();
//base url
var baseUrl=getRootPath();

// Add view
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});


//handler for all initiations fo pages
$$(document).on('pageInit', function (e) {
	getUserIdentification();
	var page = e.detail.page;
	
	//check for logging in except registering
	if( userId < 0 && page.name != 'register'){
		console.log("log in");
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
		login($$("#username").val(),$$("#password").val());
		if( userId > 0 ){
			myApp.closeModal('.login-screen');
		}
	}
});


//if logged, close the log in modal
$('#login-screen').on('open',function(){
	if(userId >=0){
		myApp.closeModal($('#login-screen'));
	}
});

//for student logging in
function login(username,password){
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
        url: baseUrl+"login",
        data: {username:username, password},
        dataType: "json",
        error: function(e){
        	console.log(e);
        	myApp.alert("登陆失败，请重试");
        },
        success: function(data){
        	if(data.id>=0){
        		storeUserIdentification(data,data.id)
        		userId=data.id;
        		user = data;
        	}else{
        		myApp.alert("用户名或密码错误，请检查后重试");
        	}
        }
    });
}

//local storage the identification
function storeUserIdentification(userinfo,id){
	var storage = window.localStorage;
	var identification = JSON.stringify(userinfo);
	console.log(identification);
	if( id >=0 ){
		storage["userId"] = id;
		storage["identification"] = identification;
	}
}

//get the identification from local storage or set it null
function getUserIdentification(){
	var storage = window.localStorage;
	userId = storage["userId"];
	if(userId != null){
		var user = null;
		if( userId >= 0 ){
			user = storage["userId"];
		}
		return user;
	}else{
		userId=-1;
		storage["userId"] = userId;
		user = null;
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