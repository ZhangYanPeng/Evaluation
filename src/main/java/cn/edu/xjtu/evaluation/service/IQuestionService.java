package cn.edu.xjtu.evaluation.service;

import java.util.List;
import java.util.Set;

import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Question;

public interface IQuestionService {
	int add(Exercise exercise, Question question);
	int remove(Question question);
	int edit(Question question);
}
