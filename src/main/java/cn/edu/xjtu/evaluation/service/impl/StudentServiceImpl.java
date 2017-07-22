package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.service.IStudentService;

@Service
public class StudentServiceImpl implements IStudentService {
	
	@Autowired
	StudentDAOImpl studentDAO;

	@Override
	@Transactional
	public int add(Student student) {
		// TODO Auto-generated method stub
		try {
			studentDAO.save(student);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int active(Student student) {
		// TODO Auto-generated method stub
		try {
			student.setStatus(1);
			studentDAO.saveOrUpdate(student);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int frozen(Student student) {
		// TODO Auto-generated method stub
		try {
			student.setStatus(0);
			studentDAO.saveOrUpdate(student);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public Student login(Student student) {
		// TODO Auto-generated method stub
		String hql = "from Student where ( username = ? or student_no = ? ) and password = ?";
		String[] values = { student.getUsername(), student.getStudent_no(), student.getPassword() };
		return studentDAO.getByHQL(hql,values);
	}

	@Override
	@Transactional
	public Student get(long id) {
		// TODO Auto-generated method stub
		try {
			return studentDAO.load(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public List<Student> getAll() {
		// TODO Auto-generated method stub
		String hql = "from Student";
		return studentDAO.getListByHQL(hql, null);
	}

}
