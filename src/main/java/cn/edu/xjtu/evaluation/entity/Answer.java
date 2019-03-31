package cn.edu.xjtu.evaluation.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_answer" )
public class Answer {
	@Id
	@GeneratedValue( generator = "answergenerator")
	@GenericGenerator( name = "answergenerator" , strategy = "increment" )
	private long id;
	
	private String questionaire;
	private int type;
	
	private Date start_time;
	private Date end_time;
	
	@OneToMany( fetch = FetchType.EAGER)
	@JoinColumn( name = "answer_id" )
	private List<Record> records;
	
	@ManyToOne
	@JsonIgnore
	private Student student;
	
	@ManyToOne
	private Test test;

	public Answer() {
		super();
		type = 1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Record> getRecords() {
		return records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public String getQuestionaire() {
		return questionaire;
	}

	public void setQuestionaire(String questionaire) {
		this.questionaire = questionaire;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
