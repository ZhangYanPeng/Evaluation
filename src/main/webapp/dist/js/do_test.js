function presentBaseTest(){
	$$("#part_list").html("");
	for( var i=0;i<c_test.parts.length;i++){
		var a_part = $$("<a></a>").attr('class',"button").attr('id','part'+i).attr('href',"#");
		$$("#part_list").append(a_part);
	}
	for( var i=0;i<c_test.parts.length;i++){
		var p = c_test.parts[i];
		var t = c_test.parts[i].partExers[0].exercise.type;
		$$('#part'+p.p_no).append(t.name);
	}
	$$('#t_title').html(c_test.title);
	if(c_type==1){
		$$('#t_title').append("(intervention)");
	}
}


function playSound(qid){
	$('#que-'+qid)[0].play();
}

function count(qid) {
	return;//不再倒计时
	if(c_type!=0)
		return;
	var t = 18;
	var a = setInterval(daojishi, 1000);// 1000毫秒
	function daojishi() {
		if(qid != c_question.id){
			clearInterval(a);
			$$("#count").html("&nbsp;");
			return;
		}
		t--;
		// 刷新时间显示
		$$("#count").html("还剩"+t+"秒，请作答！");
		if (t == -1) {
			clearInterval(a);
			$$("#count").html("&nbsp;");
			if(qid == c_question.id)
				submitAnswer(0);
		}
	}
}

function findCurrent(){
	for(var ti=0; ti<c_test.parts.length; ti++){
		var part = c_test.parts[ti];
		for(var pi=0; pi<part.partExers.length; pi++){
			var exer = part.partExers[pi].exercise;
			for( var ei=0; ei<exer.questions.length; ei++){
				var ques = exer.questions[ei];
				if( ques.q_num == c_qno && part.p_no == c_pno && part.partExers[pi].e_no == c_eno){
					c_part = part;
					c_exercise = exer;
					c_question = ques;
				}
			}
		}
	}
}

function presentQuestion(pno,eno,qno){
	presentBaseTest();
	findCurrent();
	ButtonSwitch(false);

	$$('#part'+c_part.p_no).attr("class","button active");
	$$('#progress').html(c_pro+"/"+q_total);
	myApp.setProgressbar($$('.progressbar'), c_pro*100/q_total);
	$$('#exercise_description').html(c_exercise.description);
	
	$$('#q_text').html((c_pro+1)+".");
	var options = c_question.options.split("||");
	$$('#q_op').html("");
	for(var i=1; i<options.length; i++){
		var p_op = $$("<p></p>");
		var i_op = $$("<input></input>").attr('type','radio').attr('name','answer').attr('value',i);
		p_op.append(i_op).append(options[i]);
		$$('#q_op').append(p_op);
	}
	
	if(c_type==1){
		$$('#intervention').html('<a href="#" data-popover=".picker-intervention" class="open-picker">查看干预</a>');
		$$('#inte_text').html("");
		$$('#i_audio').hide();
	}
	
	$.each($("#audios").children(),function(index,value){
		value.pause();
	});
	playSound(c_question.id);
}

function submitAnswer(t){
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
		records[c_pro] = c_record;
		c_record="";
		reasons[c_pro] = "";
		ButtonSwitch(true);
	}else{
		// intervention
		c_record = c_record + "||" +op;
		if(op == c_question.answer){
			if(c_record.split("||").length == 2){
				reasonQue(0);
				records[c_pro] = c_record;
				c_record="";
				ButtonSwitch(true);
			}else{
				reasonQue(1);
				records[c_pro] = c_record;
				c_record="";
				if(c_test.collect == 0)
					ButtonSwitch(true);
			}
		}else{
			alert("很遗憾，回答错误！");
			if(c_record.split("||").length > c_question.interventions.length + 1){
				reasonQue(0)
				records[c_pro] = c_record;
				c_record="";
			}
			else{
				interventnionQue(c_record.split("||").length-1);
			}
		}
		return;
	}
}

function ButtonSwitch(check){
	if(check){
		$$("#SubmitButton").attr("style","display:none;");
		$$("#NextButton").attr("style","display:;");
	}
	else{
		$$("#SubmitButton").attr("style","display:;");
		$$("#NextButton").attr("style","display:none;");	
	}
}

function ToNextQue(){
	c_pro = c_pro+1;
	if( c_pro == q_total ){
		finishTest();
		return;
	}
	c_qno = c_qno+1;
	if(c_qno == c_exercise.questions.length){
		c_qno=0;
		c_eno = c_eno+1;
		if(c_eno == c_part.partExers.length){
			c_eno=0;
			c_pno = c_pno+1;
		}
	}
	presentQuestion();
}

function reasonQue(type){
	if( type == 0  || c_test.collect == 0){
		reasons[c_pro]=" ";
	}else{
		myApp.popup('.popup-reason');
	}
}

function reasonSubmit(){
	reasons[c_pro]=$$("#reason_text").val();
	myApp.closeModal('.popup-reason');
	ButtonSwitch(true);
}

function interventnionQue(num){
	for(var i=0; i<c_question.interventions.length;i++){
		var intervention = c_question.interventions[i];
		if(intervention.level==num-1){
			$$('#inte_text').html(intervention.text);
			if( intervention.audio == null || intervention.audio.src=="" || intervention.audio.src.length == 0){
				$$('#i_audio').hide();
			}else{
				$$('#i_audio').attr('src',severUrl +intervention.audio.src);
				$$('#i_audio').show();
			}
			break;
		}
	}
	myApp.pickerModal('.picker-intervention', $$('#intervention'));
}