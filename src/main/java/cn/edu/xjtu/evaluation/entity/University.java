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
@Table(name = "t_university")
public class University {
	
	@Id
	@GeneratedValue(generator = "universitygenerator")
	@GenericGenerator(name = "universitygenerator", strategy = "increment")
	private long id;
	
	private String name;
	
	@OneToMany( fetch = FetchType.LAZY)
	@JoinColumn( name = "university_id")
	@JsonIgnore
	private Set<School> schools;
	
	public University() {
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

	public Set<School> getSchools() {
		return schools;
	}

	public void setSchools(Set<School> schools) {
		this.schools = schools;
	}

	

}
