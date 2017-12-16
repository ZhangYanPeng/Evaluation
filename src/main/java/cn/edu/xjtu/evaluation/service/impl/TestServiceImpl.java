package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.AnswerDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.ExerciseDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.InterventionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.PartDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.PartExerDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.QuestionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.RecordDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.TestDAOImpl;
import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.entity.PartExer;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.entity.Record;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.ITestService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class TestServiceImpl implements ITestService {

	@Autowired
	TestDAOImpl testDAO;
	@Autowired
	PartDAOImpl partDAO;
	@Autowired
	ExerciseDAOImpl exerciseDAO;
	@Autowired
	QuestionDAOImpl questionDAO;
	@Autowired
	InterventionDAOImpl interventionDAO;
	@Autowired
	AnswerDAOImpl answerDAO;
	@Autowired
	RecordDAOImpl recordDAO;
	@Autowired
	StudentDAOImpl studentDAO;
	@Autowired
	PartExerDAOImpl partExerDAO;

	@Override
	@Transactional
	public Test add(Test test) {
		// TODO Auto-generated method stub
		testDAO.saveOrUpdate(test);
		return test;
	}

	@Override
	@Transactional
	public int remove(Long id) {
		// TODO Auto-generated method stub
		try {
			Test t = testDAO.get(id);
			for (Part p : t.getParts()) {
				for (PartExer pe : p.getPartExers()) {
					Exercise e = pe.getExercise();
					for (Question q : e.getQuestions()) {
						for (Intervention i : q.getInterventions()) {
							interventionDAO.delete(i);
						}
						questionDAO.delete(q);
					}
					exerciseDAO.delete(e);
					partExerDAO.delete(pe);
				}
				partDAO.delete(p);
			}
			testDAO.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public Test get(long id) {
		// TODO Auto-generated method stub
		try {
			return testDAO.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public List<Test> getAll() {
		// TODO Auto-generated method stub
		String hql = "from Test";
		return testDAO.getListByHQL(hql, null);
	}

	@Override
	@Transactional
	public PageResults<Test> list(Integer page) {
		// TODO Auto-generated method stub
		String hql = "from Test";
		String countHql = "select count(*) from Test";
		Object[] values = {};
		return testDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
	}

	@Override
	@Transactional
	public int importTest(Test test) {
		return 1;
	}

	@Override
	@Transactional
	public int chooseTest(long id) {
		// TODO Auto-generated method stub
		String hql = "from Test where choose = 1";
		List<Test> tl = testDAO.getListByHQL(hql, null);
		if (tl != null) {
			for (Test t : tl) {
				t.setChoose(0);
				testDAO.update(t);
			}
		}
		Test t = testDAO.get(id);
		t.setChoose(1);
		testDAO.update(t);
		return 0;
	}

	@Override
	@Transactional
	public int check(Integer type, Long tid, Long uid) {
		// TODO Auto-generated method stub
		String hql = "from Answer where type = ? and student.id = ? and test.id = ?";
		Object[] values = { type, uid, tid };
		Answer a = answerDAO.getByHQL(hql, values);
		if (a == null)
			return 0;
		return 1;
	}

	@Override
	@Transactional
	public Test getChoose() {
		// TODO Auto-generated method stub
		String hql = "from Test where choose = 1";
		return testDAO.getByHQL(hql, null);
	}

	@Override
	@Transactional
	public int finishTest(int type, long tid, long uid, String[] records, String[] reasons) {
		// TODO Auto-generated method stub
		Answer answer = new Answer();
		answer.setType(type);
		answer.setTest(testDAO.get(tid));
		answer.setStudent(studentDAO.get(uid));
		answerDAO.save(answer);
		for (Part p : testDAO.get(tid).getParts()) {
//			for (Exercise e : p.getExercises()) {
//				for (Question q : e.getQuestions()) {
//					int i = q.getQ_num() - 1;
//					Record record = new Record();
//					record.setAnswer(answer);
//					record.setQuestion(q);
//					record.setResult(records[i]);
//					record.setReason(reasons[i]);
//					recordDAO.save(record);
//				}
//			}
		}
		return 0;
	}

	@Override
	@Transactional
	public List<Part> loadParts(Long id) {
		// TODO Auto-generated method stub
		String hqlString = "from Part where test.id = ? order by p_no asc";
		Object[] values = { id };
		return partDAO.getListByHQL(hqlString, values);
	}

	@Override
	@Transactional
	public List<Exercise> loadExercises(Long id) {
		// TODO Auto-generated method stub
		String hqlString = "from Exercise where part.id = ? order by e_no asc";
		Object[] values = { id };
		return exerciseDAO.getListByHQL(hqlString, values);
	}

	@Override
	@Transactional
	public List<Question> loadQuestions(Long id) {
		// TODO Auto-generated method stub
		String hqlString = "from Question where exercise.id = ? order by q_no asc";
		Object[] values = { id };
		return questionDAO.getListByHQL(hqlString, values);
	}

	@Override
	@Transactional
	public List<Intervention> loadInterventions(Long id) {
		// TODO Auto-generated method stub
		String hqlString = "from Intervention where question.id = ? order by level asc";
		Object[] values = { id };
		return interventionDAO.getListByHQL(hqlString, values);
	}

}
