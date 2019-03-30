package cn.edu.xjtu.evaluation.entity;

public class EngClassResult {
	String student_no;
	String student_name;
	double score;
	String score_str;
	int eval_score;
	double potential;
	long time_consume_val;
	String time_consume;
	String ave_time_consume;
	long ave_time_consume_val;
	String ave_inter_time;
	long ave_inter_time_val;
	
	public EngClassResult(Student student, Answer answer) {
		// TODO Auto-generated constructor stub
		student_name = student.getName();
		student_no = student.getStudent_no();
		if(answer == null){
			time_consume = "-";
			ave_time_consume = "-";
			ave_inter_time = "-";
			return;
		}
		score = 0;
		eval_score = 0;
		time_consume_val = answer.getEnd_time().getTime() - answer.getStart_time().getTime();
		ave_inter_time_val = 0;
		ave_time_consume_val = 0;
		int inter_sum = 0;
		for(Record rec : answer.getRecords()){
			if( rec.getResult().split("\\|\\|").length == 2)
				score += 4;
			eval_score += 6 - rec.getResult().split("\\|\\|").length;
			ave_inter_time_val += rec.getTimecon();
			ave_time_consume_val += rec.getTimereact();
			if(rec.getTimecon() > 0)
				inter_sum++;
		}
		score_str = String.format("%.1f", score);
		potential = (2*eval_score - score) / (answer.getRecords().size()*4.0);
		ave_inter_time_val = ave_inter_time_val/ inter_sum;
		ave_time_consume_val = ave_time_consume_val / answer.getRecords().size();
		
		time_consume = TranTimeStr(time_consume_val);
		ave_time_consume = TranTimeStr(ave_time_consume_val);
		ave_inter_time = TranTimeStr(ave_inter_time_val);
	}
	
	private String TranTimeStr(long time){
		time = time / 1000;
		long sec = time % 60;
		time = time / 60;
		long min = time % 60;
		long hour = time / 60;
		return StrEdit(String.valueOf(hour))+":"+StrEdit(String.valueOf(min))+":"
				+StrEdit(String.valueOf(sec));
	}
	
	private String StrEdit(String time){
		if(time.length()==1)
			return "0"+time;
		else
			return time;
	}
	
	public String getStudent_no() {
		return student_no;
	}
	public void setStudent_no(String student_no) {
		this.student_no = student_no;
	}
	public String getStudent_name() {
		return student_name;
	}
	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}
	
	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getScore_str() {
		return score_str;
	}

	public void setScore_str(String score_str) {
		this.score_str = score_str;
	}

	public int getEval_score() {
		return eval_score;
	}
	public void setEval_score(int eval_score) {
		this.eval_score = eval_score;
	}
	public double getPotential() {
		return potential;
	}
	public void setPotential(double potential) {
		this.potential = potential;
	}
	public long getTime_consume_val() {
		return time_consume_val;
	}
	public void setTime_consume_val(long time_consume_val) {
		this.time_consume_val = time_consume_val;
	}
	public String getTime_consume() {
		return time_consume;
	}
	public void setTime_consume(String time_consume) {
		this.time_consume = time_consume;
	}
	public String getAve_time_consume() {
		return ave_time_consume;
	}
	public void setAve_time_consume(String ave_time_consume) {
		this.ave_time_consume = ave_time_consume;
	}
	public long getAve_time_consume_val() {
		return ave_time_consume_val;
	}
	public void setAve_time_consume_val(long ave_time_consume_val) {
		this.ave_time_consume_val = ave_time_consume_val;
	}
	public String getAve_inter_time() {
		return ave_inter_time;
	}
	public void setAve_inter_time(String ave_inter_time) {
		this.ave_inter_time = ave_inter_time;
	}
	public long getAve_inter_time_val() {
		return ave_inter_time_val;
	}
	public void setAve_inter_time_val(long ave_inter_time_val) {
		this.ave_inter_time_val = ave_inter_time_val;
	}
	
	
}
