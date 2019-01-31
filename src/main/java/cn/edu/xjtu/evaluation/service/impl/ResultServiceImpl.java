package cn.edu.xjtu.evaluation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.AnswerDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.EngClassDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.TestDAOImpl;
import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.EngClassResult;
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
	@Autowired
	EngClassDAOImpl engClassDAO;
	
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
		
		evaluationResult.getTestInfo(answer);
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
		
		overall_report.init(answers);
		return overall_report;
	}

	@Override
	@Transactional
	public List<EngClassResult> loadResult(Long ecid, Integer tno, int sortby) {
		// TODO Auto-generated method stub
		String hql = "from Student where engClass.id = ?";
		Object[] values = {ecid};
		List<Student> students = studentDAO.getListByHQL(hql, values);
		
		List<EngClassResult> lecr = new ArrayList<EngClassResult>();
		for(Student student : students){
			String hqlstring = "from Answer where student.id = ? and test.testno = ?";
			Object[] vals = {student.getId(), tno};
			Answer answer = answerDAO.getByHQL(hqlstring, vals);
			lecr.add(new EngClassResult(student, answer));
		}
		if(sortby != 0){
			for(int i=0; i<lecr.size(); i++){
				for(int j=i; j<lecr.size(); j++){
					if( cmp(lecr.get(i), lecr.get(j), sortby) > 0 ){
						EngClassResult tmp = lecr.get(i);
						lecr.set(i, lecr.get(j));
						lecr.set(j, tmp);
					}
				}
			}
		}
		return lecr;
	}

	//sortby < 0 降序  >0 升序
	//sortby 1 score 2 eval_score 3 potential 4 time_consume 5 ave_time_consume 6 ave_inter_time
	private int cmp(EngClassResult engClassResult, EngClassResult engClassResult2, int sortby) {
		// TODO Auto-generated method stub
		if(sortby > 0){
			switch(sortby){
				case 1: 
					if(engClassResult.getScore() > engClassResult2.getScore())
						return 1;
					else
						return -1;
				case 2: 
					if(engClassResult.getEval_score() > engClassResult2.getEval_score())
						return 1;
					else
						return -1;
				case 3: 
					if(engClassResult.getPotential() > engClassResult2.getPotential())
						return 1;
					else
						return -1;
				case 4: 
					if(engClassResult.getTime_consume_val() > engClassResult2.getTime_consume_val())
						return 1;
					else
						return -1;
				case 5: 
					if(engClassResult.getAve_time_consume_val() > engClassResult2.getAve_time_consume_val())
						return 1;
					else
						return -1;
				case 6: 
					if(engClassResult.getAve_inter_time_val() > engClassResult2.getAve_inter_time_val())
						return 1;
					else
						return -1;
			}
		}else{
			switch(sortby){
				case -1: 
					if(engClassResult.getScore() < engClassResult2.getScore())
						return 1;
					else
						return -1;
				case -2: 
					if(engClassResult.getEval_score() < engClassResult2.getEval_score())
						return 1;
					else
						return -1;
				case -3: 
					if(engClassResult.getPotential() < engClassResult2.getPotential())
						return 1;
					else
						return -1;
				case -4: 
					if(engClassResult.getTime_consume_val() < engClassResult2.getTime_consume_val())
						return 1;
					else
						return -1;
				case -5: 
					if(engClassResult.getAve_time_consume_val() < engClassResult2.getAve_time_consume_val())
						return 1;
					else
						return -1;
				case -6: 
					if(engClassResult.getAve_inter_time_val() < engClassResult2.getAve_inter_time_val())
						return 1;
					else
						return -1;
			}
		}
		return 0;
	}

}
