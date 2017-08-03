package cn.edu.xjtu.evaluation.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	public int remove(Exercise exercise) {
		// TODO Auto-generated method stub
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
			Exercise e = exerciseDAO.load(id);
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
	public PageResults<Exercise> getPageList(Integer page, String type) {
		// TODO Auto-generated method stub
		String hql = "from Exercise e where e.exerciseType.description = ? ";
		String countHql = "select count(*) from Exercise";
		String[] values = {type};
		return exerciseDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
	}


}
