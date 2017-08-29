function loadTest(){
	var url = baseUrl;
	url += "test/getAllTest";
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : url,
		data : {},
		dataType : "json",
		error : function(e) {
		},
		success : function(data) {
			if(data != null ){
				
			}
		}
	});
}