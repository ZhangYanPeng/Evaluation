package cn.edu.xjtu.evaluation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.IAnswerService;
import cn.edu.xjtu.evaluation.service.IResultService;
import cn.edu.xjtu.evaluation.service.ITestService;
import cn.edu.xjtu.evaluation.support.PdfCreator;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	ITestService testService;
	@Autowired
	IAnswerService answerService;
	@Autowired
	IResultService resultService;
	
	@RequestMapping(value = "/getAllTest" )
	public @ResponseBody List<Test> listTest() {
		List<Test> tList = testService.getAll();
		if(tList == null){
			return new ArrayList<Test>();
		}
		else{
			for(Test t : tList){
				t.setParts(null);
			}
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
	public @ResponseBody int finishTest(HttpServletRequest request, String type, String tid, String uid, String[] records, String[] reasons, String[] timecon, String[] timereact, String start_time, String end_time) {
		return testService.finishTest(Integer.valueOf(type), Long.valueOf(tid), Long.valueOf(uid), (String[])records,(String[]) reasons,timecon,timereact, start_time, end_time);
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
	public @ResponseBody int RateSubmit(String testId, String userId, String ques) {
		String questionaire = "";
		JSONArray jsonArr = new JSONArray(ques);
		List<Object> quesstr = jsonArr.toList();
		for(int i=0; i<quesstr.size(); i++){
			questionaire += (String)quesstr.get(i) + "||";
		}
		Answer ans = answerService.getAnswer(Long.valueOf(testId), Long.valueOf(userId), 1);
		answerService.FinishQue(ans.getId(),questionaire);
		return 1;
	}
	
	@RequestMapping(value = "/getSingleReport" )
	public @ResponseBody String getSingleReport(String uid, String tid, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/download/pdf/");
		String filename = String.valueOf(uid)+String.valueOf(tid)+"test.pdf";
		try {
			PdfCreator.createSingleReport(resultService.getTestResult(Long.valueOf(uid), Long.valueOf(tid)),
					resultService.getEvaluationReport(Long.valueOf(uid), Long.valueOf(tid)),
					path+filename, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("path", "pdf/"+filename);
		return obj.toString();
	}
	
	@RequestMapping(value = "/getOverallReport" )
	public @ResponseBody String getOverallReport(String uid, HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/download/pdf/");
		String filename = String.valueOf(uid)+"overall.pdf";
		try {
			PdfCreator.createOverallReport(resultService.getOverallReport(Long.valueOf(uid)), path + filename, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("path", "pdf/" + filename);
		return obj.toString();
	}
}
