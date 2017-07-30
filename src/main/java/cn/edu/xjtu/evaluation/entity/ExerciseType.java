package cn.edu.xjtu.evaluation.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_exercise_type")
public class ExerciseType {
	
	@Id
	@GeneratedValue(generator = "exercisegenerator")
	@GenericGenerator(name = "exercisegenerator", strategy = "increment")
	private long id;
	
	private String decription;
	
	@OneToMany( fetch = FetchType.LAZY )
	@JoinColumn( name = "exercise_type_id")
	private Set<Part> parts;
	
	@OneToMany( fetch = FetchType.LAZY )
	@JoinColumn( name = "exercise_type_id")
	private Set<Exercise> exercises;

	public ExerciseType() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}

	public Set<Part> getParts() {
		return parts;
	}

	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}

	public Set<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Set<Exercise> exercises) {
		this.exercises = exercises;
	}
	
	
	
}
