package cn.edu.xjtu.evaluation.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class EvaluationResult {
	private String test;
	private String[] OverallPerformance;
	private String[][] score_statictis;
	private String[][] ability;
	private double[][] inter_sta;
	
	public void getTestInfo(Answer answer){
		OverallPerformance = new String[6];
		score_statictis = new String[5][answer.getRecords().size()+2];
		test = answer.getTest().getTitle();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		OverallPerformance[0] = sdf.format(answer.getStart_time());
		long consume = answer.getEnd_time().getTime()-answer.getStart_time().getTime();
		Date date = new Date(consume); 
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		df.setTimeZone(TimeZone.getTimeZone("1"));
		OverallPerformance[1] = df.format(date);
		
		int origin_score = 0;
		int percent_score = 0;
		int evaluation_score = 0;
		double potential = 0;
		
		int[] inter_num = new int[5];
		for( Record rec : answer.getRecords()){
			if( Integer.parseInt((rec.getResult()).split("\\|\\|")[1]) == rec.getQuestion().getAnswer()){
				origin_score += 4;
				evaluation_score += 4;
			}else{
				evaluation_score += 6-(rec.getResult()).split("\\|\\|").length;
			}
			percent_score += 4;
			for(int i=0; i<5; i++){
				score_statictis[i][rec.getNo()] = "";
			}
			score_statictis[(rec.getResult()).split("\\|\\|").length-2][rec.getNo()] = "√";
			inter_num[(rec.getResult()).split("\\|\\|").length-2] ++;
		}
		
		potential = (2*evaluation_score - origin_score)/64.0;
		
		OverallPerformance[2] = ((Integer)origin_score).toString()+"/64";
		OverallPerformance[3] = String.format("%.1f", origin_score / 64.00 * 100)+"/100";
		OverallPerformance[4] = ((Integer)evaluation_score).toString();
		OverallPerformance[5] = String.format("%.1f",potential);
		
		for(int i=0; i<5; i++){
			score_statictis[i][answer.getRecords().size()] = ((Integer)inter_num[i]).toString();
			score_statictis[i][answer.getRecords().size()+1] = ((Integer)(inter_num[i] * (4-i))).toString();
		}
		
		//听力技能分析
		ability = new String[5][3];
		int[] all_inter_freq = new int[5];
		int[] avg_inter_freq = new int[5];
		for(int i=0; i<5; i++){
			all_inter_freq[i] = 0;
			avg_inter_freq[i] = 0;
			
			for(int j=0; j<3; j++)
				ability[i][j] = "";
		}
		for( Record rec : answer.getRecords()){
			int ab_t;
			switch(rec.getQuestion().getType()){
				case presentation: ab_t=0; break;
				case grammar: ab_t=1; break;
				case comprehension: ab_t=2; break;
				case details: ab_t=3; break;
				case inference: ab_t=4; break;
				default : ab_t=0; break;
			}
			if(ability[ab_t][0] == "")
				ability[ab_t][0] = ((Integer)(rec.getNo()+1)).toString();
			else
				ability[ab_t][0] += "、" + ((Integer)(rec.getNo()+1)).toString();
			all_inter_freq[ab_t] += (rec.getResult()).split("\\|\\|").length - 2;
			avg_inter_freq[ab_t] ++;
		}
		
		for(int i=0; i<5; i++){
			ability[i][1] = ((Integer)all_inter_freq[i]).toString();
			if(avg_inter_freq[i] == 0)
				ability[i][2] = "0";
			else
				ability[i][2] = String.format("%.1f", ((double)all_inter_freq[i]/(double)avg_inter_freq[i]));
		}
		
		inter_sta = new double[2][5];
		for( int i=0; i<5; i++){
			inter_sta[0][i] = all_inter_freq[i];
		}
	}
	
	public void InitInterNum(List<Answer> answers){
		int[] inter_num = new int[5];
		for(Answer ans : answers){
			for( Record rec : ans.getRecords()){
				int ab_t;
				switch(rec.getQuestion().getType()){
					case presentation: ab_t=0; break;
					case grammar: ab_t=1; break;
					case comprehension: ab_t=2; break;
					case details: ab_t=3; break;
					case inference: ab_t=4; break;
					default : ab_t=0; break;
				}
				inter_num[ab_t] += (rec.getResult()).split("\\|\\|").length - 2;
			}
		}
		for( int i=0; i<5; i++){
			inter_sta[1][i] = inter_num[i] / answers.size();
		}
	}
	
	public String getTest() {
		return test;
	}
	
	public void setTest(String test) {
		this.test = test;
	}
	
	public String[] getOverallPerformance() {
		return OverallPerformance;
	}
	
	public void setOverallPerformance(String[] overallPerformance) {
		OverallPerformance = overallPerformance;
	}
	
	public String[][] getScore_statictis() {
		return score_statictis;
	}
	
	public void setScore_statictis(String[][] score_statictis) {
		this.score_statictis = score_statictis;
	}

	public String[][] getAbility() {
		return ability;
	}

	public void setAbility(String[][] ability) {
		this.ability = ability;
	}

	public double[][] getInter_sta() {
		return inter_sta;
	}

	public void setInter_sta(double[][] inter_sta) {
		this.inter_sta = inter_sta;
	}
	
}
