package cn.edu.xjtu.evaluation.entity;

import java.util.List;

public class OverallReport {
	private String[] test_name;
	private String[][] test_result;
	private String[][] inter_freq;
	private int[][] score;
	private double[][] inter_percent;
	
	public void init(List<Answer> answers, int[] max_scores){
		test_name = new String[answers.size()];
		test_result = new String[4][answers.size()*2];
		inter_freq = new String[6][answers.size()+1];
		score = new int[2][answers.size()];
		inter_percent = new double[answers.size()][5];
		
		int[] a_i_t = new int[5];
		for(int j=0; j<5; j++){
			a_i_t[j] = 0;
		}
		
		int i = 0;
		for(Answer ans : answers){
			test_name[i] = ans.getTest().getTitle();
			
			//成绩
			test_result[0][2*i] = "原始成绩";
			test_result[0][2*i+1] = "动态评估成绩";
			test_result[2][2*i] = "-";
			test_result[3][2*i] = "-";
			
			int origin_score = 0;
			int evaluation_score = 0;
			int inter_num = 0;
			double potential = 0;
			for(Record rec : ans.getRecords()){
				if( (rec.getResult()).split("\\|\\|").length == 2 )
					origin_score += 4;
				evaluation_score += 6 - (rec.getResult()).split("\\|\\|").length;
				inter_num += (rec.getResult()).split("\\|\\|").length - 2;
				potential = ((double)(2*evaluation_score-origin_score)) / max_scores[i];
			}

			test_result[1][2*i] = ((Integer)origin_score).toString();

			test_result[1][2*i+1] = ((Integer)evaluation_score).toString();
			test_result[2][2*i+1] = ((Integer)inter_num).toString();
			test_result[3][2*i+1] = ((Double)potential).toString();
			score[0][i] = origin_score;
			score[1][i] = evaluation_score;
			
			//提示
			int[] i_t = new int[5];
			for(int j=0; j <5; j++)
				i_t[j] = 0;
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
				i_t[ab_t] += (rec.getResult()).split("\\|\\|").length - 2;
			}
			inter_freq[0][i] = ans.getTest().getTitle();
			for(int j=0; j <5; j++){
				inter_freq[j+1][i] = ((Integer)i_t[j]).toString();
				a_i_t[j] += i_t[j];
			}

			i++;
		}
		inter_freq[0][answers.size()] = "总提示频率";
		for(int j=0; j <5; j++){
			inter_freq[j+1][answers.size()] = ((Integer)a_i_t[j]).toString();
		}
		for(int j=0; j<5; j++){
			for(int k=0; k<answers.size(); k++){
				if(Integer.valueOf(inter_freq[j+1][answers.size()])  == 0)
					inter_percent[k][j] = 0;
				else
					inter_percent[k][j] = 100 * Double.valueOf(inter_freq[j+1][k]) / Double.valueOf(inter_freq[j+1][answers.size()]);
			}
		}
	}
	
	public String[] getTest_name() {
		return test_name;
	}
	
	public void setTest_name(String[] test_name) {
		this.test_name = test_name;
	}
	
	public String[][] getTest_result() {
		return test_result;
	}
	
	public void setTest_result(String[][] test_result) {
		this.test_result = test_result;
	}
	
	public String[][] getInter_freq() {
		return inter_freq;
	}
	
	public void setInter_freq(String[][] inter_freq) {
		this.inter_freq = inter_freq;
	}
	
	public int[][] getScore() {
		return score;
	}
	
	public void setScore(int[][] score) {
		this.score = score;
	}

	public double[][] getInter_percent() {
		return inter_percent;
	}

	public void setInter_percent(double[][] inter_percent) {
		this.inter_percent = inter_percent;
	}
}
