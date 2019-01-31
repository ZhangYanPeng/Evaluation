package cn.edu.xjtu.evaluation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.EngClass;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.entity.University;
import cn.edu.xjtu.evaluation.service.IEngClassService;
import cn.edu.xjtu.evaluation.service.IExerciseService;
import cn.edu.xjtu.evaluation.service.ITeacherService;
import cn.edu.xjtu.evaluation.service.IUniversityService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping("/admin/teacher")
public class TeacherController {

	@Autowired
	ITeacherService teacherService;
	@Autowired
	IExerciseService exerciseService;
	@Autowired
	IUniversityService universityService;
	@Autowired
	IEngClassService engClassService;

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
		PageResults<Exercise> pr = exerciseService.getPageList(Integer.valueOf(page), Long.valueOf(type));
		for(Exercise e : pr.getResults()){
			e.setQuestions(null);
		}
		return pr;
	}
	
	@RequestMapping(value = "/get_all_university")
	public @ResponseBody List getAllUniversity() {
		return universityService.getAll();
	}
	
	@RequestMapping(value = "/list_engclass" , method = RequestMethod.POST)
	public @ResponseBody PageResults<EngClass> listEngClass(String page, String university) {
		PageResults<EngClass> tmp =  engClassService.list(Integer.valueOf(page), Long.valueOf(university));
		for( EngClass ec : tmp.getResults()){
			ec.setStu_num(getNum(ec));
		}
		return tmp;
	}

	private int getNum(EngClass ec) {
		// TODO Auto-generated method stub
		return engClassService.getStuNum(ec);
	}
}
