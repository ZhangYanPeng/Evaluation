package cn.edu.xjtu.evaluation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.entity.EngClassResult;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.IEngClassService;
import cn.edu.xjtu.evaluation.service.IResultService;
import cn.edu.xjtu.evaluation.service.IStudentService;
import cn.edu.xjtu.evaluation.service.ITestService;
import cn.edu.xjtu.evaluation.support.PdfCreator;

@Controller
@RequestMapping("/admin/report")
public class ReportController {

	@Autowired
	IResultService resultService;
	@Autowired
	IEngClassService engClassService;
	@Autowired
	IStudentService studentService;
	@Autowired
	ITestService testService;
	
	@RequestMapping(value = "/load_result" )
	public @ResponseBody List<EngClassResult> load_result(String engid, String test, int sortby){
		return resultService.loadResult(Long.valueOf(engid), Integer.valueOf(test), sortby);
	}
	
	@RequestMapping(value = "/load_info" )
	public @ResponseBody List<String> load_info(String engid){
		return engClassService.getInfo(Long.valueOf(engid));
	}
	
	@RequestMapping(value = "/load_test_result" )
	public @ResponseBody String[][] load_test_result(String engid, String test){
		return engClassService.getTestInfo(Long.valueOf(engid), Integer.valueOf(test));
	}
	
	@RequestMapping(value = "/load_ability_result" )
	public @ResponseBody String[][] load_ability_result(String engid, String test){
		return engClassService.getAbilityInfo(Long.valueOf(engid), Integer.valueOf(test));
	}
	
	@RequestMapping(value = "/getSingleReport" )
	public @ResponseBody String getSingleReport(HttpServletRequest request,String stu, String testno){
		Student student = studentService.getByStuNo(stu);
		Test test = testService.getByTestNo(testno);
		String path = request.getSession().getServletContext().getRealPath("/download/pdf/");
		String filename = String.valueOf(student.getId())+String.valueOf(test.getId())+"test.pdf";
		try {
			PdfCreator.createSingleReport(resultService.getTestResult(Long.valueOf(student.getId()), Long.valueOf(test.getId())),
					resultService.getEvaluationReport(Long.valueOf(student.getId()), Long.valueOf(test.getId())),
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
	public @ResponseBody String getOverallReport(String stu, HttpServletRequest request) {
		Student student = studentService.getByStuNo(stu);
		String path = request.getSession().getServletContext().getRealPath("/download/pdf/");
		String filename = String.valueOf(student.getId())+"overall.pdf";
		try {
			PdfCreator.createOverallReport(resultService.getOverallReport(Long.valueOf(student.getId())), path + filename, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject obj = new JSONObject();
		obj.put("path", "pdf/" + filename);
		return obj.toString();
	}
}
