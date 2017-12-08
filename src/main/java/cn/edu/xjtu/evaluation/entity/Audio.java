package cn.edu.xjtu.evaluation.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_audio" )
public class Audio {
	@Id
	@GeneratedValue( generator = "audiogenerator")
	@GenericGenerator( name = "audiogenerator" , strategy = "native" )
	private long id;
	
	private String path;
	private int type;
	
	@OneToOne
	@JsonIgnore
	private Exercise exercise;
	
	@OneToOne
	@JsonIgnore
	private Intervention intervention;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public Intervention getIntervention() {
		return intervention;
	}

	public void setIntervention(Intervention intervention) {
		this.intervention = intervention;
	}
	
	
	
}
