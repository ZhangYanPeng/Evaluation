function init_report(userid, testid){
	$$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		crossDomain : true,
		url : baseUrl + "test/getTestReport",
		data : {uid: userid, tid : testid},
		dataType : "json",
		error : function(e) {
		},
		success : function(data) {
			$$("#information").html("学号："+ data.stu_no +"&nbsp;&nbsp;&nbsp;&nbsp;姓名："+ data.name +"&nbsp;&nbsp;&nbsp;&nbsp;"+ data.test);
			$$("#score").html(data.score);
			$$("#rank").html(data.rank);
			$$("#r_sum").html(data.right_sum);
			var data_my = [
			{name : '高难度',value : data.percents[0],color:'yellow'},
			{name : '中等难度',value : data.percents[1],color:'blue'},
			{name : '容易',value : data.percents[2],color:'green'},
			];

			var data_total = [
			{name : '高难度',value : data.percents[3],color:'yellow'},
			{name : '中等难度',value : data.percents[4],color:'blue'},
			{name : '容易',value : data.percents[5],color:'green'},
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

			var tab_r1 =  $("<tr></tr>").append($("<th></th>").append("题号"));

			$("#details").append(tab_r1);
			var tab_r2 =  $("<tr></tr>").append($("<td></td>").append("题目难度"));
			var tab_r3 =  $("<tr></tr>").append($("<td></td>").append("我的答案"));
			var tab_r4 =  $("<tr></tr>").append($("<td></td>").append("正确答案"));

			for (var i=0; i<data.results[0].length; i++){
				tab_r1.append($("<th></th>").append(i+1));
				tab_r2.append($("<td></td>").append((data.results[0])[i]));
				tab_r3.append($("<td></td>").append((data.results[1])[i]));
				tab_r4.append($("<td></td>").append((data.results[2])[i]));
			}

			$("#details").append(tab_r1).append(tab_r2).append(tab_r3).append(tab_r4);
		}
	});

	
}