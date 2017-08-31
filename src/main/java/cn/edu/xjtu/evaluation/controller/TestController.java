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
	
	@RequestMapping(value = "/getStatus" )
	public @ResponseBody int getStatus(String type, String tid, String uid) {
		return testService.check(Integer.valueOf(type),Long.valueOf(tid),Long.valueOf(uid));
	}
	
	@RequestMapping(value = "/loadTest" )
	public @ResponseBody Test loadTest( String id) {
		long tid = Long.valueOf(id);
		if( tid >=0 )
			return testService.get(tid);
		else{
			Test test = testService.getChoose();
			if(test == null){
				test = new Test();
				test.setId(-1);
			}
			return test;
		}
	}
}
