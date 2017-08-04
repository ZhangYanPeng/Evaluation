//init edit quesition popup
function initEditQuestion(id) {
	if (id == '-1') {
		myApp.popup('.popup-edit-question');
	} else {
		console.log(id);
	}
}

//save question
function saveQuesition() {
	var url = baseUrl;
	var data_input = null;
	if ($$("#q_id").val() == "-1") {
		url += "teacher/add_quesition";
	} else {
		url += "teacher/edit_quesition";
	}
	data_input = {
		q_id : $('#r-q_id').val(),
		q_text : $('#q_text').val(),
		q_option : $('#q_option').val(),
		qi_text : $('#qi_text').val(),
		qi_audio : $('#qi_audio').val(),
		q_answer : $('#q_answer').val(),
	};
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : url,
		data : data_input,
		dataType : "json",
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		error : function(e) {
			myApp.alert("提交失败，请重试", "抱歉");
		},
		success : function(data) {
			
		}
	});
}