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

@Entity
@Table( name = "t_part" )
public class Part {
	@Id
	@GeneratedValue(generator = "partgenerator")
	@GenericGenerator(name = "partgenerator", strategy = "increment")
	private long id;
	private int p_no;
	
	@Column(length=10000)
	String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Test test;

	@OneToOne
	private Audio directAudio;
	private String directStr;
	
	@OneToMany( fetch = FetchType.EAGER )
	@JoinColumn( name = "part_id" )
	private Set<PartExer> partExers;

	public Set<PartExer> getPartExers() {
		return partExers;
	}

	public void setPartExers(Set<PartExer> partExers) {
		this.partExers = partExers;
	}

	public Part() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Test getTest() {
		return test;
	}

	public void setTest(Test test) {
		this.test = test;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getP_no() {
		return p_no;
	}

	public void setP_no(int p_no) {
		this.p_no = p_no;
	}

	public Audio getDirectAudio() {
		return directAudio;
	}

	public void setDirectAudio(Audio directAudio) {
		this.directAudio = directAudio;
	}

	public String getDirectStr() {
		return directStr;
	}

	public void setDirectStr(String directStr) {
		this.directStr = directStr;
	}
}
