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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_part" )
public class Part {
	@Id
	@GeneratedValue(generator = "partgenerator")
	@GenericGenerator(name = "partgenerator", strategy = "increment")
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Test test;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "part_id")
	private Set<Exercise> exercises;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private ExerciseType exerciseType;
	
	public Set<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Set<Exercise> exercises) {
		this.exercises = exercises;
	}

	private int no;

	public Part() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
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
	
	
}
