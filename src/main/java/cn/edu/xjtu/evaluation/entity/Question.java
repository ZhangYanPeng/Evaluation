package cn.edu.xjtu.evaluation.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
@Table( name = "t_question")
public class Question {

	@Id
	@GeneratedValue(generator = "quegenerator")
	@GenericGenerator(name = "quegenerator", strategy = "increment")
	private long id;
	
	private String text;
	private int type;
	private int option_count;
	private String options;
	private int answer;
	private Double score;
	int q_num;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn( name = "question_id" )
	private Set<Intervention> interventions;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn( name = "question_id" )
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getOption_count() {
		return option_count;
	}
	public void setOption_count(int option_count) {
		this.option_count = option_count;
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

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
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
	
	
}
