package cn.edu.xjtu.evaluation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.entity.Type;
import cn.edu.xjtu.evaluation.service.IAnswerService;
import cn.edu.xjtu.evaluation.service.ITestService;
import cn.edu.xjtu.evaluation.service.ITypeService;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	ITestService testService;
	@Autowired
	IAnswerService answerService;
	
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
	
	@RequestMapping(value = "/finishTest" )
	public @ResponseBody int finishTest(HttpServletRequest request, String type, String tid, String uid, String[] records, String[] reasons) {
		return testService.finishTest(Integer.valueOf(type), Long.valueOf(tid), Long.valueOf(uid), (String[])records,(String[]) reasons);
	}
	
	@RequestMapping(value = "/testResult" )
	public @ResponseBody Answer testResult(String type, String tid, String uid) {
		return answerService.getAnswer(Long.valueOf(tid),Long.valueOf(uid),Integer.valueOf(type));
	}
	
	@RequestMapping(value = "/getAnswers" )
	public @ResponseBody List<Answer> getAnswers(String type, String uid) {
		return answerService.getAnswers(Long.valueOf(uid),Integer.valueOf(type));
	}
	
	@RequestMapping(value = "/RateSubmit" )
	public @ResponseBody int RateSubmit(String ansId, String ques) {
		String questionaire = "";
		JSONArray jsonArr = new JSONArray(ques);
		List<Object> quesstr = jsonArr.toList();
		for(int i=0; i<quesstr.size(); i++){
			questionaire += (String)quesstr.get(i) + "||";
		}
		answerService.FinishQue(Long.valueOf(ansId),questionaire);
		return 1;
	}
}
