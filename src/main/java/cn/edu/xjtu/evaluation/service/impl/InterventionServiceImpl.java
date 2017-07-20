package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.InterventionDAOImpl;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.service.IInterventionService;

@Service
public class InterventionServiceImpl implements IInterventionService {

	@Autowired
	InterventionDAOImpl interventionDAO;
	
	@Override
	@Transactional
	public int add(Question question, Intervention intervention) {
		// TODO Auto-generated method stub
		intervention.setQuestion(question);
		try {
			interventionDAO.saveOrUpdate(intervention);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int remove(Intervention intervention) {
		// TODO Auto-generated method stub
		try {
			interventionDAO.delete(intervention);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int edit(Intervention intervention) {
		// TODO Auto-generated method stub
		try {
			interventionDAO.saveOrUpdate(intervention);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

}
