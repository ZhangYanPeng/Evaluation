package cn.edu.xjtu.evaluation.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.ITestService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	ITestService testService;
	
	@RequestMapping(value = "/getAllTest" )
	public @ResponseBody List<Test> listTest() {
		List<Test> tList = testService.getAll();
		if(tList == null){
			return new ArrayList<Test>();
		}
		else{
			return tList;
		}
	}
}
