package cn.edu.xjtu.evaluation.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.xjtu.evaluation.entity.EngClass;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.IEngClassService;
import cn.edu.xjtu.evaluation.service.IOrganizationService;
import cn.edu.xjtu.evaluation.service.IStudentService;
import cn.edu.xjtu.evaluation.service.ITeacherService;
import cn.edu.xjtu.evaluation.service.ITestService;
import cn.edu.xjtu.evaluation.service.IUniversityService;
import cn.edu.xjtu.evaluation.support.DealExcel;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping("/admin")
public class EnglishController {

	@Autowired
	ITeacherService teacherService;
	@Autowired
	IEngClassService engClassService;
	@Autowired
	IUniversityService universityService;
	@Autowired
	IStudentService studentService;
	@Autowired
	IOrganizationService organizationService;
	@Autowired
	ITestService testService;

	private int getNum(EngClass ec) {
		// TODO Auto-generated method stub
		return engClassService.getStuNum(ec);
	}
	
	// teacher manage
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
		if (tid == (long) -1) {
			return teacherService.add(teacher);
		} else {
			teacher.setId(tid);
			return teacherService.edit(teacher);
		}

	}

	@RequestMapping(value = "/delete_teacher")
	public @ResponseBody int removeTeacher(String id) {
		return teacherService.remove(Long.valueOf(id));
	}

	// english class manage
	@RequestMapping(value = "/list_engclass" , method = RequestMethod.POST)
	public @ResponseBody PageResults<EngClass> listEngClass(String page) {
		PageResults<EngClass> tmp =  engClassService.list(Integer.valueOf(page));
		for( EngClass ec : tmp.getResults()){
			ec.setStu_num(getNum(ec));
		}
		return tmp;
	}

	@RequestMapping(value = "/add_engclass")
	public @ResponseBody int addTeacher(String name, String university) {
		EngClass engClass = new EngClass();
		engClass.setName(name);
		engClass.setUniversity(universityService.load(Long.valueOf(university)));
		return engClassService.add(engClass);
	}
	
	@RequestMapping(value = "/delete_engclass")
	public @ResponseBody int removeEngClass(String id) {
		return engClassService.remove(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/get_all_engclass" , method = RequestMethod.POST)
	public @ResponseBody List<EngClass> getAllEngclass() {
		return engClassService.listAll();
	}

	@RequestMapping(value = "/upload_students_list")
	public @ResponseBody int uploadStudentsList (@RequestParam String id, @RequestParam MultipartFile stu_list, HttpServletRequest request) {
		if(stu_list.isEmpty())
			return -1;
		try {
			String originalFilename = stu_list.getOriginalFilename();
			String genePath = request.getSession().getServletContext().getRealPath("/upload/stu_list/");
			FileUtils.copyInputStreamToFile(stu_list.getInputStream(), new File(genePath,originalFilename));
			List<String[]> s_info = DealExcel.loadInStudentInfo(Long.valueOf(id),genePath+originalFilename);
			return engClassService.importStudent(s_info,Long.valueOf(id));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -2;
		}
		
	}
	
	
	//student mange
	@RequestMapping(value = "/list_student" , method = RequestMethod.POST)
	public @ResponseBody PageResults<Student> listStudent(String str,String page) {
		return studentService.list(str, Integer.valueOf(page));
	}
	
	@RequestMapping(value = "/delete_student" , method = RequestMethod.POST)
	public @ResponseBody int deleteStudent(String id) {
		return studentService.delete(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/load_student" , method = RequestMethod.POST)
	public @ResponseBody Student loadStudent(String id) {
		return studentService.get(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/edit_student" , method = RequestMethod.POST)
	public @ResponseBody int editStudent(String id, String name, String student_no, String password, String organization, String engClass) {
		Student student = new Student();
		student.setName(name);
		student.setStudent_no(student_no);
		student.setPassword(password);
		student.setOrganization(organizationService.load(Long.valueOf(organization)));
		student.setEngClass(engClassService.get(Long.valueOf(engClass)));
		long sid = Long.valueOf(id);
		if(sid==-1){
			return studentService.add(student);
		}else{
			student.setId(sid);
			return studentService.edit(student);
		}
	}
	
	//test manage
	@RequestMapping(value = "/list_test" , method = RequestMethod.POST)
	public @ResponseBody PageResults<Test> listTest(String page) {
		return testService.list(Integer.valueOf(page));
	}
	
	@RequestMapping(value = "/add_test" , method = RequestMethod.POST)
	public @ResponseBody int addTest(HttpServletRequest request) {
		String basePath = request.getSession().getServletContext().getRealPath("/res/");
		try {
			List<Test> tl = DealExcel.loadInTest(basePath+"test_in.xlsx");
			for( Test t : tl){
				testService.importTest(t);
			}
			return 1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
}
