function startTest(tid){
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
			test = data;
			q_total=0;
			for(var ti=0; ti<test.parts.length; ti++){
				var part = test.parts[ti];
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
	for( var i=1;i<=test.parts.length;i++){
		var a_part = $$("<a></a>").attr('class',"button").attr('id','part'+i).attr('href',"#");
		$$("#part_list").append(a_part);
	}
	for( var i=0;i<test.parts.length;i++){
		var p = test.parts[i];
		$$('#part'+p.p_no).append(p.exerciseType.description);
	}
}

function presentQuestion(qno){
	presentBaseTest();
	for(var ti=0; ti<test.parts.length; ti++){
		var part = test.parts[ti];
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
	
}
