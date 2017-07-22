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
@Table( name = "t_exercise")
public class Exercise {
	
	@Id
	@GeneratedValue(generator = "exegenerator")
	@GenericGenerator(name = "exegenerator", strategy = "increment")
	private long id;
	
	private String audio_path;
	private String text;
	private int type;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn( name = "exercise_id")
	private Set<Question> questions;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name = "exercise_id" )
	private Set<Part> parts;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public Set<Part> getTests() {
		return parts;
	}

	public void setTests(Set<Part> parts) {
		this.parts = parts;
	}
	
	
	
}
