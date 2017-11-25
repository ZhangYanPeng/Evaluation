package cn.edu.xjtu.evaluation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.entity.Type;
import cn.edu.xjtu.evaluation.service.ITestService;
import cn.edu.xjtu.evaluation.service.ITypeService;
import cn.edu.xjtu.evaluation.support.DealExcel;
import cn.edu.xjtu.evaluation.support.PageResults;

@Controller
@RequestMapping("/admin/test")
public class EnglishController {

	@Autowired
	ITestService testService;
	@Autowired
	ITypeService typeService;
	
	//test manage
	@RequestMapping(value = "/list_test" , method = RequestMethod.POST)
	public @ResponseBody PageResults<Test> listTest(String page) {
		return testService.list(Integer.valueOf(page));
	}
	
	@RequestMapping(value = "/add_test" , method = RequestMethod.POST)
	public @ResponseBody int addTest(HttpServletRequest request) {
		String basePath = request.getSession().getServletContext().getRealPath("/res/");
		try {
			List<Test> tl = DealExcel.loadInTest(basePath+"/test_in.xlsx");
			for( Test t : tl){
				testService.importTest(t);
			}
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}
	
	@RequestMapping(value = "/delete_test" , method = RequestMethod.POST)
	public @ResponseBody int deleteTest(String id) {
		return testService.remove(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/choose_test" , method = RequestMethod.POST)
	public @ResponseBody int chooseTest(String id) {
		return testService.chooseTest(Long.valueOf(id));
	}
	
	
	@RequestMapping(value = "/get_status" , method = RequestMethod.POST)
	public @ResponseBody int getStatus() {
		return Constants.COLLECT;
	}
	
	@RequestMapping(value = "/set_status" , method = RequestMethod.POST)
	public @ResponseBody int setStatus(String status) {
		return Constants.COLLECT = Integer.valueOf(status);
	}
	
	@RequestMapping(value = "/loadParts" )
	public @ResponseBody List<Part> loadParts( String id) {
		return testService.loadParts(Long.valueOf(id));
	}
	

	
	@RequestMapping(value = "/getTypes")
	public @ResponseBody List<Type> getTypes() {
		return typeService.getTypes();
	}
	
	@RequestMapping(value = "/loadType" )
	public @ResponseBody Type loadType(String id) {
		return typeService.load(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/deleteType" )
	public @ResponseBody int deleteType(String id) {
		return typeService.delete(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/addType" )
	public @ResponseBody int addType(String name) {
		Type type = new Type();
		type.setName(name);
		return typeService.add(type);
	}
	
	@RequestMapping(value = "/editType" )
	public @ResponseBody int addType(String id, String name) {
		Type type = typeService.load(Long.valueOf(id));
		type.setName(name);
		return typeService.update(type);
	}
}
