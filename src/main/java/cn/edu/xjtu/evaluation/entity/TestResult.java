package cn.edu.xjtu.evaluation.entity;

import java.util.List;

public class TestResult {
	private String stu_no;
	private String name;
	private String test;
	private int score;
	private double rank;
	private int right_sum;
	private double[] percents;
	private String[][] results;
	
	public TestResult(){
	};
	
	public void calculate(Answer answer, List<Answer> answers){
		right_sum = 0;
		percents = new double[]{0, 0, 0, 0, 0, 0};
		results = new String[3][answer.getRecords().size()];
		for( Record rec : answer.getRecords()){
			if( Integer.parseInt((rec.getResult()).split("\\|\\|")[1]) == rec.getQuestion().getAnswer()){
				right_sum ++;
				score += 4;
				switch(rec.getQuestion().getLevel()){
					case easy : percents[0] ++; break;
					case normal : percents[1] ++; break;
					case hard : percents[2] ++; break;
					default : break;
				}
			}
			results[1][rec.getNo()] = rec.getResult().split("\\|\\|")[1];
			results[2][rec.getNo()] = ((Integer)rec.getQuestion().getAnswer()).toString();
			switch(rec.getQuestion().getLevel()){
				case easy : results[0][rec.getNo()] = "易"; break;
				case normal : results[0][rec.getNo()] = "中"; break;
				case hard : results[0][rec.getNo()] = "难"; break;
				default : break;
			}
		}
		double total_sum_t = percents[0] + percents[1] + percents[2] ;
		percents[0] /= total_sum_t;
		percents[1] /= total_sum_t;
		percents[2] /= total_sum_t;
		
		int rank_t = 0;
		for( Answer ans : answers){
			for( Record rec : ans.getRecords()){
				int right_t = 0;
				if( Integer.parseInt(rec.getResult().split("\\|\\|")[1]) == rec.getQuestion().getAnswer()){
					right_t ++;
					switch(rec.getQuestion().getLevel()){
						case easy : percents[3] ++; break;
						case normal : percents[4] ++; break;
						case hard : percents[5] ++; break;
						default : break;
					}
				}
				if(right_t> right_sum){
					rank_t ++;
				}
			}
		}
		rank = ((double)rank_t) / answers.size()*100+1;

		total_sum_t = percents[3] + percents[4] + percents[5] ;
		percents[3] /= total_sum_t;
		percents[4] /= total_sum_t;
		percents[5] /= total_sum_t;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public String getStu_no() {
		return stu_no;
	}
	public void setStu_no(String stu_no) {
		this.stu_no = stu_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTest() {
		return test;
	}
	public void setTest(String test) {
		this.test = test;
	}
	public double getRank() {
		return rank;
	}
	public void setRank(double rank) {
		this.rank = rank;
	}
	public int getRight_sum() {
		return right_sum;
	}
	public void setRight_sum(int right_sum) {
		this.right_sum = right_sum;
	}
	public double[] getPercents() {
		return percents;
	}
	public void setPercents(double[] percents) {
		this.percents = percents;
	}
	public String[][] getResults() {
		return results;
	}
	public void setResults(String[][] results) {
		this.results = results;
	}
	
	
}
