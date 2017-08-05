var adminId;
var admin;

getAdmin();

function login(username,password) {
	var url = baseUrl + "admin/login";
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
				adminId = data.id;
				admin = data;
				storeAdmin(admin,adminId);
				if (adminId > 0) {
					myApp.closeModal('.login-screen');
				}
			} else {
				myApp.alert("用户名或密码错误，请检查后重试", "抱歉");
			}
		}
	});
}


// local storage the identification
function storeAdmin(admininfo, adminId) {
	var storage = window.localStorage;
	var adminstr = JSON.stringify(admininfo);
	storage["adminId"] = adminId;
	storage["admin"] = adminstr;
}

// get the identification from local storage or set it null
function getAdmin() {
	var storage = window.localStorage;
	adminId = storage["adminId"];
	if (adminId != null) {
		admin = null;
		if (adminId >= 0) {
			admin = JSON.parse(storage["admin"]);
			myApp.closeModal('.login-screen');
		}
	} else {
		adminId = -1;
		storage["adminId"] = adminId;
		admin = null;
	}
}