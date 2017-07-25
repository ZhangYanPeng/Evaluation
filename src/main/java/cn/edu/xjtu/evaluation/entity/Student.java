package cn.edu.xjtu.evaluation.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	private String school;
	
	private int english_level;
	private int father_level;
	private int mother_level;
	
	private int status;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id")
	public List<Answer> answers;
	
	
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
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

	public int getFather_level() {
		return father_level;
	}

	public void setFather_level(int father_level) {
		this.father_level = father_level;
	}

	public int getMother_level() {
		return mother_level;
	}

	public void setMother_level(int mother_level) {
		this.mother_level = mother_level;
	}
	
	
	
}
