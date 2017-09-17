function startTest(tid){
	records = new Array();
	reasons = new Array();

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
		success : function(data){
			c_test = data;
			q_total=0;
			for(var ti=0; ti<c_test.parts.length; ti++){
				var part = c_test.parts[ti];
				for(var pi=0; pi<part.exercises.length; pi++){
					var exer = part.exercises[pi];
					q_total = q_total+exer.questions.length;
				}
			}
			presentQuestion(1);
		}
	});
}

function presentBaseTest(){
	$$("#part_list").html("");
	for( var i=1;i<=c_test.parts.length;i++){
		var a_part = $$("<a></a>").attr('class',"button").attr('id','part'+i).attr('href',"#");
		$$("#part_list").append(a_part);
	}
	for( var i=0;i<c_test.parts.length;i++){
		var p = c_test.parts[i];
		$$('#part'+p.p_no).append(p.exerciseType.description);
	}
	$$('#t_title').html(c_test.title);
	if(c_type==1){
		$$('#t_title').append("(intervention)");
	}
}


function playSound(audio_path){
	alert(audio_path);
}

function presentQuestion(qno){
	presentBaseTest();
	var fe;
	for(var ti=0; ti<c_test.parts.length; ti++){
		var part = c_test.parts[ti];
		for(var pi=0; pi<part.exercises.length; pi++){
			var exer = part.exercises[pi];
			for( var ei=0; ei<exer.questions.length; ei++){
				var ques = exer.questions[ei];
				if( ques.q_num == qno ){
					c_part = part;
					if(qno==1){
						fe=0;
					}else{
						fe = c_exercise.e_no;
					}
					c_exercise = exer;
					c_question = ques;
				}
			}
		}
	}

	if(fe != c_exercise.e_no){
		playSound(c_exercise.audio_path);
	}
	
	$$('#part'+c_part.p_no).attr("class","button active");
	$$('#progress').html(qno+"/"+q_total);
	myApp.setProgressbar($$('.progressbar'), qno*100/q_total);
	$$('#part_description').html(c_part.description);
	$$('#exercise_description').html(c_exercise.description);
	$$('#exercise_text').html(c_exercise.text);
	
	$$('#q_text').html(c_question.q_num+".");
	var options = c_question.options.split("||");
	$$('#q_op').html("");
	for(var i=1; i<options.length; i++){
		var p_op = $$("<p></p>");
		var i_op = $$("<input></input>").attr('type','radio').attr('name','answer').attr('value',i);
		p_op.append(i_op).append(options[i]);
		$$('#q_op').append(p_op);
	}
	
	if(c_type==1){
		$$('#intervention').html('<a href="#" data-panel="right" class="open-panel">查看干预</a>');
		$$('#inte_text').html("");
	}
}

function nextQuestion(){
	$$('#inte_text').html("");
	var op = $("input[name='answer']:checked").val();  
	if(op == undefined){
		alert("请作答!");
		return;
	}
	
	if(c_type==0){
		// evaluation
		c_record = "||"+op;
		records[c_question.q_num-1] = c_record;
		c_record="";
		reasons[c_question.q_num-1] = "";
	}else{
		// intervention
		c_record = c_record + "||" +op;
		if(op == c_question.answer){
			if(c_record.split("||").length == 2){
				reasonQue(0);
			}else{
				reasonQue(1);
			}
			records[c_question.q_num-1] = c_record;
			c_record="";
		}else{
			alert("很遗憾，回答错误！");
			if(c_record.split("||").length > c_question.interventions.length){
				reasonQue(1);
				records[c_question.q_num-1] = c_record;
				c_record="";
			}
			else{
				interventnionQue(c_record.split("||").length-1);
				return;
			}
		}
	}
	
	if( c_question.q_num == q_total ){
		finishTest();
		return;
	}
	
	presentQuestion(c_question.q_num+1);
}

function finishTest(){
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
			reasons : reasons
		},
		dataType : "json",
		error : function(e) {
			console.log(e);
		},
		success : function(data){
			mainView.router.loadPage("test_result.html");
		}
	});
}

function reasonQue(type){
	if( type == 0 ){
		reasons[c_question.q_num-1]="";
	}else{
		myApp.popup('.popup-reason');
		reasons[c_question.q_num-1]="";
	}
}

function interventnionQue(num){
	for(var i=0; i<c_question.interventions.length;i++){
		var intervention = c_question.interventions[i];
		if(intervention.level==num){
			$$('#inte_text').html(intervention.text);
			break;
		}
	}
	myApp.openPanel('right');
}

