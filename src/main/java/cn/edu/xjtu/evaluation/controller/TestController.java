package cn.edu.xjtu.evaluation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.ITestService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	ITestService testService;
	
	@RequestMapping(value = "/getAllTest" , method = RequestMethod.POST)
	public List<Test> listTest(String page) {
		return testService.getAll();
	}
}
