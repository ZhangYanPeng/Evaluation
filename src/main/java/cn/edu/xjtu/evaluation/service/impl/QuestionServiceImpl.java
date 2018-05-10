package cn.edu.xjtu.evaluation.service.impl;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.AudioDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.ExerciseDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.InterventionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.PartDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.QuestionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.TestDAOImpl;
import cn.edu.xjtu.evaluation.entity.Audio;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.service.IQuestionService;

@Service
public class QuestionServiceImpl implements IQuestionService {
	@Autowired
	ExerciseDAOImpl exerciseDAO;
	@Autowired
	QuestionDAOImpl questionDAO;
	@Autowired
	InterventionDAOImpl interventionDAO;
	@Autowired
	AudioDAOImpl audioDAO;
	
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
	public int remove(long id) {
		// TODO Auto-generated method stub
		Question question = questionDAO.get(id);
		try {
			Iterator it = question.getInterventions().iterator();
			while(it.hasNext()){
				Intervention i = (Intervention) it.next();
				Audio aud = i.getAudio();
				i.setAudio(null);
				interventionDAO.update(i);
				if(aud != null){
					File file = new File(aud.getPath());
					if (file.exists()) {
						file.delete();
					}
					audioDAO.deleteById(aud.getId());
				}
				interventionDAO.delete(i);
			}
			int qn = question.getQ_num();
			for(Question q : question.getExercise().getQuestions()) {
				if( q.getQ_num() > qn ) {
					q.setQ_num(q.getQ_num()-1);
					questionDAO.update(q);
				}
			}
			
			Audio aud = question.getAudio();
			question.setAudio(null);
			questionDAO.update(question);
			if(aud != null){
				File file = new File(aud.getPath());
				if (file.exists()) {
					file.delete();
				}
				audioDAO.deleteById(aud.getId());
			}
			questionDAO.delete(question);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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

	@Override
	@Transactional
	public int add(long id) {
		// TODO Auto-generated method stub
		Exercise e = exerciseDAO.get(id);
		Question q = new Question();
		q.setQ_num(e.getQuestions().size());
		q.setOptions("||||||||||");
		Set<Intervention> is = new HashSet<Intervention>();
		for(int i=0; i<4; i++) {
			Intervention in = new Intervention();
			in.setLevel(i);
			in.setQuestion(q);
			interventionDAO.save(in);
			is.add(in);
		}
		q.setInterventions(is);
		q.setExercise(e);
		questionDAO.save(q);
		e.getQuestions().add(q);
		exerciseDAO.update(e);
		return 0;
	}

	@Override
	@Transactional
	public Question load(long id) {
		// TODO Auto-generated method stub
		return questionDAO.get(id);
	}

}
