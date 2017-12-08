package cn.edu.xjtu.evaluation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_intervention" )
public class Intervention {
	@Id
	@GeneratedValue(generator = "interventiongenerator")
	@GenericGenerator(name = "interventiongenerator", strategy = "increment")
	private long id;
	
	@Column(length=10000)
	private String text;
	private int level;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Question question;
	
	@OneToOne
	private Audio audio;
	
	public Intervention() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Audio getAudio() {
		return audio;
	}

	public void setAudio(Audio audio) {
		this.audio = audio;
	}
	
	
}
