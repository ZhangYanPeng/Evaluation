package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Student;

public interface IStudentService {
	int add(Student student);
	int active(Student student);
	int frozen(Student student);
	Student login(Student student);
	Student get(long id);
	List<Student> getAll();
}
