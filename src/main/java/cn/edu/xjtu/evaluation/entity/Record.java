package cn.edu.xjtu.evaluation.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_record" )
public class Record {
	@Id
	@GeneratedValue( generator = "recgenerator")
	@GenericGenerator( name = "recgenerator" , strategy = "increment" )
	private long id;
	
	private String answers;
	private String reason;
	
	@ManyToOne( fetch = FetchType.LAZY )
	private Answer answer;
	
	public Record() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
