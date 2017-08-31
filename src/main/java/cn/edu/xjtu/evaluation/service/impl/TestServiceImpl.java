package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.AnswerDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.ExerciseDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.ExerciseTypeDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.InterventionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.PartDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.QuestionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.TestDAOImpl;
import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.ExerciseType;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.ITestService;
import cn.edu.xjtu.evaluation.support.DealExcel;
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
	ExerciseTypeDAOImpl exerciseTypeDAO;
	@Autowired
	AnswerDAOImpl answerDAO;
	
	@Override
	@Transactional
	public int add(Test test) {
		// TODO Auto-generated method stub
		try {
			testDAO.saveOrUpdate(test);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int remove(Long id) {
		// TODO Auto-generated method stub
		try {
			Test t = testDAO.get(id);
			for(Part p : t.getParts()) {
				for(Exercise e : p.getExercises()) {
					for(Question q : e.getQuestions()) {
						for(Intervention i : q.getInterventions()) {
							interventionDAO.delete(i);
						}
						questionDAO.delete(q);
					}
					exerciseDAO.delete(e);
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
		// TODO Auto-generated method stub
		if(test == null){
			return 0;
		}
		try {
			Set<Part> ps = test.getParts();
			test.setParts(null);
			testDAO.saveOrUpdate(test);
			for( Part p : ps){
				ExerciseType et = p.getExerciseType();
				Object[] values = { et.getDescription()};
				String hql ="from ExerciseType where description = ?";
				if( exerciseTypeDAO.getByHQL(hql, values) == null ) {
					exerciseTypeDAO.save(et);
				}
				et = exerciseTypeDAO.getByHQL(hql, values);
				p.setExerciseType(et);
				Set<Exercise> es = p.getExercises();
				p.setExercises(null);
				p.setTest(test);
				partDAO.save(p);
				for( Exercise e : es){
					e.setExerciseType(et);
					Set<Question> qs = e.getQuestions();
					e.setQuestions(null);
					e.setPart(p);
					exerciseDAO.save(e);
					for( Question q : qs){
						Set<Intervention> is = q.getInterventions();
						q.setInterventions(null);
						q.setExercise(e);
						questionDAO.save(q);
						for( Intervention i : is){
							i.setQuestion(q);
							interventionDAO.save(i);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int chooseTest(long id) {
		// TODO Auto-generated method stub
		String hql = "from Test where choose = 1";
		List<Test> tl = testDAO.getListByHQL(hql, null);
		if(tl!=null) {
			for(Test t : tl) {
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
		Object[] values = {type, tid, uid};
		Answer a = answerDAO.getByHQL(hql, values);
		if( a==null )
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

}
