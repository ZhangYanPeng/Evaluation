function init_evaluation(userid, testid){
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : baseUrl + "test/getEvaluationReport",
		data : {uid: userid, tid : testid},
		dataType : "json",
		error : function(e) {
		},
		success : function(data) {
			$("#overall").html("");
			$("#test_name").html(data.test);
			$.each(data.overallPerformance,function(index,value){
				$("#overall").append($("<td></td>").append(value));
			});

			for(var i=0; i<data.score_statictis[0].length; i++){
				$("#tab_r_1").append($$("<th></th>").append(data.score_statictis[0][i]));
				$("#tab_r_2").append($$("<td></td>").append(data.score_statictis[1][i]));
				$("#tab_r_3").append($$("<td></td>").append(data.score_statictis[2][i]));
				$("#tab_r_4").append($$("<td></td>").append(data.score_statictis[3][i]));
				$("#tab_r_5").append($$("<td></td>").append(data.score_statictis[4][i]));
				$("#tab_r_6").append($$("<td></td>").append(data.score_statictis[5][i]));
			}

			for(var i=0; i<3; i++){
				$("#inter_r_1").append($$("<td></td>").append(data.ability[0][i]));
				$("#inter_r_2").append($$("<td></td>").append(data.ability[1][i]));
				$("#inter_r_3").append($$("<td></td>").append(data.ability[2][i]));
				$("#inter_r_4").append($$("<td></td>").append(data.ability[3][i]));
				$("#inter_r_5").append($$("<td></td>").append(data.ability[4][i]));
			}

			var data = [
				{
					name : '我的总提示频率',
					value: data.inter_sta[0],
					color: '#1385a5'
				},
				{
					name : '全部学生平均总提示频率',
					value: data.inter_sta[1],
					color: '#c56966'
				}
			];
			var chart = new iChart.ColumnMulti2D({
				render : 'interCanvas',
				data: data,
				labels:["词汇与表达","语法","主旨大意","细节","推理"],
				title : '我在群体中的相对表现',
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