package cn.edu.xjtu.evaluation.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Admin;
import cn.edu.xjtu.evaluation.service.IAdminService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	IAdminService adminService;
	
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
}
