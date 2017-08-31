package cn.edu.xjtu.evaluation.entity;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table( name = "t_part" )
public class Part {
	@Id
	@GeneratedValue(generator = "partgenerator")
	@GenericGenerator(name = "partgenerator", strategy = "increment")
	private long id;
	private int p_no;
	
	@Type(type="text")
	String desription;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Test test;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "part_id")
	private Set<Exercise> exercises;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private ExerciseType exerciseType;
	
	public Set<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Set<Exercise> exercises) {
		this.exercises = exercises;
	}

	public Part() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public ExerciseType getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(ExerciseType exerciseType) {
		this.exerciseType = exerciseType;
	}

	public String getDesription() {
		return desription;
	}

	public void setDesription(String desription) {
		this.desription = desription;
	}

	public int getP_no() {
		return p_no;
	}

	public void setP_no(int p_no) {
		this.p_no = p_no;
	}
	
	
}
