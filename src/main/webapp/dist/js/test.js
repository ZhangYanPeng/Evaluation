function loadTest(type){
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : baseUrl + "test/getAllTest",
		data : {},
		dataType : "json",
		error : function(e) {
		},
		success : function(data) {
			console.log(data);
			$$("#test_ul").html("");
			$.each(data, function(index,value){     
				console.log(type + "--" +value.id + "--" +userId);
				$$.ajax({
					async : false,
					cache : false,
					type : 'POST',
					crossDomain : true,
					url : baseUrl + "test/getStatus",
					data : {
						type : type,
						tid : value.id,
						uid : userId
					},
					dataType : "json",
					error : function(e) {
						console.log(e);
						alert(122);
					},
					success : function(data) {
						var li = $("<li></li>").attr("class","item-content");
						var d_icon = $("<div></div>").attr("class","item-media");
						var d_in = $("<div></div>").attr("class","item-inner");
						var d_title = $("<div></div>").attr("class","item-title").append(value.title);
						var d_after = $("<div></div>").attr("class","item-after");
						if(data == 1){
							var icon = $("<i></i>").attr("class","fa fa-check");
							d_icon.append(icon);
							d_after.append("已完成");
						}else{
							var icon = $("<i></i>").attr("class","fa fa-close");
							d_icon.append(icon);
							d_after.append("");
						}
						d_in.append(d_title).append(d_after);
						li.append(d_icon).append(d_in);
						$("#test_ul").append(li);
					}
				});
			 }); 
		}
	});
}