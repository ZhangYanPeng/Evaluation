function init_report(){
	var data_my = [
	{name : '高难度',value : 11,color:'yellow'},
	{name : '中等难度',value : 25,color:'blue'},
	{name : '容易',value : 64,color:'green'},
	];

	var data_total = [
	{name : '高难度',value : 6,color:'yellow'},
	{name : '中等难度',value : 23,color:'blue'},
	{name : '容易',value : 71,color:'green'},
	];
	
	new iChart.Pie2D({
		render : 'myResult',
		data: data_my,
		title : '我的答题情况',
		legend : {
			enable : false
		},
		showpercent:true,
		decimalsnum:2,
		width : 400,
		height : 200,
		radius:140
	}).draw();

	new iChart.Pie2D({
		render : 'totalResult',
		data: data_total,
		title : '全部学生作答情况',
		legend : {
			enable : false
		},
		showpercent:true,
		decimalsnum:2,
		width : 400,
		height : 200,
		radius:140
	}).draw();
}