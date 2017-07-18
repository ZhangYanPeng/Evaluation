package cn.edu.xjtu.evaluation.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_intervention" )
public class Intervention {
	@Id
	@GeneratedValue(generator = "interventiongenerator")
	@GenericGenerator(name = "interventiongenerator", strategy = "increment")
	private long id;
	
	private String text;
	private String audio_path;
	
	private double deScore;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Question question;
	
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

	public String getAudio_path() {
		return audio_path;
	}

	public void setAudio_path(String audio_path) {
		this.audio_path = audio_path;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public double getDeScore() {
		return deScore;
	}

	public void setDeScore(double deScore) {
		this.deScore = deScore;
	}
	
	
}
