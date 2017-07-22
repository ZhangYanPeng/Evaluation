package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.PartDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.TestDAOImpl;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.ITestService;

@Service
public class TestServiceImpl implements ITestService {

	@Autowired
	TestDAOImpl testDAO;
	@Autowired
	PartDAOImpl partDAO;
	
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

}
