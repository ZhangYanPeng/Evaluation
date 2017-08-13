package cn.edu.xjtu.evaluation.service;

import cn.edu.xjtu.evaluation.entity.EngClass;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface IEngClassService {
	public PageResults<EngClass> list(int page);
	public EngClass get(long id);
	public int add(EngClass engClass);
	public int remove(long id);
	public int edit(EngClass engClass);
}
