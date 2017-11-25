package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface IExerciseService {
	int add(Exercise exercise);
	int remove(long id);
	int edit(Exercise exercise);
	Exercise get(long id);
	List<Exercise> list();
	PageResults<Exercise> getPageList(Integer page, long type);
	Exercise create();
}
