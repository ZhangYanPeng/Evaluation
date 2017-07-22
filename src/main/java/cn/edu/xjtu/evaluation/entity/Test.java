package cn.edu.xjtu.evaluation.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_test" )
public class Test {
	@Id
	@GeneratedValue( generator = "testgenerator" )
	@GenericGenerator( name = "testgenerator", strategy = "increment")
	private long id;
	
	private String Title;
	private String Description;
	private String remarks;
	
	@OneToMany( fetch = FetchType.EAGER , cascade = CascadeType.ALL )
	@JoinColumn( name = "test_id" )
	private Set<Part> parts;
	
	@OneToMany( fetch = FetchType.LAZY)
	@JoinColumn( name = "test_id")
	private Set<Answer> answers;

	public Test() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Set<Part> getParts() {
		return parts;
	}

	public void setParts(Set<Part> parts) {
		this.parts = parts;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(Set<Answer> answers) {
		this.answers = answers;
	}
	
	
}
