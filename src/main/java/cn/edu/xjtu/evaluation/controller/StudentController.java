package cn.edu.xjtu.evaluation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.entity.EngClass;
import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.University;
import cn.edu.xjtu.evaluation.service.IEngClassService;
import cn.edu.xjtu.evaluation.service.ISchoolService;
import cn.edu.xjtu.evaluation.service.IStudentService;
import cn.edu.xjtu.evaluation.service.IUniversityService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping("/student")
public class StudentController {
	@Autowired
	IStudentService studentService;
	@Autowired
	IUniversityService universityService;
	@Autowired
	ISchoolService schoolService;
	@Autowired
	IEngClassService engClassService;

	@RequestMapping(value = "/login")
	public @ResponseBody Student login(String username, String password) {
		System.out.println(username);
		System.out.println(password);
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
	
	@RequestMapping(value = "/QueSubmit")
	public @ResponseBody int QueSubmit(String userId, String questionaire) {
		Student student = studentService.get(Long.valueOf(userId));
		student.setQuestionaire(questionaire);
		studentService.edit(student);
		return 1;
	}
	
	@RequestMapping(value = "/modifypassword")
	public @ResponseBody int modifyPassword(String id, String password) {
		Student std = studentService.get(Long.valueOf(id));
		std.setPassword(password);
		return studentService.edit(std);
	}
	
	@RequestMapping(value = "/list_university")
	public @ResponseBody List<University> listUniversity() {
		return universityService.getAll();
	}
	
	@RequestMapping(value = "/register")
	public @ResponseBody Student register(String r_username, String r_name, String r_university, String r_password, String r_school, String r_f_engclass, String r_b_engclass) {
		Student student = studentService.getByStuNo(r_username);
		if(student != null){
			student = new Student();
			student.setStudent_no("-1");
			return student;
		}else{
			student = new Student();
			student.setName(r_name);
			student.setStudent_no(r_username);
			student.setUsername(r_username);
			student.setPassword(r_password);
			School school = schoolService.getSchool(r_school);
			student.setSchool(school);
			String engclass_name = r_f_engclass + r_b_engclass;
			EngClass engClass = engClassService.getByName(engclass_name);
			if(engClass == null){
				engClass = new EngClass();
				engClass.setName(engclass_name);
				engClass.setUniversity(universityService.load(Long.valueOf(r_university)));
				engClassService.add(engClass);
			}
			student.setEngClass(engClass);
			studentService.add(student);
			return student;
		}
	}
}
