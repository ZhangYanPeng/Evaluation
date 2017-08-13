package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface IStudentService {
	int add(Student student);
	int active(Student student);
	int frozen(Student student);
	Student login(Student student);
	Student get(long id);
	List<Student> getAll();
	PageResults<Student> list(String str, int page);
	int delete(long id);
	int edit(Student student);
}
