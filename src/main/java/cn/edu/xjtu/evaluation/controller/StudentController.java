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
		if( student == null ) {
			student = new Student();
			student.setId(-1);
		}
		return student;
	}
}
