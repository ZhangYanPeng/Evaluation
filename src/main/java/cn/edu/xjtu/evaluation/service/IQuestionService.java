package cn.edu.xjtu.evaluation.service;

import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Question;

public interface IQuestionService {
	int add(Exercise exercise, Question question);
	int remove(Question question);
	int edit(Question question);
	int add(long id);
	Question load(long id);
}
