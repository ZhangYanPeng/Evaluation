package cn.edu.xjtu.evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.service.IExerciseService;
import cn.edu.xjtu.evaluation.service.ITeacherService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping("/teacher")
public class TeacherController {

	@Autowired
	ITeacherService teacherService;
	@Autowired
	IExerciseService exerciseService;

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
	public @ResponseBody Teacher register(String username, String password, String name) {
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setPassword(password);
		teacher.setUsername(username);
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
	
	@RequestMapping(value = "/get_exercises", method = RequestMethod.POST)
	public @ResponseBody PageResults<Exercise> getExercises(String page, String type) {
		return exerciseService.getPageList(Integer.valueOf(page), type);
	}
	
	
}
