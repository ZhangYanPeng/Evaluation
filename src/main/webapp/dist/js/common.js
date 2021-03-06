
//cache user's identification
var userId; // equals -1 before logging
var user;
var userType;

var c_test;
var c_type;
var c_part;
var c_exercise;
var c_question;
var q_total;

var c_pno;
var c_eno;
var c_qno;
var c_pro;

var c_record="";
var records;
var reasons;
var timeconsume;
var reacttimeconsume;

var load_progress=0;

var test_start_time;
var test_end_time;

// base url
var baseUrl = getRootPath();
var severUrl = getSeverUrl();

//Initialize your app
var myApp = new Framework7(
	{
          modalCloseByOutside: false,
          actionsCloseByOutside: false,
          popupCloseByOutside: false
	});

// Export selectors engine
var $$ = Dom7;

// Add view
var leftView = myApp.addView('.view-left', {
	// Because we use fixed-through navbar we can enable dynamic navbar
	dynamicNavbar : true
});
var mainView = myApp.addView('.view-main', {
	// Because we use fixed-through navbar we can enable dynamic navbar
	dynamicNavbar : true
});

function getSeverUrl() {
	// 获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8080
	var localhostPath = curWwwPath.substring(0, pos);
	return localhostPath;
}

function getRootPath() {
	// 获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8080
	var localhostPath = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/ems
	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPath + projectName + "/");
}


//url parameter
function getQueryString(name) {
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
	var r = window.location.search.substr(1).match(reg);
	if (r != null)
		return unescape(r[2]);
	return null;
}