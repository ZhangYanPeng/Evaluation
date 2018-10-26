package cn.edu.xjtu.evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Admin;
import cn.edu.xjtu.evaluation.service.IAdminService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping("/admin/admin")
public class AdminController {
	
	@Autowired
	IAdminService adminService;
	
	//admin manage
	@RequestMapping(value = "/list_admin")
	public @ResponseBody PageResults<Admin> listAdmin(String page) {
		return adminService.list(Integer.valueOf(page));
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
