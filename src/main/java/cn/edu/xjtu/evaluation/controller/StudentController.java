package cn.edu.xjtu.evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.service.IStudentService;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	IStudentService studentService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Student login(String username, String password) {
		Student student = new Student();
		student.setUsername(username);
		student.setPassword(password);
		student = studentService.login(student);
		if (student == null) {
			student = new Student();
			student.setId(-1);
		}
		return student;
	}
	
	@RequestMapping(value = "/QueSubmit", method = RequestMethod.POST)
	public @ResponseBody int QueSubmit(String userId, String questionaire) {
		Student student = studentService.get(Long.valueOf(userId));
		student.setQuestionaire(questionaire);
		studentService.edit(student);
		return 1;
	}
	
	@RequestMapping(value = "/modifypassword", method = RequestMethod.POST)
	public @ResponseBody int modifyPassword(String id, String password) {
		Student std = studentService.get(Long.valueOf(id));
		std.setPassword(password);
		return studentService.edit(std);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody Student register(String username, String password, String gender, String name, String school,
			String major, String grade, String student_no, String english_level) {
		Student student = new Student();
		student.setUsername(username);
		student.setPassword(password);
		student.setGender(Integer.valueOf(gender));
		student.setName(name);
		student.setMajor(major);
		student.setGrade(grade);
		student.setStudent_no(student_no);
		student.setEnglish_level(Integer.valueOf(english_level));
		student.setStatus(1);
		int status = studentService.add(student);
		if (status == 1) {
			student = studentService.login(student);
			return student;
		} else {
			student = new Student();
			student.setId(-1);
			return student;
		}
	}
}
