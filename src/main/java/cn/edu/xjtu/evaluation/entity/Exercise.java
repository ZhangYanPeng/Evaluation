package cn.edu.xjtu.evaluation.entity;

import java.util.Set;

import javax.persistence.Column;
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
	@GenericGenerator(name = "exegenerator", strategy = "native")
	private long id;
	
	private String audio_path;
	private String text;
	@Column(length=10000)
	private String description;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn( name = "exercise_id")
	private Set<Question> questions;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Type type;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	
}
