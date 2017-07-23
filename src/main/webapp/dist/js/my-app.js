// Initialize your app
var myApp = new Framework7();

// Export selectors engine
var $$ = Dom7;

//cache user's identification
var userId=-1;
var user=null;
//base url
var baseUrl="localhost:8080/xjtu/";

// Add view
var mainView = myApp.addView('.view-main', {
    // Because we use fixed-through navbar we can enable dynamic navbar
    dynamicNavbar: true
});

//Init all pages
$$(document).on('pageInit', function (e) {
	//check for logging in
	if( userId ===-1 ){
		console.log("log in");
		myApp.loginScreen();
	}
	else{
	
	    var page = e.detail.page;
	    // Code for About page
	    if (page.name === 'about') {
	        //
	    }
	}
});

$$(document).on('click', function (e) {
	//check for logging in
	var element = e.srcElement;
	if (element.id === 'log-in-button'){
		//log in
		$.ajax({
            type: "POST",
            url: baseUrl+"login",
            data: {username:$("#username").val(), content:$("#password").val()},
            dataType: "json",
            success: function(data){
            	if(data.id>=0){
            		user = data;
            	}else{
            		alert("登陆失败，请重试");
            	}
            },
            error:function(e){
            	console.log(e);
            	alert("登陆失败，请重试");
            }
        });
	}
});