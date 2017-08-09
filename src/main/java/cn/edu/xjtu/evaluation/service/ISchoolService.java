package cn.edu.xjtu.evaluation.service;

import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface ISchoolService {
	public int add(School school); 
	public int delete(long id);
	public PageResults<School> list(int page, long unviersity);
	public int edit(School school);
	public School load(Long id);
}
