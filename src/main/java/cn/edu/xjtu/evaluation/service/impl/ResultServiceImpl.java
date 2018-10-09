package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.AnswerDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.TestDAOImpl;
import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.EvaluationResult;
import cn.edu.xjtu.evaluation.entity.OverallReport;
import cn.edu.xjtu.evaluation.entity.Record;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.entity.TestResult;
import cn.edu.xjtu.evaluation.service.IResultService;

@Service
public class ResultServiceImpl implements IResultService {
	@Autowired
	AnswerDAOImpl answerDAO;
	@Autowired
	StudentDAOImpl studentDAO;
	@Autowired
	TestDAOImpl testDAO;
	
	@Override
	@Transactional
	public TestResult getTestResult(long uid, long tid) {
		// TODO Auto-generated method stub
		Student student = studentDAO.get(uid);
		Test test = testDAO.get(tid);
		
		TestResult testResult = new TestResult();
		testResult.setStu_no(student.getStudent_no());
		testResult.setName(student.getName());
		testResult.setTest(test.getTitle());
		
		String hql = "from Answer where student.id = ? and test.id = ?";
		Object[] values = { uid, tid};
		Answer answer = answerDAO.getByHQL(hql,values);
		
		String hqls = "from Answer where test.id = ?";
		Object[] values_t = {tid};
		List<Answer> answers = answerDAO.getListByHQL(hqls,values_t);
		
		testResult.calculate(answer, answers);
		return testResult;
	}

	@Override
	public double[] getEvaluationResult(long uid, long tid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double[] getTotalResult(long uid, long tid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public EvaluationResult getEvaluationReport(Long uid, Long tid) {
		// TODO Auto-generated method stub
		String hql = "from Answer where student.id = ? and test.id = ?";
		Object[] values = { uid, tid};
		Answer answer = answerDAO.getByHQL(hql,values);
		EvaluationResult evaluationResult = new EvaluationResult();
		
		String hqls = "from Answer where test.id = ?";
		Object[] values_t = {tid};
		List<Answer> answers = answerDAO.getListByHQL(hqls,values_t);
		int max_score=0;
		for(Answer ans : answers){
			int score_t = 0;
			for(Record rec : ans.getRecords()){
				score_t += 6 - (rec.getResult()).split("\\|\\|").length;
			}
			if(score_t>max_score)
				max_score = score_t;
		}
		
		evaluationResult.getTestInfo(answer,max_score);
		evaluationResult.InitInterNum(answers);
		return evaluationResult;
	}

	@Override
	@Transactional
	public OverallReport getOverallReport(Long uid) {
		// TODO Auto-generated method stub
		String hqls = "from Answer where student.id = ?";
		Object[] values_t = {uid};
		List<Answer> answers = answerDAO.getListByHQL(hqls,values_t);
		OverallReport overall_report = new OverallReport();
		int[] max_scores = new int[answers.size()];
		
		int i=0;
		for(Answer ans : answers){
			String hql = "from Answer where test.id = ?";
			Object[] vals ={ans.getTest().getId()};
			List<Answer> ans_t = answerDAO.getListByHQL(hql,vals);
			max_scores[i] = 0;
			for(Answer an : ans_t){
				int sc = 0;
				for(Record rec : an.getRecords()){
					if((rec.getResult()).split("\\|\\|").length == 2)
						sc += 4;
				}
				if(sc>max_scores[i])
					max_scores[i] = sc;
			}
			i++;
		}
		
		overall_report.init(answers, max_scores);
		return overall_report;
	}

}
