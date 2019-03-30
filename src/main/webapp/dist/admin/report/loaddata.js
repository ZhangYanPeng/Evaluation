function loadStuData(s){
	var testno = $("#testno").val();
	if(testno == 0)
		return;
	else{
		$.ajax({
			sync : false,
			cache : false,
			type : 'POST',
			crossDomain : true,
			url : "load_result",
			data : {
				engid : eid,
				test : testno,
				sortby : s
			},
			dataType : "json",
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			error : function(e) {
				alert("网络错误，请重试");
			},
			success : function(data) {
				studata = data;
				$("#reportcontent").html("");
				$("#ave_table").html("");
				$("#ave_sta").attr("style","display:block;");
				var ave_param = new Array(0,0,0,0,0,0,0);
				$.each(data, function(index,value){
					ave_param[0] = ave_param[0] + value.score;
					ave_param[1] = ave_param[1] + value.eval_score;
					ave_param[2] = ave_param[2] + value.potential;
					ave_param[3] = ave_param[3] + value.time_consume_val;
					ave_param[4] = ave_param[4] + value.ave_time_consume_val;
					ave_param[5] = ave_param[5] + value.ave_inter_time_val;
					var td1 = $("<td></td>").attr("width","150px").attr("class","tdColor").append(value.student_no);
					var td2 = $("<td></td>").attr("width","150px").attr("class","tdColor").append($("<a></a>").attr("href","javascript:getReport('"+value.student_no+"');").append(value.student_name));
					var td3 = $("<td></td>").attr("width","150px").attr("class","tdColor").append(value.score);
					var td4 = $("<td></td>").attr("width","150px").attr("class","tdColor").append(value.eval_score);
					var td5 = $("<td></td>").attr("width","150px").attr("class","tdColor").append(value.potential.toFixed(2));
					var td6 = $("<td></td>").attr("width","150px").attr("class","tdColor").append(value.time_consume);
					var td7 = $("<td></td>").attr("width","150px").attr("class","tdColor").append(value.ave_time_consume);
					var td8 = $("<td></td>").attr("width","150px").attr("class","tdColor").append(value.ave_inter_time);
					var tr = $("<tr></tr>").append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8);
					$("#reportcontent").append(tr);
				});
				for (var i=0;i<6;i++){
					ave_param[i] = ave_param[i] / data.length;
					if(i>2){
						var td = $("<td></td>").attr("class","tdColor").append(formatTimeStr(ave_param[i]));
					}
					else{
						var td = $("<td></td>").attr("class","tdColor").append(ave_param[i].toFixed(2));
					}
					$("#ave_table").append(td);
				}
			}
		});
	}
}

function loadTestData(){
	var testno = $("#testno").val();
	if(testno == 0)
		return;
	else{
		$.ajax({
			sync : false,
			cache : false,
			type : 'POST',
			crossDomain : true,
			url : "load_test_result",
			data : {
				engid : eid,
				test : testno
			},
			dataType : "json",
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			error : function(e) {
				alert("网络错误，请重试");
			},
			success : function(data) {
				testdata = data;
				$("#tcon_tab").html("");
				var tr_title = $("<tr></tr>");
				tr_title.append($("<td></td>").attr("class","tdColor").attr("colspan","3").attr("style","background-color: #4390b9; width: 300px;").append($("<font></font>").attr("color","white").append("题号")));
				for(var i=1; i<17; i++){
					tr_title.append($("<td></td>").attr("class","tdColor").attr("style","background-color: #4390b9; width: 50px;").append($("<font></font>").attr("color","white").append($("<a></a>").attr("href","javascript:drawQuePic("+i+");").append(i))));
				}
				tr_title.append($("<td></td>").attr("class","tdColor").attr("style","background-color: #4390b9; width: 150px;").append($("<font></font>").attr("color","white").append("总人数")));
				$("#tcon_tab").append(tr_title);
				{
					var tr1 = $("<tr></tr>");
					tr1.append($("<td></td>").attr("class","tdColor").attr("rowspan","5").attr("style","width: 50px;").append("提<br>示<br>情<br>况"));
					tr1.append($("<td></td>").attr("class","tdColor").attr("style","width: 150px;").append("未提示"));
					tr1.append($("<td></td>").attr("class","tdColor").attr("style","background-color: white;").attr("style","width: 100px;").append("得4分"));
					for(var i=0; i<17; i++){
						tr1.append($("<td></td>").attr("class","tdColor").append(data[0][i]));
					}						
					$("#tcon_tab").append(tr1);
				}
				{
					var tr2 = $("<tr></tr>");
					tr2.append($("<td></td>").attr("class","tdColor").append("提示一次"));
					tr2.append($("<td></td>").attr("class","tdColor").append("得3分"));
					for(var i=0; i<17; i++){
						tr2.append($("<td></td>").attr("class","tdColor").attr("style","background-color: white;").append(data[1][i]));
					}						
					$("#tcon_tab").append(tr2);
				}
				{
					var tr3 = $("<tr></tr>");
					tr3.append($("<td></td>").attr("class","tdColor").append("提示二次"));
					tr3.append($("<td></td>").attr("class","tdColor").append("得2分"));
					for(var i=0; i<17; i++){
						tr3.append($("<td></td>").attr("class","tdColor").attr("style","background-color: white;").append(data[2][i]));
					}						
					$("#tcon_tab").append(tr3);
				}
				{
					var tr4 = $("<tr></tr>");
					tr4.append($("<td></td>").attr("class","tdColor").append("提示三次"));
					tr4.append($("<td></td>").attr("class","tdColor").append("得1分"));
					for(var i=0; i<17; i++){
						tr4.append($("<td></td>").attr("class","tdColor").attr("style","background-color: white;").append(data[3][i]));
					}						
					$("#tcon_tab").append(tr4);
				}
				{
					var tr5 = $("<tr></tr>");
					tr5.append($("<td></td>").attr("class","tdColor").append("提示四次"));
					tr5.append($("<td></td>").attr("class","tdColor").append("得0分"));
					for(var i=0; i<17; i++){
						tr5.append($("<td></td>").attr("class","tdColor").attr("style","background-color: white;").append(data[4][i]));
					}						
					$("#tcon_tab").append(tr5);
				}
				{
					var tr6 = $("<tr></tr>");
					tr6.append($("<td></td>").attr("class","tdColor").attr("colspan","3").attr("style","background-color: #4390b9; width: 300px;").append($("<font></font>").attr("color","white").append($("<a></a>").attr("href","javascript:drawInterPic();").append("题目平均提示频率"))));
					for(var i=0; i<17; i++){
						tr6.append($("<td></td>").attr("style","background-color: white;").attr("class","tdColor").append(data[5][i]));
					}						
					$("#tcon_tab").append(tr6);
				}
			}
		});
	}
}

