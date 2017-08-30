package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface ITestService {
	int add(Test test);
	int remove(Long id);
	Test get(long id);
	List<Test> getAll();
	PageResults<Test> list(Integer page);
	int importTest(Test test);
	int chooseTest(long id);
	int check(Integer type, Long tid, Long uid);
}
