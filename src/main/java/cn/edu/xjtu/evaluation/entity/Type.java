package cn.edu.xjtu.evaluation.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_type")
public class Type {
	@Id
	@GeneratedValue(generator = "typegenerator")
	@GenericGenerator(name = "typegenerator", strategy = "increment")
	private long id;
	
	@Column(length=10000)
	private String direction;
	private String name;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	@JsonIgnore
	private Set<Exercise> exercises;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_id")
	@JsonIgnore
	private Set<Part> Parts;

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

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Set<Part> getParts() {
		return Parts;
	}

	public void setParts(Set<Part> parts) {
		Parts = parts;
	}

	
	
}
