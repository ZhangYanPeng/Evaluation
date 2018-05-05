function Q3(){
	if($$("#q3").val() == 2){
		$$("#q4-content").show();
		$$("#q5-content").show();
	}else{
		$$("#q4-content").hide();
		$$("#q5-content").hide();
	}
}

function QueSubmit(){
	if($$("#q1").val() == "" ||
		$$("#q2").val() == 0 ||
		$$("#q3").val() == 0 ||
		$$("#q3").val() == 2 && $$("#q4").val() == "" ||
		$$("#q3").val() == 2 && $$("#q5").val() == "" ||
		$$("#q6").val() == 0 ||
		$$("#q7").val() == "" ||
		$$("#q8").val() == 0 )
	{
		myApp.alert("请回答全部问题","提示");
		return;
	}

	var questionaire = $$("#q1").val() + "||" +
				$$("#q2").val() + "||" +
				$$("#q3").val() + "||" +
				$$("#q4").val() + "||" +
				$$("#q5").val() + "||" +
				$$("#q6").val() + "||" +
				$$("#q7").val() + "||" +
				$$("#q8").val();

	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : baseUrl + "student/QueSubmit",
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : {
			userId : userId,
			questionaire : questionaire
		},
		dataType : "json",
		error : function(e) {
			myApp.alert("提交失败，请重试", "抱歉");
		},
		success : function() {
			user.questionaire = questionaire;
			myApp.closeModal('.popup-questionaire');
		}
	});
}


function RateSubmit(){
	var questionaire = new Array();
	$$("input[name='rates']").each(function(i, el) {
		questionaire[i] =$(this).val();
	});

	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		traditional : true,
		url : baseUrl + "test/RateSubmit",
		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
		data : {
			ansId : $$("#test_id").val(),
			ques : JSON.stringify(questionaire)
		},
		dataType : "json",
		error : function(e) {
			myApp.alert("提交失败，请重试", "抱歉");
		},
		success : function() {
			myApp.closeModal('.popup-rate');
		}
	});
}