package cn.edu.xjtu.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.TeacherDAOImpl;
import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.service.ITeacherService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class TeacherServiceImpl implements ITeacherService{

	@Autowired
	TeacherDAOImpl teacherDAO;
	
	@Override
	@Transactional
	public Teacher login(Teacher teacher) {
		// TODO Auto-generated method stub
		String hql = "from Teacher where username = ? and password = ?";
		String[] values = { teacher.getUsername(),  teacher.getPassword() };
		return teacherDAO.getByHQL(hql,values);
	}

	@Override
	@Transactional
	public int register(Teacher teacher) {
		// TODO Auto-generated method stub
		try {
			String hql = "from Teacher where username = ?";
			String[] values = { teacher.getUsername()};
			if( teacherDAO.getByHQL(hql,values) != null ){
				return 0;
			}
			teacherDAO.save(teacher);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public PageResults<Teacher> list(int page) {
		// TODO Auto-generated method stub
		String hql = "from Teacher ";
		String countHql = "select count(*) from Teacher ";
		String[] values = {};
		return teacherDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
	
	}

	@Override
	@Transactional
	public Teacher get(Long id) {
		// TODO Auto-generated method stub
		return teacherDAO.get(id);
	}

	@Override
	@Transactional
	public int add(Teacher teacher) {
		// TODO Auto-generated method stub
		try {
			teacherDAO.save(teacher);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
	
	@Override
	@Transactional
	public int edit(Teacher teacher) {
		// TODO Auto-generated method stub
		try {
			teacherDAO.update(teacher);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}
}
