package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.service.IStudentService;
import cn.edu.xjtu.evaluation.support.PageResults;

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
		return studentDAO.getByHQL(hql, values);
	}

	@Override
	@Transactional
	public Student get(long id) {
		// TODO Auto-generated method stub
		try {
			return studentDAO.get(id);
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

	@Override
	@Transactional
	public PageResults<Student> list(String str, int page) {
		// TODO Auto-generated method stub
		String hql = "from Student ";
		String countHql = "select count(*) from Student ";
		if (!str.equals("")) {
			hql += "where student_no = ? or engClass.name = ?";
			countHql += "where student_no = ? or engClass.name = ?";
			Object[] values = {str,str};
			return studentDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}
		Object[] values = {};
		return studentDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);

	}

	@Override
	@Transactional
	public int delete(long id) {
		// TODO Auto-generated method stub
		try {
			studentDAO.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int edit(Student student) {
		// TODO Auto-generated method stub
		try {
			studentDAO.update(student);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

}
