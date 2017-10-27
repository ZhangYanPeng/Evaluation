function startTest(tid){
	records = new Array();
	reasons = new Array();

	$("load_complete").hide();

	$$.ajax({
		async : true,
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
			loadData(data);
		}
	});
}

function loadData(data){
	c_test = data;
	q_total = 0;
	load_progress = 0;
	$("#audios").html("");
	$("#load_complete").hide();
	for(var ti=0; ti<c_test.parts.length; ti++){
		var part = c_test.parts[ti];
		for(var pi=0; pi<part.exercises.length; pi++){
			var exer = part.exercises[pi];
			q_total = q_total+exer.questions.length;
			for( var qi=0; qi<exer.questions.length; qi++){
				loadAudio(exer.questions[qi]);
			}
		}
	}
	$("#test_title").append(data.title);
	$("#load_progress").html("正在加载所需音频资源("+load_progress+"/"+q_total+")");
}

function loadAudio(que){
//加载所有音频信息（题目和干预）
	var aud = $("<audio></audio>").attr('id','que-'+que.id).attr('onended','javascript:count();').attr('oncanplaythrough','javascript:loaded();');
	aud.attr("preload","preload").attr('src', baseUrl + que.audio_path);
	$("#audios").append(aud);
}

function loaded(){
	load_progress = load_progress + 1;
	if( load_progress == q_total){
		$("#load_complete").hide();
		$("#load_complete").show();
	}else{
		$("#load_progress").html("正在加载所需音频资源("+load_progress+"/"+q_total+")");
	}
}


