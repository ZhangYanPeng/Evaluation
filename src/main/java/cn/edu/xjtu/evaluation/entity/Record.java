package cn.edu.xjtu.evaluation.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_record" )
public class Record {
	@Id
	@GeneratedValue( generator = "recgenerator")
	@GenericGenerator( name = "recgenerator" , strategy = "increment" )
	private long id;
	
	private String result;
	private String reason;
	private int no;
	private long timecon;
	private long timereact;

	@ManyToOne( fetch = FetchType.LAZY )
	@JsonIgnore
	private Answer answer;
	
	@ManyToOne( fetch = FetchType.LAZY )
	private Question question;
	
	public long getTimecon() {
		return timecon;
	}

	public void setTimecon(long timecon) {
		this.timecon = timecon;
	}
	
	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public Record() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getResult() {
		return result.replace("\"", "");
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public long getTimereact() {
		return timereact;
	}

	public void setTimereact(long timereact) {
		this.timereact = timereact;
	}
	
	
}
