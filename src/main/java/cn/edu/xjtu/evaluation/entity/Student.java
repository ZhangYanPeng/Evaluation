package cn.edu.xjtu.evaluation.entity;

import java.util.List;

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
@Table(name = "t_student")
public class Student {

	@Id
	@GeneratedValue(generator = "studentgenerator")
	@GenericGenerator(name = "studentgenerator", strategy = "increment")
	private long id;
	
	private String name;
	private int gender;
	private String grade;
	private int age;
	private String username;
	private String student_no;
	private String password;
	private String major;
	private String questionaire;
	private String questionaireBF;
	private String questionaireAF;
	
	private int english_level;// -1：未知 0：未通过四级 1：通过4级 2：通过六级 
	
	private int status;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id")
	@JsonIgnore
	private List<Answer> answers;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private EngClass engClass;
	@ManyToOne(fetch = FetchType.EAGER)
	private School school;
	
	public Student() {
		super();
	}

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

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getStudent_no() {
		return student_no;
	}

	public void setStudent_no(String student_no) {
		this.student_no = student_no;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}
	
	@JsonIgnore
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEnglish_level() {
		return english_level;
	}

	public void setEnglish_level(int english_level) {
		this.english_level = english_level;
	}

	public EngClass getEngClass() {
		return engClass;
	}

	public void setEngClass(EngClass engClass) {
		this.engClass = engClass;
	}

	public String getQuestionaire() {
		return questionaire;
	}

	public void setQuestionaire(String questionaire) {
		this.questionaire = questionaire;
	}

	public School getSchool() {
		return school;
	}

	public void setSchool(School school) {
		this.school = school;
	}

	public String getQuestionaireBF() {
		return questionaireBF;
	}

	public void setQuestionaireBF(String questionaireBF) {
		this.questionaireBF = questionaireBF;
	}

	public String getQuestionaireAF() {
		return questionaireAF;
	}

	public void setQuestionaireAF(String questionaireAF) {
		this.questionaireAF = questionaireAF;
	}
	
	
}
