package cn.edu.xjtu.evaluation.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
		if( teacher == null ) {
			teacher = new Teacher();
			teacher.setId(-1);
		}
		return teacher;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody Teacher register(@RequestBody Map map) {
		Teacher teacher = new Teacher();
		teacher.setAge((String)map.get("age"));
		teacher.setGender((String)map.get("gender"));
		teacher.setName((String)map.get("name"));
		teacher.setPassword((String)map.get("password"));
		teacher.setUsername((String)map.get("username"));
		teacher.setTitle((String)map.get("title"));
		teacher.setUnits(null);
		int status = teacherService.register(teacher);
		if( status == 1 ){
			teacher = teacherService.login(teacher);
			return teacher;
		}else{
			return null;
		}
	}
}