function loadAbilityData(){
	var testno = $("#testno").val();
	if(testno == 0)
		return;
	else{
		$.ajax({
			sync : false,
			cache : false,
			type : 'POST',
			crossDomain : true,
			url : "load_ability_result",
			data : {
				engid : eid,
				test : testno
			},
			dataType : "json",
			contentType : "application/x-www-form-urlencoded;charset=utf-8",
			error : function(e) {
				alert("网络错误，请重试");
			},
			success : function(data) {
				$("#acon_tab").html("");
				var tr_title = $("<tr></tr>");
				tr_title.append($("<td></td>").attr("class","tdColor").attr("style","background-color: #4390b9; width: 200px;").append($("<font></font>").attr("color","white").append("听力技能")));
				tr_title.append($("<td></td>").attr("class","tdColor").attr("style","background-color: #4390b9; width: 150px;").append($("<font></font>").attr("color","white").append("相关题目")));
				tr_title.append($("<td></td>").attr("class","tdColor").attr("style","background-color: #4390b9; width: 150px;").append($("<font></font>").attr("color","white").append("总提示频率")));
				tr_title.append($("<td></td>").attr("class","tdColor").attr("style","background-color: #4390b9; width: 150px;").append($("<font></font>").attr("color","white").append("平均提示频率")));
				$("#acon_tab").append(tr_title);
				var ab = new Array("词汇与表达","语法","主旨大意","细节","推理");
				for(var i=0; i<5; i++){
					var tr_con = $("<tr></tr>");
					tr_con.append($("<td></td>").attr("class","tdColor").append(ab[i]));
					for(var j=0; j<3; j++){
						tr_con.append($("<td></td>").attr("style","background-color: white;").attr("class","tdColor").append(data[i][j]));
					}
					$("#acon_tab").append(tr_con);
				}
				var data1 = new Array();
				var data2 = new Array();
				for(var i=0; i<5; i++){
					data1[i]=data[i][1];
					data2[i]=data[i][2];
				}
				var data = [
				{
					name : '总提示频率',
					value: data1,
					color: '#1385a5'
				},
				{
					name : '平均提示频率',
					value: data2,
					color: '#c56966'
				}
				];
				var chart = new iChart.ColumnMulti2D({
					render : 'interCanvas',
					data: data,
					labels:["词汇与表达","语法","主旨大意","细节","推理"],
					title : '技能提示频率',
					width : 800,
					height : 500,
					background_color : '#ffffff',
					legend:{
						enable: true,
						background_color : null,
						border : {
							enable : false
						},
						align : 'center',
						valign : 'top'
					},
					coordinate:{
						background_color : '#f1f1f1',
						scale:[{
							position:'left',	
							start_scale:0,
							end_scale:10,
							scale_space:2
						}],
						width:500,
						height:330
					}
				});
				chart.draw();
			}

		});
	}
}