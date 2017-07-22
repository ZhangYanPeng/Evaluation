package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Test;

public interface ITestService {
	int add(Test test);
	int remove(Test test);
	Test get(long id);
	List<Test> getAll();
}
