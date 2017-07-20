package cn.edu.xjtu.evaluation.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.xjtu.evaluation.entity.Admin;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.service.IAdminService;
import cn.edu.xjtu.evaluation.service.IExerciseService;
import cn.edu.xjtu.evaluation.service.IQuestionService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	IAdminService adminService;
	@Autowired
	IQuestionService questionService;	
	@Autowired
	IExerciseService exerciseService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		
		return "home";
	}
	
}
