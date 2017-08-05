// Initialize your app
var myApp = new Framework7();

// Export selectors engine
var $$ = Dom7;

var baseUrl = getRootPath();

var mainView = myApp.addView('.view-main', {
	// Because we use fixed-through navbar we can enable dynamic navbar
	dynamicNavbar : true
});


function getRootPath() {
	// 获取当前网址，如： http://localhost:8080/ems/Pages/Basic/Person.jsp
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： /ems/Pages/Basic/Person.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8080
	var localhostPath = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/ems
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	return (localhostPath + projectName + "/");
}