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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import cn.edu.xjtu.evaluation.common.Definition;

@Entity
@Table(name = "t_question")
public class Question {

	@Id
	@GeneratedValue(generator = "quegenerator")
	@GenericGenerator(name = "quegenerator", strategy = "increment")
	private long id;

	private String text;
	@Column(length=10000)
	private String options;
	private int answer;
	int q_num;
	
	private Definition.QUESTION_TYPE type;
	private Definition.QUESTION_LEVEL level;

	@OneToOne
	private Audio audio;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id")
	private Set<Intervention> interventions;

	@OneToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinColumn(name = "question_id")
	private Set<Record> records;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Exercise exercise;

	public Question() {
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

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public Set<Intervention> getInterventions() {
		return interventions;
	}

	public void setInterventions(Set<Intervention> interventions) {
		this.interventions = interventions;
	}

	public Exercise getExercise() {
		return exercise;
	}

	public void setExercise(Exercise exercise) {
		this.exercise = exercise;
	}

	public int getQ_num() {
		return q_num;
	}

	public void setQ_num(int q_num) {
		this.q_num = q_num;
	}
	
	public Set<Record> getRecords() {
		return records;
	}

	public void setRecords(Set<Record> records) {
		this.records = records;
	}

	public Audio getAudio() {
		return audio;
	}

	public void setAudio(Audio audio) {
		this.audio = audio;
	}
	
	public Definition.QUESTION_TYPE getType() {
		return type;
	}

	public void setType(Definition.QUESTION_TYPE type) {
		this.type = type;
	}

	public Definition.QUESTION_LEVEL getLevel() {
		return level;
	}

	public void setLevel(Definition.QUESTION_LEVEL level) {
		this.level = level;
	}
}
