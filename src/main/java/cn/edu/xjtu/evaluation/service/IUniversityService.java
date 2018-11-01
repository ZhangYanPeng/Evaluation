package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.University;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface IUniversityService {
	public int add(University university); 
	public int delete(long id);
	public PageResults<University> list(int page);
	public int edit(University university);
	public University load(long id);
	public int update(University university);
	public List<University> getAll();
}
