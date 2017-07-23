package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.xjtu.evaluation.dao.impl.AnswerDAOImpl;
import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.IAnswerService;

@Service
public class AnswerServiceImpl implements IAnswerService {

	@Autowired
	AnswerDAOImpl answerDAO;
	
	@Override
	public int answer(Answer answer) {
		// TODO Auto-generated method stub
		try {
			answerDAO.save(answer);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public Answer get(long id) {
		// TODO Auto-generated method stub
		try {
			return answerDAO.load(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Answer> getByStudent(Student student) {
		// TODO Auto-generated method stub
		String hql = "from Answer where student.id = ?";
		String[] values = { (new Long(student.getId())).toString()};
		return answerDAO.getListByHQL(hql,values);
	}

	@Override
	public List<Answer> getByTest(Test test) {
		// TODO Auto-generated method stub
		String hql = "from Answer where test.id = ?";
		String[] values = { (new Long(test.getId())).toString()};
		return answerDAO.getListByHQL(hql,values);
	}

}