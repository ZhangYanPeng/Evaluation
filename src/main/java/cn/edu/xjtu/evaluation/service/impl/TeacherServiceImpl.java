package cn.edu.xjtu.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.xjtu.evaluation.dao.impl.TeacherDAOImpl;
import cn.edu.xjtu.evaluation.entity.Teacher;
import cn.edu.xjtu.evaluation.service.ITeacherService;

@Service
public class TeacherServiceImpl implements ITeacherService{

	@Autowired
	TeacherDAOImpl teacherDAO;
	
	@Override
	public Teacher login(Teacher teacher) {
		// TODO Auto-generated method stub
		String hql = "from Student where username = ? and password = ?";
		String[] values = { teacher.getUsername(),  teacher.getPassword() };
		return teacherDAO.getByHQL(hql,values);
	}

	@Override
	public int register(Teacher teacher) {
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

}
