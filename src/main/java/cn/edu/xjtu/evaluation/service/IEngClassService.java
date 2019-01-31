package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.EngClass;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface IEngClassService {
	public PageResults<EngClass> list(int page, long university);
	public EngClass get(long id);
	public int add(EngClass engClass);
	public int remove(long id);
	public int edit(EngClass engClass);
	public int importStudent(List<String[]> s_info, long id);
	public int getStuNum(EngClass ec);
	public List<EngClass> listAll();
	public EngClass getByName(String engclass_name);
	public List<String> getInfo(Long eid);
	public String[][] getTestInfo(Long engid, Integer testno);
	public String[][] getAbilityInfo(Long engid, Integer testno);
}
