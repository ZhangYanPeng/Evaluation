var storage = window.localStorage;
var id = storage["id"];
if (id == null || id <0) {
	window.location.href ="./index";
}