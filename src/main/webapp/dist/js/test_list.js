function toTestList(type) {
	if (type == 0) {
		mainView.router.loadPage("test_list.html");
	} else {
		mainView.router.loadPage("test_i_list.html");
	}
}

function loadTest(type, tul) {
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
			$.each(data, function(index, value) {
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
					},
					success : function(data) {
						var li = $("<li></li>").attr("class",
							"item-content");
						var d_icon = $("<div></div>").attr("class",
							"item-media");
						var d_in = $("<div></div>").attr("class",
							"item-inner");
						var d_title = $("<div></div>").attr("class",
							"item-title").append(value.title);
						var d_after = $("<div></div>").attr("class",
							"item-after");
						if (data == 1) {
							var icon = $("<i></i>").attr("class",
								"fa fa-check");
							d_icon.append(icon);
							d_after.append("已完成");
						} else {
							var icon = $("<i></i>").attr("class",
								"fa fa-close");
							d_icon.append(icon);
							var btn = $("<a></a>").attr('class',
								'button').append('开始测试');
							btn.attr('href', 'test.html?id=' + value.id
								+ "&type=" + type);
							d_after.append(btn);
						}
						d_in.append(d_title).append(d_after);
						li.append(d_icon).append(d_in);
						$(tul).append(li);
					}
				});
			});
		}
	});
}

function initTest(tid, type) {
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : baseUrl + "test/loadTest",
		data : {
			id : tid
		},
		dataType : "json",
		error : function(e) {
			console.log(e);
		},
		success : function(data) {
			if (data.id == -1) {
				alert("没有可用测试！");
				return;
			}
			var tstid = data.id;
			$("#test_title").append(data.title);
			$("test_description").append(data.description);
			$$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				crossDomain : true,
				url : baseUrl + "test/getStatus",
				data : {
					type : 1,
					tid : tstid,
					uid : userId
				},
				dataType : "json",
				error : function(e) {
				},
				success : function(data) {
					if (data == 1) {
						if (data == 1) {
							$("#op").append("您已经完成本测试评估");
						} else {
							var aop = $("<a></a>").attr('class',
								'button button-big button-fill');
							aop.append('开始试听干预诊断').attr("style","background: rgb(17,93,141);");
							aop.attr('href', 'load_test.html?tid='
								+ tstid + '&type=' + 1);
							$("#op").append(aop);
						}
					} else {
						var aop = $("<a></a>").attr('class',
							'button button-big button-fill').attr("style","background: rgb(17,93,141);");
						aop.append('开始试听诊断');
						aop.attr('href', 'load_test.html?tid=' + tstid
							+ '&type=' + 1);
						$("#op").append(aop);
					}
				}
			});
		}
	});
}

function loadAnswers(type) {
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : baseUrl + "test/getAnswers",
		data : {
			type : type,
			uid : userId
		},
		dataType : "json",
		error : function(e) {
		},
		success : function(data) {
			$.each(data, function(index, value) {
				var test = value.test;
				var li = $("<li></li>").attr("class", "item-content");
				var d_icon = $("<div></div>").attr("class", "item-media");
				var d_in = $("<div></div>").attr("class", "item-inner");
				var d_title = $("<div></div>").attr("class", "item-title").append(test.title);
				var d_after = $("<div></div>").attr("class", "item-after");
				var icon = $("<i></i>").attr("class", "fa fa-check");
				d_icon.append(icon);
				var btn = $("<a></a>").attr('class', 'button').append('查看报告');
				//btn.attr('href', 'result_page/result_type.html?tid=' + test.id + "&type="+ type);
				btn.attr('href','javascript:getSingleReport('+test.id+');');
				d_after.append(btn);
				d_in.append(d_title).append(d_after);
				li.append(d_icon).append(d_in);
				$('#test_rlist').append(li);
			});
		}
	});
}
