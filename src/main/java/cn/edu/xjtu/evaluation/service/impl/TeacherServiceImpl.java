package cn.edu.xjtu.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.TeacherDAOImpl;
import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.service.ITeacherService;

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

}
