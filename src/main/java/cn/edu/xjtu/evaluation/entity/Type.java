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
@Table( name = "t_type")
public class Type {
	@Id
	@GeneratedValue(generator = "typegenerator")
	@GenericGenerator(name = "typegenerator", strategy = "increment")
	private long id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private Set<Exercise> exercises;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Exercise> getExercises() {
		return exercises;
	}

	public void setExercises(Set<Exercise> exercises) {
		this.exercises = exercises;
	}
	
	
}
