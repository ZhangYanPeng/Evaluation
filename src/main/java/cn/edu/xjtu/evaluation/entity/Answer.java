package cn.edu.xjtu.evaluation.entity;

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
	
	private int type;
	
	@OneToMany( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	@JoinColumn( name = "answer_id" )
	private List<Record> records;
	
	@ManyToOne
	@JsonIgnore
	private Student student;
	
	@ManyToOne
	private Test test;

	public Answer() {
		super();
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
}
