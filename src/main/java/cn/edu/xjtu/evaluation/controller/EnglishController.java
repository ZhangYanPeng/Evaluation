package cn.edu.xjtu.evaluation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.util.SystemOutLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.common.Definition;
import cn.edu.xjtu.evaluation.common.Definition.QUESTION_LEVEL;
import cn.edu.xjtu.evaluation.common.Definition.QUESTION_TYPE;
import cn.edu.xjtu.evaluation.entity.Audio;
import cn.edu.xjtu.evaluation.entity.EngClassResult;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.entity.Type;
import cn.edu.xjtu.evaluation.service.IAudioService;
import cn.edu.xjtu.evaluation.service.IExerciseService;
import cn.edu.xjtu.evaluation.service.IInterventionService;
import cn.edu.xjtu.evaluation.service.IPartService;
import cn.edu.xjtu.evaluation.service.IQuestionService;
import cn.edu.xjtu.evaluation.service.IResultService;
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
	IPartService partService;
	@Autowired
	ITypeService typeService;
	@Autowired
	IExerciseService exerciseService;
	@Autowired
	IQuestionService questionService;
	@Autowired
	IAudioService audioService;
	@Autowired
	IInterventionService interventionService;
	@Autowired
	IResultService resultService;
	
	//test manage
	@RequestMapping(value = "/list_test" , method = RequestMethod.POST)
	public @ResponseBody PageResults<Test> listTest(String page) {
		PageResults<Test> pr = testService.list(Integer.valueOf(page));
		for(Test t : pr.getResults()){
			t.setParts(null);
		}
		return pr;
	}
	
	@RequestMapping(value = "/add_test" , method = RequestMethod.POST)
	public @ResponseBody Test addTest(String name) {
		Test t = new Test();
		t.setTitle(name);
		t.setChoose(0);
		t.setCollect(0);
		t.setId(System.currentTimeMillis());
		return testService.add(t);
	}
	
	@RequestMapping(value = "/editTest" )
	public @ResponseBody int editTest(String id, String title, String testno) {
		Test test = testService.get(Long.valueOf(id));
		test.setTitle(title);
		test.setTestno(Integer.valueOf(testno));
		return testService.updateTest(test);
	}
	
	@RequestMapping(value = "/delete_test" , method = RequestMethod.POST)
	public @ResponseBody int deleteTest(String id) {
		return testService.remove(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/choose" , method = RequestMethod.POST)
	public @ResponseBody int chooseTest(String id) {
		return testService.chooseTest(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/collect" , method = RequestMethod.POST)
	public @ResponseBody int collect(String id, String state) {
		return testService.collect(Long.valueOf(id),Integer.valueOf(state));
	}
	
	@RequestMapping(value = "/loadTest" )
	public @ResponseBody Test loadTest( String id) {
		return testService.get(Long.valueOf(id));
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
	public @ResponseBody int addType(String name, String direction) {
		Type type = new Type();
		type.setName(name);
		type.setDirection(direction);
		return typeService.add(type);
	}
	
	@RequestMapping(value = "/editType" )
	public @ResponseBody int editType(String id, String name, String direction) {
		Type type = typeService.load(Long.valueOf(id));
		type.setName(name);
		type.setDirection(direction);
		return typeService.update(type);
	}
	
	@RequestMapping(value = "/addExercise" )
	public @ResponseBody Exercise addExercise() {
		return exerciseService.create();
	}
	
	@RequestMapping(value = "/deleteExercise" )
	public @ResponseBody int deleteExercise(String id) {
		return exerciseService.remove(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/listExercise" )
	public @ResponseBody PageResults<Exercise> listExercise(String page, String type) {
		if(type == "" || type == null)
			type = "-1";
		PageResults<Exercise> pr = exerciseService.getPageList(Integer.valueOf(page), Long.valueOf(type));
		for(Exercise e : pr.getResults()){
			e.setQuestions(null);
		}
		return pr;
	}
	
	@RequestMapping(value = "/listAllExercise" )
	public @ResponseBody List<Exercise> listAllExercise( String type) {
		if(type == "" || type == null)
			type = "-1";
		List<Exercise> le = exerciseService.getList(Long.valueOf(type));
		for(Exercise e : le){
			e.setQuestions(null);
		}
		return le;
	}
	
	@RequestMapping(value = "/loadExercise" )
	public @ResponseBody Exercise loadExercise(String id) {
		return exerciseService.get(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/addQuestion" )
	public @ResponseBody int addQuestion(String id) {
		return questionService.add(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/deleteQuestion" )
	public @ResponseBody int deleteQuestion(String id) {
		return questionService.remove(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/editExercise" )
	public @ResponseBody int editExercise(String exercise) {
		JSONObject jsonObj = new JSONObject(exercise);
		Exercise exer = exerciseService.get(jsonObj.getLong("id"));
		exer.setType(typeService.load(jsonObj.getLong("type")));
		exer.setDescription(jsonObj.getString("description"));
		exer.setText(jsonObj.getString("text"));
		if(exer.getQuestions().size()>0){
			JSONArray answer = new JSONArray();
			if(exer.getQuestions().size()>1){
				answer = jsonObj.getJSONArray("answer");
			}else{
				answer.put(jsonObj.getInt("answer"));
			}
			JSONArray option =  jsonObj.getJSONArray("option");
			JSONArray in_text =  jsonObj.getJSONArray("in_text");
			JSONArray level,type;
			if(exer.getQuestions().size()>1){
				level = jsonObj.getJSONArray("set_level");
				type = jsonObj.getJSONArray("set_type");
			}else{
				level = new JSONArray();
				type = new JSONArray();
				level.put(jsonObj.getString("set_level"));
				type.put(jsonObj.getString("set_type"));
			}
			for(Question q : exer.getQuestions()){
				int qn = q.getQ_num();
				q.setAnswer(answer.getInt(qn));
				String op = "";
				for(int i=qn*5; i<(qn+1)*5; i++){
					op += "||" + option.getString(i);
				}
				switch((String)level.get(qn)){
					case "easy" : q.setLevel(Definition.QUESTION_LEVEL.easy);break;
					case "normal" : q.setLevel(Definition.QUESTION_LEVEL.normal);break;
					case "hard" : q.setLevel(Definition.QUESTION_LEVEL.hard);break;
					default : q.setLevel(Definition.QUESTION_LEVEL.easy);break;
				}
				switch((String)type.get(qn)){
					case "presentation" : q.setType(Definition.QUESTION_TYPE.presentation);break;
					case "grammar" : q.setType(Definition.QUESTION_TYPE.grammar);break;
					case "comprehension" : q.setType(Definition.QUESTION_TYPE.comprehension);break;
					case "details" : q.setType(Definition.QUESTION_TYPE.details);break;
					case "inference" : q.setType(Definition.QUESTION_TYPE.inference);break;
					default : q.setType(Definition.QUESTION_TYPE.presentation);break;
				}
				q.setOptions(op);
				questionService.edit(q);
				for(Intervention i : q.getInterventions()){
					i.setText(in_text.getString(qn*4+i.getLevel()));
					interventionService.edit(i);
				}
			}
		}
		return exerciseService.edit(exer);
	}
	
	@RequestMapping(value = "/deleteAudio" )
	public @ResponseBody int deleteAudio(String id){
		return audioService.delete(Long.valueOf(id));
	}
	
	@RequestMapping(value = "/uploadAudio")
	public @ResponseBody int uploadPic(@RequestParam String id, @RequestParam String type, HttpServletRequest request){
		Audio audio = new Audio();
		int t = Integer.valueOf(type);
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultiValueMap<String, MultipartFile> mfmap = multipartRequest.getMultiFileMap();
		MultipartFile aud;
		for (String key : mfmap.keySet()) {
			if(t == 0 && key.contains("qa")){
				aud = mfmap.getFirst(key);
				Question question = questionService.load(Long.valueOf(id));
				audio.setQuestion(question);
				String originalFilename = aud.getOriginalFilename();
				String genePath = request.getSession().getServletContext().getRealPath("/upload/audio/");
				audio.setSrc(request.getContextPath()+"/audio/"+originalFilename);
				audio.setPath(genePath+"/"+originalFilename);
				audioService.save(audio, aud);
				question.setAudio(audioService.getByQue(question.getId()));
				questionService.edit(question);
			}else if(t == 1 && key.contains("ia")){
				aud = mfmap.getFirst(key);
				Intervention intervention = interventionService.load(Long.valueOf(id));
				audio.setIntervention(intervention);
				String originalFilename = aud.getOriginalFilename();
				String genePath = request.getSession().getServletContext().getRealPath("/upload/audio/");
				audio.setSrc(request.getContextPath()+"/audio/"+originalFilename);
				audio.setPath(genePath+"/"+originalFilename);
				audioService.save(audio, aud);
				intervention.setAudio(audioService.getByIn(intervention.getId()));
				interventionService.edit(intervention);
			}
		}
		return 0;
	}
	
	@RequestMapping(value = "/addTestExercise" )
	public @ResponseBody int deleteAudio(String id, String eid){
		return testService.addExercise(Long.valueOf(id), Long.valueOf(eid));
	}
	
	@RequestMapping(value = "/removeTestExercise" )
	public @ResponseBody int removeTestExercise(String id, String eid){
		return testService.removeExercise(Long.valueOf(id), Long.valueOf(eid));
	}
	
	@RequestMapping(value = "/del_audio" )
	public @ResponseBody int del_audio(String id){
		return audioService.removeAud(Long.valueOf(id));
	}
}
