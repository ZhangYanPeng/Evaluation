package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface ISchoolService {
	public int add(School school); 
	public int delete(long id);
	public PageResults<School> list(int page);
	public int edit(School school);
	public School load(Long id);
	public List<School> getAll();
}
