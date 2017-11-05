function presentBaseTest(){
	$$("#part_list").html("");
	for( var i=1;i<=c_test.parts.length;i++){
		var a_part = $$("<a></a>").attr('class',"button").attr('id','part'+i).attr('href',"#");
		$$("#part_list").append(a_part);
	}
	for( var i=0;i<c_test.parts.length;i++){
		var p = c_test.parts[i];
		$$('#part'+p.p_no).append(p.exerciseType);
	}
	$$('#t_title').html(c_test.title);
	if(c_type==1){
		$$('#t_title').append("(intervention)");
	}
}


function playSound(qid){
	$('#que-'+qid)[0].play();
}

function count() {
	if(c_type!=0)
		return;
	var t = 15;
	var a = setInterval(daojishi, 1000);// 1000毫秒
	function daojishi() {
		t--;
		// 刷新时间显示
		$$("#count").html("还剩"+t+"秒，请作答！");
		if (t == 0) {
			clearInterval(a);
			$$("#count").html("");
			nextQuestion(0);
		}
	}
}

function presentQuestion(qno){
	presentBaseTest();
	for(var ti=0; ti<c_test.parts.length; ti++){
		var part = c_test.parts[ti];
		for(var pi=0; pi<part.exercises.length; pi++){
			var exer = part.exercises[pi];
			for( var ei=0; ei<exer.questions.length; ei++){
				var ques = exer.questions[ei];
				if( ques.q_num == qno ){
					c_part = part;
					c_exercise = exer;
					c_question = ques;
				}
			}
		}
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
		$$('#i_audio').hide();
	}
	
	playSound(c_question.id);
}

function nextQuestion(t){
	$$('#inte_text').html("");
	var op = $("input[name='answer']:checked").val();  
	if(op == undefined && t!=0){
		alert("请作答!");
		return;
	}
	
	if(t==0 &&op == undefined){
		op=-1;
	}
	
	if(c_type==0){
		// evaluation
		c_record = "||"+op;
		records[c_question.q_num-1] = c_record;
		c_record="";
		reasons[c_question.q_num-1] = "";
		ToNextQue();
	}else{
		// intervention
		c_record = c_record + "||" +op;
		if(op == c_question.answer){
			if(c_record.split("||").length == 2){
				reasonQue(0);
				records[c_question.q_num-1] = c_record;
				c_record="";
				ToNextQue();
			}else{
				reasonQue(1);
				records[c_question.q_num-1] = c_record;
				c_record="";
			}
		}else{
			alert("很遗憾，回答错误！");
			if(c_record.split("||").length > c_question.interventions.length){
				reasonQue(1);
				records[c_question.q_num-1] = c_record;
				c_record="";
			}
			else{
				interventnionQue(c_record.split("||").length-1);
			}
		}
		return;
	}
}

function ToNextQue(){
	console.log(records[c_question.q_num-1]);
	console.log(c_question.answer);
	if( c_question.q_num == q_total ){
		finishTest();
		return;
	}
	
	presentQuestion(c_question.q_num+1);
}

function reasonQue(type){
	if( type == 0 ){
		reasons[c_question.q_num-1]="";
	}else{
		myApp.popup('.popup-reason');
	}
}

function reasonSubmit(){
	reasons[c_question.q_num-1]=$$("#reason_text").val();
	myApp.closeModal('.popup-reason');
	ToNextQue();
}

function interventnionQue(num){
	for(var i=0; i<c_question.interventions.length;i++){
		var intervention = c_question.interventions[i];
		if(intervention.level==num){
			$$('#inte_text').html(intervention.text);
			if( intervention.audio_path == null || intervention.audio_path=="" || intervention.audio_path.length == 0){
				$$('#i_audio').hide();
			}else{
				$$('#i_audio').attr('src',intervention.audio_path);
				$$('#i_audio').show();
			}
			break;
		}
	}
	myApp.openPanel('right');
}