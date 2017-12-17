package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface ITestService {
	Test add(Test test);
	int remove(Long id);
	Test get(long id);
	List<Test> getAll();
	PageResults<Test> list(Integer page);
	int importTest(Test test);
	int chooseTest(long id);
	int check(Integer type, Long tid, Long uid);
	Test getChoose();
	int finishTest(int type, long tid, long uid, String[] records, String[] reasons);
	List<Part> loadParts(Long id);
	List<Exercise> loadExercises(Long id);
	List<Question> loadQuestions(Long id);
	List<Intervention> loadInterventions(Long id);
	int collect(long id, Integer state);
}
