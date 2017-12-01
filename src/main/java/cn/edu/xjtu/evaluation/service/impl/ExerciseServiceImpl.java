package cn.edu.xjtu.evaluation.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.ExerciseDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.InterventionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.QuestionDAOImpl;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.service.IExerciseService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class ExerciseServiceImpl implements IExerciseService {

	@Autowired
	ExerciseDAOImpl exerciseDAO;
	@Autowired
	QuestionDAOImpl questionDAO;
	@Autowired
	InterventionDAOImpl interventionDAO;
	
	@Override
	@Transactional
	public int add(Exercise exercise) {
		// TODO Auto-generated method stub
		try {
			exerciseDAO.saveOrUpdate(exercise);
			if( exercise.getQuestions() != null ){
				Iterator it = exercise.getQuestions().iterator();
				while(it.hasNext()){
					Question question = (Question) it.next();
					questionDAO.saveOrUpdate(question);
					if( question.getInterventions() != null ){
						Iterator itq = question.getInterventions().iterator();
						while(itq.hasNext()){
							Intervention i = (Intervention) itq.next();
							interventionDAO.saveOrUpdate(i);
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
	public int remove(long id) {
		// TODO Auto-generated method stub
		Exercise exercise = exerciseDAO.get(id);
		try {
			Iterator it = exercise.getQuestions().iterator();
			while(it.hasNext()){
				Question question = (Question) it.next();
				Iterator itq = question.getInterventions().iterator();
				while(itq.hasNext()){
					Intervention i = (Intervention) itq.next();
					interventionDAO.delete(i);
				}
				questionDAO.delete(question);
			}
			exerciseDAO.delete(exercise);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int edit(Exercise exercise) {
		// TODO Auto-generated method stub
		try {
			exerciseDAO.update(exercise);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public Exercise get(long id) {
		// TODO Auto-generated method stub
		try {
			Exercise e = exerciseDAO.get(id);
			return e;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	@Transactional
	public List<Exercise> list() {
		// TODO Auto-generated method stub
		String hql = "from Exercise";
		return exerciseDAO.getListByHQL(hql, null);
	}

	@Override
	@Transactional
	public PageResults<Exercise> getPageList(Integer page, long type) {
		// TODO Auto-generated method stub
		if( type == -1){
			String hql = "from Exercise";
			String countHql = "select count(*) from Exercise";
			Object[] values = {};
			return exerciseDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}else{
			String hql = "from Exercise where type.id = ? ";
			String countHql = "select count(*) from Exercise where type.id = ? ";
			Object[] values = {type};
			return exerciseDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}
	}

	@Override
	@Transactional
	public Exercise create() {
		// TODO Auto-generated method stub
		Exercise e = new Exercise();
		long id = System.currentTimeMillis();
		e.setId(id);
		exerciseDAO.save(e);
		return e;
	}


}
