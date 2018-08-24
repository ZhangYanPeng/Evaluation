package cn.edu.xjtu.evaluation.controller;

import java.io.File;
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
import cn.edu.xjtu.evaluation.entity.Organization;
import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.entity.University;
import cn.edu.xjtu.evaluation.service.IEngClassService;
import cn.edu.xjtu.evaluation.service.IOrganizationService;
import cn.edu.xjtu.evaluation.service.ISchoolService;
import cn.edu.xjtu.evaluation.service.IStudentService;
import cn.edu.xjtu.evaluation.service.ITeacherService;
import cn.edu.xjtu.evaluation.service.IUniversityService;
import cn.edu.xjtu.evaluation.support.DealExcel;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping(value="/admin/account")
public class AccountController {

	@Autowired
	ISchoolService schoolService;
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

	// account manage
	@RequestMapping(value = "/list_university")
	public @ResponseBody PageResults<University> listUniversity(String page) {
		return universityService.list(Integer.valueOf(page));
	}

	@RequestMapping(value = "/add_university")
	public @ResponseBody int addAdmin(String name) {
		University university = new University();
		university.setName(name);
		university.setSchools(null);
		return universityService.add(university);
	}

	@RequestMapping(value = "/load_university")
	public @ResponseBody University loadUniversity(String id) {
		return universityService.load(Long.valueOf(id));
	}

	@RequestMapping(value = "/edit_university")
	public @ResponseBody int editUniversity(String id, String name) {
		University university = universityService.load(Long.valueOf(id));
		university.setName(name);
		return universityService.update(university);
	}

	@RequestMapping(value = "/remove_university")
	public @ResponseBody int removUniversity(String id) {
		return universityService.delete(Long.valueOf(id));
	}

	// school manage
	@RequestMapping(value = "/get_all_university")
	public @ResponseBody List getAllUniversity() {
		return universityService.getAll();
	}

	@RequestMapping(value = "/list_school")
	public @ResponseBody PageResults<School> listSchool(int page, String university) {
		return schoolService.list(page, Long.valueOf(university));
	}

	@RequestMapping(value = "/edit_school")
	public @ResponseBody int editSchool(String id, String name, String university) {
		Long sid = Long.valueOf(id);
		School school = new School();
		school.setName(name);
		school.setOrganizations(null);
		school.setUniversity(universityService.load(Long.valueOf(university)));
		if (sid == -1) {
			return schoolService.add(school);
		} else {
			school.setId(sid);
			return schoolService.edit(school);
		}
	}

	@RequestMapping(value = "/load_school")
	public @ResponseBody School loadSchool(String id) {
		return schoolService.load(Long.valueOf(id));
	}

	@RequestMapping(value = "/remove_school")
	public @ResponseBody int removSchool(String id) {
		return schoolService.delete(Long.valueOf(id));
	}

	// class manage
	@RequestMapping(value = "/get_all_school")
	public @ResponseBody List getAllSchool(String university) {
		return schoolService.getAll(Long.valueOf(university));
	}

	@RequestMapping(value = "/edit_class")
	public @ResponseBody int editClass(String id, String name, String school) {
		Long cid = Long.valueOf(id);
		Organization organization = new Organization();
		organization.setClassName(name);
		organization.setSchool(schoolService.load(Long.valueOf(school)));
		if (cid == -1) {
			return organizationService.add(organization);
		} else {
			organization.setId(cid);
			return organizationService.edit(organization);
		}
	}

	@RequestMapping(value = "/list_class")
	public @ResponseBody PageResults<Organization> listClass(int page, String university, String school) {
		return organizationService.list(page, Long.valueOf(university), Long.valueOf(school));
	}

	@RequestMapping(value = "/load_class")
	public @ResponseBody Organization loadClass(String id) {
		return organizationService.load(Long.valueOf(id));
	}

	@RequestMapping(value = "/get_all_class")
	public @ResponseBody List<Organization> getAllClass(String school) {
		return organizationService.getAllClass(Long.valueOf(school));
	}
	
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
	public @ResponseBody PageResults<EngClass> listEngClass(String page, String university) {
		PageResults<EngClass> tmp =  engClassService.list(Integer.valueOf(page), Long.valueOf(university));
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
			List<String[]> s_info = DealExcel.loadInStudentInfo(Long.valueOf(id),genePath+"/"+originalFilename);
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
		long sid = Long.valueOf(id);
		Student student = new Student();
		if(sid>=0){
			student = studentService.get(sid);
		}
		student.setName(name);
		student.setStudent_no(student_no);
		student.setUsername(student_no);
		student.setPassword(password);
		student.setOrganization(organizationService.load(Long.valueOf(organization)));
		student.setEngClass(engClassService.get(Long.valueOf(engClass)));
		if(sid==-1){
			return studentService.add(student);
		}else{
			return studentService.edit(student);
		}
	}
}
