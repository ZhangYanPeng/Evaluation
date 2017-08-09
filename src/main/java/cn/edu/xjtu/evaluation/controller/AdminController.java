package cn.edu.xjtu.evaluation.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Admin;
import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.entity.University;
import cn.edu.xjtu.evaluation.service.IAdminService;
import cn.edu.xjtu.evaluation.service.ISchoolService;
import cn.edu.xjtu.evaluation.service.IUniversityService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	IAdminService adminService;
	@Autowired
	IUniversityService universityService;
	@Autowired
	ISchoolService schoolService;
	
	//common
	@RequestMapping(value = "/jump")
	public String jump(HttpServletRequest request,HttpServletResponse response, String url) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Admin login(String username, String password) {
		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		admin = adminService.login(admin);
		if( admin == null ){
			admin = new Admin();
			admin.setId(-1);
		}
		return admin;
	}
	
	@RequestMapping(value = "/index")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/log";
	}
	
	
	//admin manage
	@RequestMapping(value = "/list_admin")
	public @ResponseBody PageResults<Admin> listAdmin(String username, String page) {
		return adminService.list(username, Integer.valueOf(page));
	}
	
	@RequestMapping(value = "/edit_admin")
	public @ResponseBody int editAdmin(String username, String password, String id) {
		Admin admin = new Admin();
		admin.setId(Long.valueOf(id));
		admin.setPassword(password);
		admin.setUsername(username);
		return adminService.edit(admin);
	}
	
	@RequestMapping(value = "/load_admin")
	public @ResponseBody Admin loadAdmin(String id) {
		return adminService.load(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/add_admin")
	public @ResponseBody int addAdmin(String username, String password) {
		Admin admin = new Admin();
		admin.setPassword(password);
		admin.setUsername(username);
		return adminService.add(admin);
	}
	
	@RequestMapping(value = "/delete_admin")
	public @ResponseBody boolean deleteAdmin(String id) {
		return adminService.delete(Long.valueOf(id));
	}
	
	
	//account manage
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
	
	//school manage
	@RequestMapping(value = "/get_all_university")
	public @ResponseBody List getAllUniversity() {
		return universityService.getAll();
	}
	
	@RequestMapping(value = "/list_school")
	public @ResponseBody PageResults<School> getAllUniversity(int page, String university) {
		System.out.println(university);
		System.out.println(schoolService.list(page,Long.valueOf(university)).getResults().size());
		return schoolService.list(page,Long.valueOf(university));
	}

	@RequestMapping(value = "/edit_school")
	public @ResponseBody int editSchool(String id,String name, String university) {
		Long sid = Long.valueOf(id);
		School school = new School();
		school.setName(name);
		school.setOrganizations(null);
		school.setUniversity(universityService.load(Long.valueOf(university)));
		if(sid==-1){
			return schoolService.add(school);
		}else{
			school.setId(sid);
			return schoolService.edit(school);
		}
	}
	
	@RequestMapping(value = "/load_school")
	public @ResponseBody School getAllUniversity(String id) {
		return schoolService.load(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/remove_school")
	public @ResponseBody int removSchool(String id) {
		return schoolService.delete(Long.valueOf(id));
	}
}
