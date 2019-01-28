function drawScorePic(){
	var sta = new Array();
	for(var i=0; i<4; i++)
		sta[i] = 0;
	$.each(studata, function(index,value){
		var i = parseInt((value.score-1)/16);
		if(i<0)
			i=0;
		sta[i] = sta[i]+1;
	});
	var data = [
	{name : '0-16',value : sta[0],color:'#a5c2d5'},
	{name : '17-32',value : sta[1],color:'#cbab4f'},
	{name : '33-48',value : sta[2],color:'#76a871'},
	{name : '49-64',value : sta[3],color:'#9f7961'},
	];

	var chart = new iChart.Column2D({
		render : 'stuCanvas',
		data: data,
		width : 800,
		height : 400,
		coordinate:{
			background_color:'#fefefe',
			scale:[{
				position:'left',	
				scale_space:1,
				listeners:{
					parseText:function(t,x,y){
						return {text:t}
					}
				}
			}]
		}
	});

	chart.plugin(new iChart.Custom({
		drawFn:function(){
						/**
						 *计算位置
						 */	
						 var coo = chart.getCoordinate(),
						 x = coo.get('originx'),
						 y = coo.get('originy'),
						 H = coo.height;
						/**
						 *在左侧的位置，设置逆时针90度的旋转，渲染文字。
						 */
						 chart.target.textAlign('center')
						 .textBaseline('middle')
						 .textFont('600 13px Verdana')
						 .fillText('学生人数',x-40,y+H/2,false,'#6d869f', false,false,false,-90);

						}
					}));

	chart.draw();
}

function drawInterPic(){
	var sta = new Array();
	for(var i=0; i<4; i++)
		sta[i] = 0;
	var data = new Array();

	for(var i=0; i<16; i++){
		data[i] = {name : "Q"+i, value : testdata[5][i], color:'#76a871' };
	}	
	console.log(data);

	var chart = new iChart.Column2D({
		render : 'testCanvas',
		data: data,
		title : {
					text : '题目平均提示频率',
					color : '#3e576f'
				},
		width : 800,
		height : 400,
		coordinate:{
			background_color:'#fefefe',
			scale:[{
				position:'left',	
				scale_space:1,
				listeners:{
					parseText:function(t,x,y){
						return {text:t}
					}
				}
			}]
		}
	});

	chart.plugin(new iChart.Custom({
		drawFn:function(){
						/**
						 *计算位置
						 */	
						 var coo = chart.getCoordinate(),
						 x = coo.get('originx'),
						 y = coo.get('originy'),
						 H = coo.height;
						/**
						 *在左侧的位置，设置逆时针90度的旋转，渲染文字。
						 */
						 chart.target.textAlign('center')
						 .textBaseline('middle')
						 .textFont('600 13px Verdana')
						 .fillText('学生人数',x-40,y+H/2,false,'#6d869f', false,false,false,-90);

						}
					}));

	chart.draw();
}

function drawQuePic(qi){
	var sum = testdata[0][qi-1] + testdata[1][qi-1] + testdata[2][qi-1] + testdata[3][qi-1] + testdata[4][qi-1];
	var data = [
	{name : '4分',value : testdata[0][qi-1]/sum*100,color:'#9d4a4a'},
	{name : '3分',value : testdata[1][qi-1]/sum*100,color:'#5d7f97'},
	{name : '2分',value : testdata[2][qi-1]/sum*100,color:'#97b3bc'},
	{name : '1分',value : testdata[3][qi-1]/sum*100,color:'#a5aaaa'},
	{name : '0分',value : testdata[4][qi-1]/sum*100,color:'#778088'}
	];

	new iChart.Pie2D({
		render : 'testCanvas',
		data: data,
		title : '第'+qi+'题各分值得分人数分布图',
		legend : {
			enable : true
		},
		showpercent:true,
		decimalsnum:2,
		width : 800,
		height : 400,
		radius:140
	}).draw();
}

function getReport(stuno){
	$("#stuCanvas").html("");
	var a_single = $("<a></a>").attr("href","javascript:loadSignleReport("+stuno+");").append($("<button></button>").attr("class","userbtn").attr("disabled","disabled").append("本次成绩"));
	var a_all = $("<a></a>").attr("href","javascript:loadOverallReport("+stuno+");").append($("<button></button>").attr("class","userbtn").attr("disabled","disabled").append("三次成绩"));
	var cfd = $("<div></div>").attr("class","cfD").append(a_single).append(a_all);
	var div = $("<div></div>").attr("class","conform").append(cfd);
	$("#stuCanvas").append(div);
}

function loadSignleReport(stuno){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		traditional: true,
		url : "getSingleReport",
		data : {
			stu : stuno,
			testno : $("#testno").val()
		},
		dataType : "json",
		error : function(e) {
			console.log(e);
		},
		success : function(data){
			console.log(data.path);
			window.location.href=data.path;
		}
	});
}

function loadOverallReport(stuno){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		traditional: true,
		url : "getOverallReport",
		data : {
			stu : stuno,
			testno : $("#testno").val()
		},
		dataType : "json",
		error : function(e) {
			console.log(e);
		},
		success : function(data){
			window.location.href=data.path;
		}
	});
}