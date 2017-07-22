package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Test;

public interface IAnswerService {
	int answer( Answer answer );
	Answer get(long id);
	List getByStudent(Student student);
	List getByTest(Test test);
}
