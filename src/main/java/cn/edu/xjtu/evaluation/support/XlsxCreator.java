package cn.edu.xjtu.evaluation.support;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.EngClass;
import cn.edu.xjtu.evaluation.entity.EngClassResult;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.entity.PartExer;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.entity.Record;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Test;

public class XlsxCreator {
	public static void createOuputData(EngClass engclass, List<List> stulist, List<String[][]> testlist, List<String[][]> ablist, List<Answer> answers, String path) {
		// TODO Auto-generated method stub
		HSSFWorkbook wb = new HSSFWorkbook(); 
		createStuSheet(wb, stulist,engclass);
		createTstSheet(wb, testlist,engclass);
		createAbSheet(wb, ablist,engclass);
		createQuesSheet(wb, engclass);
		createAnswerSheet(wb, answers,engclass);
		createEvalSheet(wb,answers,engclass);
		createFeedBackSheet(wb,engclass);
	    //第六步将生成excel文件保存到指定路径下  
	    try {  
	        FileOutputStream fout = new FileOutputStream(path);  
	        wb.write(fout);  
	        fout.close();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
	} 
	private static void createAnswerSheet(HSSFWorkbook wb, List<Answer> answers, EngClass engclass) {
		//第二步创建sheet  
        HSSFSheet sheet = wb.createSheet("具体作答情况");  
          
        //创建表头
        //第四步创建单元格   
        {
            HSSFRow row = sheet.createRow(0);  
            HSSFCellStyle  style = wb.createCellStyle();      
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中  
            style.setFillForegroundColor((short)39);
        	String[] title = {"个人信息","学校","学科","班级","学号","姓名"};
        	for(int i=0; i<title.length; i++){
                HSSFCell cell1 = row.createCell(i); //第一个单元格  
                cell1.setCellValue(title[i]);  
                cell1.setCellStyle(style); 
        	}
        	for(int i=0; i<3; i++){
                HSSFCell cell1 = row.createCell(6+18*i); //第一个单元格  
                cell1.setCellValue("");  
                cell1.setCellStyle(style); 

                HSSFCell cell2 = row.createCell(7+18*i);
                cell2.setCellValue("TEST" + (i+1));  
                cell2.setCellStyle(style); 
                
                for(int j=1; j<17; j++){
                	HSSFCell cell3 = row.createCell(7+j+18*i);
                    cell3.setCellValue("题目" + j);  
                    cell3.setCellStyle(style); 
                }
        	}
        }
        
        int rown = 0;
        for(Student stu : engclass.getStudents()){
        	rown++;
            HSSFRow row = sheet.createRow(rown);  
        	row.createCell(0).setCellValue("");
        	row.createCell(1).setCellValue(stu.getEngClass().getUniversity().getName());
        	row.createCell(2).setCellValue(stu.getSchool().getName());
        	row.createCell(3).setCellValue(stu.getEngClass().getName());
        	row.createCell(4).setCellValue(stu.getStudent_no());
        	row.createCell(5).setCellValue(stu.getName());

        	for(int i=0; i<3; i++){
        		for(Answer ans : answers){
        			if(ans.getStudent().getStudent_no() == stu.getStudent_no() && ans.getTest().getTestno() == (i+1)){
        				List<Record> records = SortAnswer(ans);
        				for(int j=1; j<17; j++){
                        	HSSFCell cell3 = row.createCell(7+j+18*i);
                        	String str = records.get(j-1).getResult();
                        	str = str.replaceAll("\\|", " ");
                        	str = str.replace("1", "A");
                        	str = str.replace("2", "B");
                        	str = str.replace("3", "C");
                        	str = str.replace("4", "D");
                        	str = str.replace("5", "E");
                            cell3.setCellValue(str);  
                        }
        			}
        		}
        	}
        }
	}

	private static void createEvalSheet(HSSFWorkbook wb, List<Answer> answers, EngClass engclass) {
		//第二步创建sheet  
        HSSFSheet sheet = wb.createSheet("评价试题");  
          
        //创建表头
        //第四步创建单元格   
        {
            HSSFRow row = sheet.createRow(0);  
            HSSFCellStyle  style = wb.createCellStyle();      
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中  
            style.setFillForegroundColor((short)39);
        	String[] title = {"个人信息","学校","学科","班级","学号","姓名"};
        	for(int i=0; i<title.length; i++){
                HSSFCell cell1 = row.createCell(i); //第一个单元格  
                cell1.setCellValue(title[i]);  
                cell1.setCellStyle(style); 
        	}
        	for(int i=0; i<3; i++){
                HSSFCell cell1 = row.createCell(6+5*i); //第一个单元格  
                cell1.setCellValue("");  
                cell1.setCellStyle(style); 

                HSSFCell cell2 = row.createCell(7+5*i);
                cell2.setCellValue("TEST" + (i+1));  
                cell2.setCellStyle(style); 
                
                for(int j=1; j<4; j++){
                	HSSFCell cell3 = row.createCell(7+j+5*i);
                    cell3.setCellValue("题目" + j);  
                    cell3.setCellStyle(style); 
                }
        	}
        }
        
        int rown = 0;
        for(Student stu : engclass.getStudents()){
        	rown++;
            HSSFRow row = sheet.createRow(rown);  
        	row.createCell(0).setCellValue("");
        	row.createCell(1).setCellValue(stu.getEngClass().getUniversity().getName());
        	row.createCell(2).setCellValue(stu.getSchool().getName());
        	row.createCell(3).setCellValue(stu.getEngClass().getName());
        	row.createCell(4).setCellValue(stu.getStudent_no());
        	row.createCell(5).setCellValue(stu.getName());

        	for(int i=0; i<3; i++){
        		for(Answer ans : answers){
        			if(ans.getStudent().getStudent_no() == stu.getStudent_no() && ans.getTest().getTestno() == (i+1)){
        				String[] strs = ans.getStates().split(" \\| ");
        				for(int j=1; j<4; j++){
                        	row.createCell(7+j+5*i).setCellValue(strs[j-1].substring(0, 1));
                        }
        			}
        		}
        	}
        }
	}

	private static void createFeedBackSheet(HSSFWorkbook wb, EngClass engclass) {
		//第二步创建sheet  
        HSSFSheet sheet = wb.createSheet("系统总体反馈问卷");  
          
        //创建表头
        //第四步创建单元格   
        {
            HSSFRow row = sheet.createRow(0);  
            HSSFCellStyle  style = wb.createCellStyle();      
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中  
            style.setFillForegroundColor((short)39);
        	String[] title = {"个人信息","学校","学科","班级","学号","姓名","","题目1","题目2","题目3","题目4","题目5","题目6","题目7","题目8","题目9"};
        	for(int i=0; i<title.length; i++){
                HSSFCell cell1 = row.createCell(i); //第一个单元格  
                cell1.setCellValue(title[i]);  
                cell1.setCellStyle(style); 
        	}
        }
        
        int rown = 0;
        for(Student stu : engclass.getStudents()){
        	rown++;
            HSSFRow row = sheet.createRow(rown);  
        	row.createCell(0).setCellValue("");
        	row.createCell(1).setCellValue(stu.getEngClass().getUniversity().getName());
        	row.createCell(2).setCellValue(stu.getSchool().getName());
        	row.createCell(3).setCellValue(stu.getEngClass().getName());
        	row.createCell(4).setCellValue(stu.getStudent_no());
        	row.createCell(5).setCellValue(stu.getName());
        	try{
        		String[] fb = stu.getSystemFeedback().split("\\|");
            	row.createCell(7).setCellValue(fb[0]);
        		row.createCell(8).setCellValue(transOpt(fb[1]));
            	for(int i=0; i<4; i++){
            		row.createCell(9+i).setCellValue(transOpt(fb[2+4*i])
            				+transOpt(fb[3+4*i])
            				+transOpt(fb[4+4*i])
            				+transOpt(fb[5+4*i]));
            	}
        		HSSFCell cell1 = row.createCell(13); //第一个单元格  
        		String str = "";
            	for(int k=18; k< fb.length-2; k++){
            		str += fb[k] +";";
            	}
            	cell1.setCellValue(str);
            	row.createCell(14).setCellValue(fb[fb.length-2]);
            	row.createCell(15).setCellValue(fb[fb.length-1]);
        	}catch(Exception e){
        		
        	}
        	
        }
	}

	
	//给答题记录排个序
	private static List<Record> SortAnswer(Answer ans) {
		// TODO Auto-generated method stub
		List<Record> records = new ArrayList();
		Test t = ans.getTest();
		for(int pi=0;;pi++){
			boolean checkp = false;
			for(Part p : t.getParts()){
				if(p.getP_no() == pi){
					checkp = true;
					for(int ei=0;;ei++){
						boolean checke = false;
						for(PartExer pe : p.getPartExers()){
							if(pe.getE_no() == ei){
								checke = true;
								Exercise e = pe.getExercise();
								for(int qi=0;;qi++){
									boolean checkq = false;
									for(Question q : e.getQuestions()){
										if(q.getQ_num() == qi){
											checkq = true;
											for(Record rec : ans.getRecords()){
												if(rec.getQuestion().getId() == q.getId()){
													records.add(rec);
												}
											}
										}
									}
									if(!checkq)
										break;
								}
							}
						}
						if(!checke)
							break;
					}
				}
			}
			if(!checkp)
				break;
		}
		return records;
	}

	private static void createQuesSheet(HSSFWorkbook wb, EngClass engclass) {
		// TODO Auto-generated method stub
		HSSFSheet sheet = wb.createSheet("基本信息"); 
		{
        	String[][] title = {{"个人信息","学校","学科","班级","学号","姓名","个人信息项目","学习时长","兴趣程度	课程情况","每周接触时长","练习方式偏好","自我评估"},
        			{"","","","","","","项目单位","年","/","/","时","/","/"},
        			{"","","","","","","选择","选择","选择","选择","选择","选择"},
        			{"","","","","","","具体问题","你学习英语有多长时间了？","你对英语感兴趣的程度：","进入大学后，你所在的学校是否有开设专门的英语听力课程？","你每周接触英语听力的时间是多久？","你最喜欢的练习英语听力的方式是什么？","你认为你目前的英语听力能力处于什么水平？"},
        			{"","","","","","","选项设置","A.1-4\r\nB. 5-8\r\nC.9-12\r\nD.13-16","A.非常不感兴趣 \r\nB.不感兴趣\r\nC.一般兴趣\r\nD.较感兴趣\r\nE.非常感兴趣","有——1\r\n无——0","A.0-2\r\nB.2-4\r\nC.4-6\r\nD.6-8\r\nE.8以上","A.看英语新闻\r\nB.看英/美剧\r\nC.听英文歌\r\nD.实战做题\r\nE.以上都不是","A.初级 \r\nB.中级\r\nC.高级"},};
        	for(int j=0; j<=4; j++){
                HSSFRow row = sheet.createRow(j);  
                HSSFCellStyle  style = wb.createCellStyle(); 
                style.setWrapText(true);       
                style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中  
                style.setFillForegroundColor((short)39);
        		for(int i=0; i<title[j].length; i++){
        			HSSFCell cell1 = row.createCell(i); //第一个单元格  
                    cell1.setCellValue(new HSSFRichTextString(title[j][i]));  
                    cell1.setCellStyle(style); 
        		}
        	}
        }
		
		int rn = 5;
		for(Student stu : engclass.getStudents()){
			HSSFRow row = sheet.createRow(rn);  
			{
				row.createCell(0).setCellValue("");
	        	row.createCell(1).setCellValue(stu.getEngClass().getUniversity().getName());
	        	row.createCell(2).setCellValue(stu.getSchool().getName());
	        	row.createCell(3).setCellValue(stu.getEngClass().getName());
	        	row.createCell(4).setCellValue(stu.getStudent_no());
	        	row.createCell(5).setCellValue(stu.getName());
	        	
	        	String ques = stu.getQuestionaire();
	        	String ans[];
	        	if( ques.split("\\|\\|").length > ques.split(" \\| ").length){
	        		ans = ques.split("\\|\\|");
		        	row.createCell(7).setCellValue(transOpt(ans[0]));
		        	row.createCell(8).setCellValue(transOpt(ans[1]));
		        	row.createCell(9).setCellValue(transOpt(ans[2]));
		        	row.createCell(10).setCellValue(transOpt(ans[5]));
		        	row.createCell(11).setCellValue(ans[6]);
		        	row.createCell(12).setCellValue(transOpt(ans[7]));
	        	}else{
	        		ans = ques.split(" \\| ");
		        	row.createCell(7).setCellValue(ans[0].substring(0, 1));
		        	row.createCell(8).setCellValue(ans[1].substring(0, 1));
		        	row.createCell(9).setCellValue(ans[2].substring(0, 1));
		        	row.createCell(10).setCellValue(ans[5].substring(0, 1));
		        	row.createCell(11).setCellValue(ans[6].substring(0, 1));
		        	row.createCell(12).setCellValue(ans[7].substring(0, 1));
	        	}
			}
			rn++;
		}
	}
	private static String transOpt(String str){
		switch(str){
			case "1": return "A";
			case "2": return "B";
			case "3": return "C";
			case "4": return "D";
			case "5": return "E";
			case "on": return "1";
			case "off": return "0";
		}
		return "0";
	}

	private static void createAbSheet(HSSFWorkbook wb, List<String[][]> ablist, EngClass engclass) {
		// TODO Auto-generated method stub
		HSSFSheet sheet = wb.createSheet("技能"); 
		int tn=0;
		for(String[][] testdata : ablist){
			{
	            HSSFRow row = sheet.createRow(tn*8);  
	            HSSFCellStyle  style = wb.createCellStyle();      
	            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中  
	            style.setFillForegroundColor((short)39);
	            String[] title = {"听力技能","相关题目","总提示频率","平均提示频率"};
	        	for(int i=0; i<title.length; i++){
	                HSSFCell cell1 = row.createCell(i); //第一个单元格  
	                cell1.setCellValue(title[i]);  
	                cell1.setCellStyle(style); 
	        	}
	        }
			String[] con = new String[]{"词汇与表达","语法","主旨大意","细节","推理"};
			for(int r=1; r<6; r++){
				HSSFRow row = sheet.createRow(r+tn*8); 
				{
					HSSFCell cell1 = row.createCell(0); //第一个单元格  
	                cell1.setCellValue(con[r-1]);   
				}
	            for(int i=0; i<3; i++){
	                HSSFCell cell1 = row.createCell(i+1); //第一个单元格  
	                cell1.setCellValue(testdata[r-1][i]);  
	        	}
			}
			tn++;
		}
	}

	private static void createTstSheet(HSSFWorkbook wb, List<String[][]> testlist, EngClass engclass) {
		// TODO Auto-generated method stub
		HSSFSheet sheet = wb.createSheet("试题"); 
		int tn=0;
		for(String[][] testdata : testlist){
			{
	            HSSFRow row = sheet.createRow(tn*10);  
	            HSSFCellStyle  style = wb.createCellStyle();      
	            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中  
	            style.setFillForegroundColor((short)39);
	            for(int i=1; i<17; i++){
	                HSSFCell cell1 = row.createCell(i+1); //第一个单元格  
	                cell1.setCellValue(i);  
	                cell1.setCellStyle(style); 
	        	}
	        }
			String[][] con = new String[][]{{"未提示","得4分"},{"提示一次","得3分"},{"提示二次","得2分"},{"提示三次","得1分"},{"提示四次","得0分"}};
			for(int r=1; r<6; r++){
				HSSFRow row = sheet.createRow(r+tn*10); 
				{
					HSSFCell cell1 = row.createCell(0); //第一个单元格  
	                cell1.setCellValue(con[r-1][0]);  
					HSSFCell cell2 = row.createCell(1); //第一个单元格  
	                cell2.setCellValue(con[r-1][1]);  
				}
	            for(int i=0; i<17; i++){
	                HSSFCell cell1 = row.createCell(i+2); //第一个单元格  
	                cell1.setCellValue(testdata[r-1][i]);  
	        	}
			}
			
			{
				HSSFRow row = sheet.createRow(7+tn*10); 
				{
					HSSFCell cell1 = row.createCell(0); //第一个单元格  
	                cell1.setCellValue("题目平均提示频率");   
				}
	            for(int i=0; i<16; i++){
	                HSSFCell cell1 = row.createCell(i+2); //第一个单元格  
	                cell1.setCellValue(testdata[5][i]);  
	        	}
			}
			tn++;
		}
	}

	public static void createStuSheet(HSSFWorkbook wb, List<List> stulist, EngClass engclass){
		//第二步创建sheet  
        HSSFSheet sheet = wb.createSheet("学生");  
          
        //创建表头
        //第四步创建单元格   
        {
            HSSFRow row = sheet.createRow(0);  
            HSSFCellStyle  style = wb.createCellStyle();      
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中  
            style.setFillForegroundColor((short)39);
        	String[] title = {"个人信息","学校","学科","班级","学号","姓名","Test1完成情况","诊断成绩","动态评估成绩","学习潜能分数","测试时长","总体平均反应时长","干预平均反应时长","Test2完成情况","诊断成绩","动态评估成绩","学习潜能分数","测试时长","总体平均反应时长","干预平均反应时长","Test3完成情况","诊断成绩","动态评估成绩","学习潜能分数","测试时长","总体平均反应时长","干预平均反应时长"};
        	for(int i=0; i<title.length; i++){
                HSSFCell cell1 = row.createCell(i); //第一个单元格  
                cell1.setCellValue(title[i]);  
                cell1.setCellStyle(style); 
        	}
        }

        int rown = 0;
        for(Student stu : engclass.getStudents()){
        	rown++;
            HSSFRow row = sheet.createRow(rown);  
        	row.createCell(0).setCellValue("");
        	row.createCell(1).setCellValue(stu.getEngClass().getUniversity().getName());
        	row.createCell(2).setCellValue(stu.getSchool().getName());
        	row.createCell(3).setCellValue(stu.getEngClass().getName());
        	row.createCell(4).setCellValue(stu.getStudent_no());
        	row.createCell(5).setCellValue(stu.getName());
        	int tn=0;
        	for( List<EngClassResult> stutest : stulist){
        		for(EngClassResult er : stutest){
        			if( er.getStudent_no() == stu.getStudent_no() ){
        				if(er.getTime_consume() == "-"){
        					row.createCell(6+7*tn).setCellValue("否");
        					row.createCell(7+7*tn).setCellValue("-");
        					row.createCell(8+7*tn).setCellValue("-");
        					row.createCell(9+7*tn).setCellValue("-");
        					row.createCell(10+7*tn).setCellValue("-");
        					row.createCell(11+7*tn).setCellValue("-");
        					row.createCell(12+7*tn).setCellValue("-");
        				}else{
        					row.createCell(6+7*tn).setCellValue("是");
        					row.createCell(7+7*tn).setCellValue(er.getScore_str());
        					row.createCell(8+7*tn).setCellValue(er.getEval_score());
        					row.createCell(9+7*tn).setCellValue(er.getPotential());
        					row.createCell(10+7*tn).setCellValue(er.getTime_consume());
        					row.createCell(11+7*tn).setCellValue(er.getAve_time_consume());
        					row.createCell(12+7*tn).setCellValue(er.getAve_inter_time());
        				}
            			break;
        			}
        		}
        		tn++;
            }
        }
        {
        	rown++;
        	rown++;
            HSSFRow row = sheet.createRow(rown);  
            HSSFCellStyle style = wb.createCellStyle();      
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  //居中  
            style.setFillForegroundColor((short)39);
        	String[] title = {"班级平均值","诊断成绩","动态评估成绩","学习潜能分数","测试时长","总体平均反应时长","干预平均反应时长","班级平均值","诊断成绩","动态评估成绩","学习潜能分数","测试时长","总体平均反应时长","干预平均反应时长","班级平均值","诊断成绩","动态评估成绩","学习潜能分数","测试时长","总体平均反应时长","干预平均反应时长"};
        	for(int i=0; i<title.length; i++){
                HSSFCell cell1 = row.createCell(i+6); //第一个单元格  
                cell1.setCellValue(title[i]);  
                cell1.setCellStyle(style); 
        	}
        }
        {
        	rown++;
            HSSFRow row = sheet.createRow(rown);
            int tn=0;
        	for( List<EngClassResult> stutest : stulist){
                double score = 0;
            	int eval_score = 0;
            	double potential = 0;
            	long time_consume_val = 0;
            	long ave_time_consume_val = 0;
            	long ave_inter_time_val = 0;
        		for(EngClassResult er : stutest){
        			score += er.getScore();
        			eval_score += er.getEval_score();
        			potential += er.getPotential();
        			time_consume_val += er.getTime_consume_val();
        			ave_time_consume_val += er.getAve_time_consume_val();
        			ave_inter_time_val += + er.getAve_inter_time_val();
        		}
        		score /= stutest.size();
        		eval_score /= stutest.size();
        		potential /= stutest.size();
        		time_consume_val /= stutest.size();
        		ave_time_consume_val /= stutest.size();
        		ave_inter_time_val /= stutest.size();
        		row.createCell(6+7*tn).setCellValue("");
				row.createCell(7+7*tn).setCellValue(score);
				row.createCell(8+7*tn).setCellValue(eval_score);
				row.createCell(9+7*tn).setCellValue(potential);
				row.createCell(10+7*tn).setCellValue(TranTimeStr(time_consume_val));
				row.createCell(11+7*tn).setCellValue(TranTimeStr(ave_time_consume_val));
				row.createCell(12+7*tn).setCellValue(TranTimeStr(ave_inter_time_val));
				tn++;
        	}
        }
	}

	private static String TranTimeStr(long time){
		time = time / 1000;
		long sec = time % 60;
		time = time / 60;
		long min = time % 60;
		long hour = time / 60;
		return StrEdit(String.valueOf(hour))+":"+StrEdit(String.valueOf(min))+":"
				+StrEdit(String.valueOf(sec));
	}
	private static String StrEdit(String time){
		if(time.length()==1)
			return "0"+time;
		else
			return time;
	}
}
