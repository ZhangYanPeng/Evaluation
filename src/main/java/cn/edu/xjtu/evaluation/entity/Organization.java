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
@Table(name = "t_organization")
public class Organization {
	
	@Id
	@GeneratedValue(generator = "organizationgenerator")
	@GenericGenerator(name = "organizationgenerator", strategy = "increment")
	private long id;
	
	private String className;
	
	@OneToMany( fetch = FetchType.LAZY)
	@JoinColumn( name = "unit_id")
	private Set<Student> students;
	
	@ManyToOne( fetch = FetchType.LAZY)
	private Teacher teacher;

	public Organization() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	
	
	
}