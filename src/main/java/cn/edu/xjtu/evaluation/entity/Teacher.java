package cn.edu.xjtu.evaluation.entity;

import java.util.Set;

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
@Table( name = "t_teacher")
public class Teacher {
	
	@Id
	@GeneratedValue(generator = "teachergenerator")
	@GenericGenerator(name = "teachergenerator", strategy = "increment")
	private long id;
	
	private String username;
	private String password;
	private String name;
	
	@JsonIgnore
	@OneToMany( fetch = FetchType.LAZY)
	@JoinColumn( name = "engclass_id")
	private Set<EngClass> engClasses;

	public Teacher() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<EngClass> getEngClasses() {
		return engClasses;
	}

	public void setEngClasses(Set<EngClass> engClasses) {
		this.engClasses = engClasses;
	}
	
	
}
