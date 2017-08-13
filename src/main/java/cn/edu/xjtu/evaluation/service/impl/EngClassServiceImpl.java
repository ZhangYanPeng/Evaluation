package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.EngClassDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.entity.EngClass;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.service.IEngClassService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class EngClassServiceImpl implements IEngClassService {
	@Autowired
	EngClassDAOImpl engClassDAO;
	@Autowired
	StudentDAOImpl studentDAO;
	
	@Override
	@Transactional
	public PageResults<EngClass> list(int page) {
		// TODO Auto-generated method stub
		String hql = "from EngClass ";
		String countHql = "select count(*) from EngClass";
		String[] values = {};
		return engClassDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
	}

	@Override
	@Transactional
	public EngClass get(long id) {
		// TODO Auto-generated method stub
		return engClassDAO.get(id);
	}

	@Override
	@Transactional
	public int add(EngClass engClass) {
		// TODO Auto-generated method stub
		try {
			engClassDAO.save(engClass);
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
		try {
			List<Student> sList = engClassDAO.get(id).getStudents();
			for( Student student : sList) {
				studentDAO.delete(student);
			}
			engClassDAO.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int edit(EngClass engClass) {
		// TODO Auto-generated method stub
		try {
			engClassDAO.update(engClass);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

}
