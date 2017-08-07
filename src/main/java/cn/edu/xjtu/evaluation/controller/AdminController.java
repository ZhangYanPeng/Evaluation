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
	
	@RequestMapping(value = "/index")
	public String login(HttpServletRequest request,HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
			response.setContentType("text/html;charset=utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/index";
	}
}
