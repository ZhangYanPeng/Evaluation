package cn.edu.xjtu.evaluation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Admin;
import cn.edu.xjtu.evaluation.service.IAdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	IAdminService adminService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Admin login(String username, String password) {
		Admin admin = new Admin();
		admin.setUsername(username);
		admin.setPassword(password);
		admin = adminService.login(admin);
		return admin;
	}
	
	@RequestMapping(value = "/")
	public String login() {
		return "/admin/index";
	}
}
