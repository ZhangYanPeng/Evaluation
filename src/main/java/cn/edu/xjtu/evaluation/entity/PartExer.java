package cn.edu.xjtu.evaluation.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_part_exer" )
public class PartExer {
	@Id
	@GeneratedValue(generator = "partexergenerator")
	@GenericGenerator(name = "partexergenerator", strategy = "increment")
	private long id;
	private int e_no;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Part part;
	
	@ManyToOne
	private Exercise exercise;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getE_no() {
		return e_no;
	}

	public void setE_no(int e_no) {
		this.e_no = e_no;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}
	
	
	
	
}