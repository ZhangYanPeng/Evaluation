package cn.edu.xjtu.evaluation.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Admin;
import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.service.IAdminService;
import cn.edu.xjtu.evaluation.service.ITeacherService;

@Controller
@RequestMapping("/admin")
public class CommonController {

	@Autowired
	IAdminService adminService;
	@Autowired
	ITeacherService teacherService;

	// common
	@RequestMapping(value = "/jump")
	public String jump(HttpServletRequest request, HttpServletResponse response, String url) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

	@RequestMapping(value = "/login")
	public @ResponseBody Admin login(String username, String password) {
		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		admin = adminService.login(admin);
		if (admin == null) {
			admin = new Admin();
			admin.setId(-1);
		}
		return admin;
	}
	
	@RequestMapping(value = "/login_teacher")
	public @ResponseBody Teacher login_teacher(String username, String password) {
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

	@RequestMapping(value = "/index")
	public String login(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/log";
	}
}
