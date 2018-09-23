function gettimestr(time){
	var timestr = "";
	timestr = time.getFullYear()+"-"+time.getMonth()+"-"+time.getDate();
	timestr = timestr + " " + time.getHours() +":" + time.getMinutes() + ":" + time.getSeconds();
	return timestr;
}

function finishTest(){
	test_end_time = new Date();
	$.each($("#audios").children(),function(index,value){
		value.pause();
	});
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		traditional: true,
		url : baseUrl + "test/finishTest",
		data : {
			tid : c_test.id,
			uid : userId,
			type : c_type,
			records : records,
			reasons : reasons,
			start_time: gettimestr(test_start_time),
			end_time: gettimestr(test_end_time)
		},
		dataType : "json",
		error : function(e) {
			console.log(e);
		},
		success : function(data){
			mainView.router.loadPage("welcome.html");
		}
	});
}

var test_report_id;

function set_type(tid){
	test_report_id = tid;
	myApp.popover('.popover-test-type', $$('#'+'tr'+tid));
}

function test_report(){
	myApp.closeModal('.popover-test-type');
	mainView.router.loadPage("result_page/test_report.html");
}

function evaluation_report(){
	
}

function calculate_result(tid, type){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		traditional: true,
		url : baseUrl + "test/testResult",
		data : {
			tid : tid,
			uid : userId,
			type : type
		},
		dataType : "json",
		error : function(e) {
			console.log(e);
		},
		success : function(data){
			var t_score=0;
			if(data.questionaire == null){
				$$("#test_id").val(data.id);
				myApp.popup('.popup-rate');
			}
			for (var ri =0; ri <data.records.length; ri++){
				$.each(data.records, function(index, value){
					if(ri == value.no){
						if(type == 0){
							var div_card_header = $("<div></div>").attr("class",'card-header').append(ri+1);
							var re = "正确答案：" + NumToAlph(value.question.answer);
							if(value.result.split("||").pop() == value.question.answer){
								re = re +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你回答正确!";
								t_score = t_score + 4;
							}else{
								re = re +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你的答案是:" + NumToAlph(value.result.split("||").pop());
							}
							var div_card_footer = $("<div></div>").attr("class",'card-footer').append(re);
							var div_card = $("<div></div>").attr("class",'card').append(div_card_header).append(div_card_footer);
							$("#test_result").append(div_card);
						}
						if(type == 1){
							var div_card_header = $("<div></div>").attr("class",'card-header').append(ri+1);
							var re = "正确答案：" + NumToAlph(value.question.answer);
							if(value.result.split("||").pop() == value.question.answer){
								re = re +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;你用了"+(value.result.split("||").length-1)+"次回答正确!";
								t_score = t_score + 6 - value.result.split("||").length ;
							}else{
								re = re +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;很遗憾你没能答对";
							}
							var div_card_footer = $("<div></div>").attr("class",'card-footer').append(re);
							var div_card = $("<div></div>").attr("class",'card').append(div_card_header).append(div_card_footer);
							$("#test_result").append(div_card);
						}
						return false;
					}
				});
			}
			$("#score").html("此次测试满分为："+data.records.length*4+"&nbsp;&nbsp;&nbsp;&nbsp;你评分为：" + t_score);
		}
	});
}

function NumToAlph(c){
	switch(c){
		case '1' : return 'A';break;
		case '2' : return 'B';break;
		case '3' : return 'C';break;
		case '4' : return 'D';break;
		case '5' : return 'E';break;
		case '0' : return '未选择';break;
		case 1 : return 'A';break;
		case 2 : return 'B';break;
		case 3 : return 'C';break;
		case 4 : return 'D';break;
		case 5 : return 'E';break;
		defalult : return '未选择';break;
	}
}
