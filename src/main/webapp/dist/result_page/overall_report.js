function init_overall(userid){
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : baseUrl + "test/getOverallReport",
		data : {uid: userid},
		dataType : "json",
		error : function(e) {
			console.log(e);
		},
		success : function(data) {
			$$.each(data.test_name,function(index,value){
				$("#o_tab_r1").append($("<th></th>").append(value).attr("colspan",2));
			});
			for(var i=0; i<data.test_result[0].length;i++){
				$("#o_tab_r2").append($("<th></th>").append(data.test_result[0][i]));
				$("#o_tab_r3").append($("<td></td>").append(data.test_result[1][i]));
				$("#o_tab_r4").append($("<td></td>").append(data.test_result[2][i]));
				$("#o_tab_r5").append($("<td></td>").append(data.test_result[3][i]));
			}

			for(var i=0; i<data.inter_freq[0].length;i++){
				$("#i_tab_r1").append($("<th></td>").append(data.inter_freq[0][i]));
				$("#i_tab_r2").append($("<td></td>").append(data.inter_freq[1][i]));
				$("#i_tab_r3").append($("<td></td>").append(data.inter_freq[2][i]));
				$("#i_tab_r4").append($("<td></td>").append(data.inter_freq[3][i]));
				$("#i_tab_r5").append($("<td></td>").append(data.inter_freq[4][i]));
				$("#i_tab_r6").append($("<td></td>").append(data.inter_freq[5][i]));
			}

			var data1 = [
				{
					name : '提示前',
					value: data.score[0],
					color: '#1385a5'
				},
				{
					name : '提示后',
					value: data.score[1],
					color: '#c56966'
				}
			];
			var chart = new iChart.ColumnMulti2D({
				render : 'scoreCanvas',
				data: data1,
				labels: data.test_name,
				title : '通过提示前后的成绩对比，看看自己是否有了进步~',
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
						end_scale:70,
						scale_space:10
					}],
					width:500,
					height:330
				}
			});
			chart.draw();

			var data2=[];
			var colors = ['#4f81bd','#bd4d4a','#98c045'];
			for(var i=0; i<data.test_name.length; i++){
			console.log(data2);
				data2[i] = {
					name : data.test_name[i],
					value : data.inter_percent[i],
					color : colors[i]
				};
			}
			console.log(data2);
	        
			var chart = new iChart.ColumnStacked2D({
					render : 'percentCanvas',
					data: data2,
					labels:["词汇与表达","语法","主旨大意","细节","推理"],
					title : {
						text:'技能提示情况',
						color:'#254d70'
					},
					width : 1000,
					height : 400,
					column_width:90,
					background_color : '#ffffff',
					shadow : true,
					shadow_blur : 2,
					shadow_color : '#aaaaaa',
					shadow_offsetx : 1,
					shadow_offsety : 0, 
					sub_option:{
						label:{color:'#f9f9f9',fontsize:12,fontweight:600},
						border : {
							width : 2,
							color : '#ffffff'
						} 
					},
					label:{color:'#254d70',fontsize:12,fontweight:600},
					percent:true,//标志为百分比堆积图
					showpercent:true,
					decimalsnum:1,
					legend:{
						enable:true,
						background_color : null,
						line_height:25,
						color:'#254d70',
						fontsize:12,
						fontweight:600,
						border : {
							enable : false
						}
					},
					coordinate:{
						background_color : 0,
						axis : {
							color : '#c0d0e0',
							width : 0
						}, 
						scale:[{
							 position:'left',	
							 scale_enable : false,
							 start_scale:0,
							 scale_space:50,
							 label:{color:'#254d70',fontsize:11,fontweight:600}
						}],
						width:600,
						height:260
					}
			});
		}
	});
}