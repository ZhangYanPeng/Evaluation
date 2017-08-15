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

@Entity
@Table( name = "t_exercise")
public class Exercise {
	
	@Id
	@GeneratedValue(generator = "exegenerator")
	@GenericGenerator(name = "exegenerator", strategy = "increment")
	private long id;
	
	private String audio_path;
	private String text;
	private String e_no;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private ExerciseType exerciseType;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn( name = "exercise_id")
	private Set<Question> questions;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Part part;

	public Exercise() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAudio_path() {
		return audio_path;
	}

	public void setAudio_path(String audio_path) {
		this.audio_path = audio_path;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public ExerciseType getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(ExerciseType exerciseType) {
		this.exerciseType = exerciseType;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public String getE_no() {
		return e_no;
	}

	public void setE_no(String e_no) {
		this.e_no = e_no;
	}
	
	
}
