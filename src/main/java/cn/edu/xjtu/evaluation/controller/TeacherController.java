package cn.edu.xjtu.evaluation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.service.ITeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	ITeacherService teacherService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Teacher login(String username, String password) {
		Teacher teacher = new Teacher();
		teacher.setUsername(username);
		teacher.setPassword(password);
		teacher = teacherService.login(teacher);
		if (teacher == null) {
			teacher = new Teacher();
			teacher.setId(-1);
		}
		return teacher;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody Teacher register(String username, String password, String gender, String name, String school,
			String major, String title) {
		Teacher teacher = new Teacher();
		System.out.println(name);
		teacher.setGender(gender);
		teacher.setName(name);
		teacher.setPassword(password);
		teacher.setUsername(username);
		teacher.setTitle(title);
		teacher.setSchool(school);
		teacher.setMajor(major);
		teacher.setUnits(null);
		int status = teacherService.register(teacher);
		if (status == 1) {
			teacher = teacherService.login(teacher);
			return teacher;
		} else {
			teacher = new Teacher();
			teacher.setId(-1);
			return teacher;
		}
	}
}
