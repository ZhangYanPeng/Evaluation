package cn.edu.xjtu.evaluation.service;

import cn.edu.xjtu.evaluation.entity.Teacher;

public interface ITeacherService {
	Teacher login(Teacher teacher);
	int register(Teacher teacher);

}
