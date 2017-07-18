package cn.edu.xjtu.evaluation.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "t_admin")
public class Admin {
	@Id
	@GeneratedValue(generator = "admingenerator")
	@GenericGenerator(name = "admingenerator", strategy = "increment")
	private long id;
	
	private String username;
	private String password;
	
	
	public Admin() {
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
	
	
}
