package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.ExerciseDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.InterventionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.PartDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.QuestionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.TestDAOImpl;
import cn.edu.xjtu.evaluation.entity.Exercise;
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
	public int remove(Test test) {
		// TODO Auto-generated method stub
		try {
			testDAO.delete(test);
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
			return testDAO.load(id);
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
			testDAO.saveOrUpdate(test);
			for( Part p : test.getParts()){
				partDAO.save(p);
				for( Exercise e : p.getExercises()){
					exerciseDAO.save(e);
					for( Question q : e.getQuestions()){
						questionDAO.save(q);
						for( Intervention i : q.getInterventions()){
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

}
