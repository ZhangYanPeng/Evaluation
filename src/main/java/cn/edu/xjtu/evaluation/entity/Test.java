package cn.edu.xjtu.evaluation.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_test" )
public class Test {
	@Id
	@GeneratedValue( generator = "testgenerator" )
	@GenericGenerator( name = "testgenerator", strategy = "increment")
	private long id;
	
	private String Title;
	private String Description;
	private String remarks;
	
	@OneToMany( fetch = FetchType.LAZY)
	@JoinColumn( name = "test_id" )
	private List<Exercise> exercises;
	
	@OneToMany( fetch = FetchType.LAZY)
	@JoinColumn( name = "test_id")
	private List<Answer> answers;

	public Test() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(List<Exercise> exercises) {
		this.exercises = exercises;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
	
	
}
