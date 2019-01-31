function presentBaseTest(cqno){
	for( var i=0;i<c_test.parts.length;i++){
		var p = c_test.parts[i];
		var t = c_test.parts[i].partExers[0].exercise.type;
		$$('#part'+p.p_no).append(t.name);
	}


	var c=document.getElementById("progress");
	var ctx=c.getContext("2d");

	$("#progress").attr('width',$$("#test_text").width());

	ctx.fillStyle="#FFFFFF";
	ctx.fillRect(0,0, $$("#test_text").width(),80);

	var cellw = ($$("#test_text").width()-40)/16.0;

	ctx.moveTo(20,0);
	ctx.lineTo(20,35);
	ctx.stroke();

	var t_no = 0;
	for( var i=0;i<c_test.parts.length;i++){
		var qsum=0;
		var e_type = "";
		for( var j=0;j<c_test.parts.length;j++){
			var t_part = c_test.parts[j];
			if(t_part.p_no == i){
				for(var k=0; k<t_part.partExers.length; k++){
					var exe = t_part.partExers[k].exercise;
					qsum = qsum + exe.questions.length;
				}
				e_type = t_part.partExers[0].exercise.type.name;
				break;
			}
		}

		ctx.fillStyle="#115D8D";
		ctx.font="14px Arial";
		ctx.fillText(e_type,20+cellw*t_no+cellw/2.0*qsum - e_type.length/2*7,20);
		t_no = t_no + qsum;

		ctx.moveTo(20+cellw*t_no,00);
		ctx.lineTo(20+cellw*t_no,35);
		ctx.stroke();
	}
	{
		ctx.fillStyle="#115D8D";
		for(var i=0; i<16; i++){
			ctx.rect(20+cellw*i,35, cellw,40);
			if(i<cqno)
			{
				ctx.fillRect(20+cellw*i,35, cellw,40);
			}
		}
		ctx.stroke();
	}
	$$('#t_title').html(c_test.title);
}

var starttimeque;
function playSound(qid){
	$$("#SubmitButton").attr("disabled","disabled");
	//$$("#SubmitButton").removeAttr("disabled");
	var a = setInterval(daojishi, 3000);// 1000毫秒
	function daojishi() {
		$('#que-'+qid)[0].play();
		clearInterval(a);
	}
	//$$("#SubmitButton").attr("style","display:none;");
	//$$("#NextButton").attr("style","display:none;");
	//$$("#intervention").attr("style","display:none;");	
}

function count(qid) {
	$$("#SubmitButton").attr("style","display:;");
	starttimeque = new Date();
	$$("#SubmitButton").removeAttr("disabled");
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
	presentBaseTest(c_pro);
	findCurrent();
	ButtonSwitch(false);

	//预览内容
	if(c_exercise.questions.length>1){
		$("#preview").html("本大题共包含"+c_exercise.questions.length+"个小题，点击此处对应题号预览题目选项：&nbsp;&nbsp;&nbsp;&nbsp;")
		for(var qno = 0; qno<c_exercise.questions.length; qno++){
			for( var ei=0; ei<c_exercise.questions.length; ei++){
				var ques = c_exercise.questions[ei];
				if(ques.q_num == qno){
					var href = $("<a></a>").attr("href","#").attr("class","open-preivew").attr("id","preview-op").attr("style","color:rgb(17,93,141);").append(c_pro-c_qno+qno+1);
					var preview = $("<p></p>").append($("<strong></strong>").append(href));
					$("#preview").append(href);
					$("#preview").append("&nbsp;&nbsp;&nbsp;&nbsp;");
				}
			}
		}
	}else{
		$("#preview").html("");
	}

	$$('#exercise_description').html(c_exercise.description);
	
	$$('#q_text').html("第"+(c_pro+1)+"题");
	var options = c_question.options.split("||");
	$$('#q_op').html("");
	for(var i=1; i<options.length; i++){
		var p_op = $$("<p></p>");
		var i_op = $$("<input></input>").attr('type','radio').attr('name','answer').attr('value',i);
		p_op.append(i_op).append(options[i]);
		$$('#q_op').append(p_op);
	}
	
	if(c_type==1){
		$$('#inte_text').html("");
		$$('#i_audio').hide();
	}
	
	$.each($("#audios").children(),function(index,value){
		value.pause();
	});
	$$("#intervention").attr("style","display:none;");	
	playSound(c_question.id);
}

var time_st;
function submitAnswer(t){
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
		if(c_record.split("||").length == 2){
			var endtimeqeu = new Date();
			reacttimeconsume[c_pro] = endtimeqeu - starttimeque;
		}
		if(op == c_question.answer){
			if(c_record.split("||").length == 2){
				reasonQue(0);
				records[c_pro] = c_record;
				c_record="";
				ButtonSwitch(true);
				myApp.popup('.ans-right-popup');
				timeconsume[c_pro]=0;
			}else{
				timeconsume[c_pro]=(new Date()).getTime()-time_st.getTime();
				reasonQue(1);
				records[c_pro] = c_record;
				c_record="";
				myApp.popup('.ans-right-popup');
				if(c_test.collect == 0)
					ButtonSwitch(true);
			}
		}else{
			time_st = new Date();
			$("input[name='answer']:checked").attr('disabled','true');
			$("input[name='answer']:checked").each(function(){
				$(this)[0].checked = false;
			});

			alert("很遗憾，回答错误！");
			if(c_record.split("||").length > c_question.interventions.length + 1){
				reasonQue(0)
				records[c_pro] = c_record;
				c_record="";
				ButtonSwitch(true);
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

function RightToNextQue(){
	myApp.closeModal($$(".ans-right-popup"));
	ToNextQue();
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
	if( $$(".picker-intervention").css('display') != 'none')
		myApp.closeModal('.picker-intervention')
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
	$$("#interslist").attr("style","display:none;");

	$$('#inte_text').html("");
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

	$$("#intervention").attr("style","display:;");	
	myApp.pickerModal('.picker-intervention', $$('#intervention'));
}

function ViewInter(num){
	if(num == 0){
		myApp.closeModal($$(".ans-right-popup"));
		num=1;
	}
	interventnionQue(num);
	$$("#interslist").attr("style","display:;");
}