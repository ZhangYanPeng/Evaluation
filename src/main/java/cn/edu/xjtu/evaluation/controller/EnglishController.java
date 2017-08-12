package cn.edu.xjtu.evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.service.ITeacherService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping("/admin")
public class EnglishController {
	
	@Autowired
	ITeacherService teacherService;
	
	//teacher manage
	@RequestMapping(value = "/list_teacher")
	public @ResponseBody PageResults<Teacher> listAdmin(String page) {
		return teacherService.list(Integer.valueOf(page));
	}
	
	@RequestMapping(value = "/load_teacher")
	public @ResponseBody Teacher listTeacher(String id) {
		return teacherService.get(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/edit_teacher")
	public @ResponseBody int editTeacher(String id, String name, String username, String password) {
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setUsername(username);
		teacher.setPassword(password);
		long tid = Long.valueOf(id);
		if( tid == (long)-1 ){
			return teacherService.add(teacher);
		}else{
			teacher.setId(tid);
			return teacherService.edit(teacher);
		}
		
	}
}
