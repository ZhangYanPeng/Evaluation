package cn.edu.xjtu.evaluation.service.impl;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.InterventionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.QuestionDAOImpl;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.service.IQuestionService;

@Service
public class QuestionServiceImpl implements IQuestionService {

	@Autowired
	QuestionDAOImpl questionDAO;
	@Autowired
	InterventionDAOImpl interventionDAO;
	
	@Override
	@Transactional
	public int add(Exercise exercise, Question question) {
		// TODO Auto-generated method stub
		try {
			question.setExercise(exercise);
			questionDAO.saveOrUpdate(question);
			if( question.getInterventions() != null ){
				Iterator it = question.getInterventions().iterator();
				while(it.hasNext()){
					Intervention i = (Intervention) it.next();
					interventionDAO.saveOrUpdate(i);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			return 1;
		}
		return 0;
	}

	@Override
	@Transactional
	public int remove(Question question) {
		// TODO Auto-generated method stub
		try {
			Iterator it = question.getInterventions().iterator();
			while(it.hasNext()){
				Intervention i = (Intervention) it.next();
				interventionDAO.delete(i);
			}
			questionDAO.delete(question);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int edit(Question question) {
		// TODO Auto-generated method stub
		try {
			questionDAO.update(question);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

}
