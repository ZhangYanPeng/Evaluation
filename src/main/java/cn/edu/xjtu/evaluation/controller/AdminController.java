package cn.edu.xjtu.evaluation.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.xjtu.evaluation.entity.Admin;
import cn.edu.xjtu.evaluation.entity.Organization;
import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.entity.University;
import cn.edu.xjtu.evaluation.service.IAdminService;
import cn.edu.xjtu.evaluation.service.IOrganizationService;
import cn.edu.xjtu.evaluation.service.ISchoolService;
import cn.edu.xjtu.evaluation.service.IUniversityService;
import cn.edu.xjtu.evaluation.support.DealExcel;
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
	@Autowired
	IOrganizationService organizationService;
	
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
	public @ResponseBody PageResults<School> listSchool(int page, String university) {
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
	public @ResponseBody School loadSchool(String id) {
		return schoolService.load(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/remove_school")
	public @ResponseBody int removSchool(String id) {
		return schoolService.delete(Long.valueOf(id));
	}
	
	//class manage
	@RequestMapping(value = "/get_all_school")
	public @ResponseBody List getAllSchool( String university) {
		return schoolService.getAll(Long.valueOf(university));
	}
	
	@RequestMapping(value = "/edit_class")
	public @ResponseBody int editClass(String id,String name, String school) {
		Long cid = Long.valueOf(id);
		Organization organization = new Organization();
		organization.setClassName(name);
		organization.setSchool(schoolService.load(Long.valueOf(school)));
		if(cid==-1){
			return organizationService.add(organization);
		}else{
			organization.setId(cid);
			return organizationService.edit(organization);
		}
	}
	
	@RequestMapping(value = "/list_class")
	public @ResponseBody PageResults<Organization> listClass(int page,String university, String school) {
		return organizationService.list(page,Long.valueOf(university),Long.valueOf(school));
	}
	
	@RequestMapping(value = "/load_class")
	public @ResponseBody Organization loadClass(String id) {
		return organizationService.load(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/upload_students_list")
	public @ResponseBody int uploadStudentsList (@RequestParam String id, @RequestParam MultipartFile stu_list, HttpServletRequest request) {
		if(stu_list.isEmpty())
			return -1;
		try {
			String originalFilename = stu_list.getOriginalFilename();
			String genePath = request.getSession().getServletContext().getRealPath("/upload/stu_list/");
			FileUtils.copyInputStreamToFile(stu_list.getInputStream(), new File(genePath,originalFilename));
			DealExcel.loadInStudentInfo(Long.valueOf(id),genePath+originalFilename);
			System.out.println(genePath);
			return 1;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return -2;
		}
		
	}
}
