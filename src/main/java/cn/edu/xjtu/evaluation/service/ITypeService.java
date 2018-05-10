package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Type;

public interface ITypeService {
	public List<Type> getTypes();
	public int add(Type type);
	public int delete(long id);
	public int update(Type type);
	public Type load(long id);
}
