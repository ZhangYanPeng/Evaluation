package cn.edu.xjtu.evaluation.service;

import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface ITeacherService {
	Teacher login(Teacher teacher);
	int register(Teacher teacher);
	PageResults<Teacher> list(int page);
	Teacher get(Long id);
	int add(Teacher teacher);
	int edit(Teacher teacher);
	int remove(long id);
}
