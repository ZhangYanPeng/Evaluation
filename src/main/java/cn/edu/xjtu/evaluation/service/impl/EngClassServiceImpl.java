package cn.edu.xjtu.evaluation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.EngClassDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.SchoolDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.entity.EngClass;
import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.service.IEngClassService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class EngClassServiceImpl implements IEngClassService {
	@Autowired
	EngClassDAOImpl engClassDAO;
	@Autowired
	StudentDAOImpl studentDAO;
	@Autowired
	SchoolDAOImpl schoolDAO;
	
	@Override
	@Transactional
	public PageResults<EngClass> list(int page, long university) {
		// TODO Auto-generated method stub
		if(university <0) {
			String hql = "from EngClass ";
			String countHql = "select count(*) from EngClass";
			String[] values = {};
			return engClassDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}
		else {
			String hql = "from EngClass where university.id = ? ";
			String countHql = "select count(*) from EngClass where university.id = ? ";
			Object[] values = {university};
			return engClassDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}
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

	@Override
	@Transactional
	public int importStudent(List<String[]> s_info, long id) {
		// TODO Auto-generated method stub
		EngClass engClass = engClassDAO.get(id);
		List<Student> sl = new ArrayList<Student>();
		for(String[] str : s_info){
			if(!str[3].equals(engClass.getName())){
				return -1;
			}
			Student s = new Student();
			s.setStudent_no(str[0]);
			s.setName(str[1]);
			s.setEngClass(engClass);
			s.setUsername(str[0]);
			s.setPassword("s111111");
			String shql = "from School where name = ?";
			Object[] svalues = {str[2]};
			School school = schoolDAO.getByHQL(shql, svalues);
			s.setSchool(school);
			s.setEngClass(engClass);
			sl.add(s);
		}
		for( Student s : sl){
			studentDAO.save(s);
		}
		return 1;
	}

	@Override
	@Transactional
	public int getStuNum(EngClass ec) {
		// TODO Auto-generated method stub
		String hql = "from Student where engClass.id = ?";
		Object[] values = {ec.getId()};
		return studentDAO.getListByHQL(hql, values).size();
	}

	@Override
	@Transactional
	public List<EngClass> listAll() {
		// TODO Auto-generated method stub
		String hql = "from EngClass ";
		Object[] values = {};
		return engClassDAO.getListByHQL(hql, values);
	}

	@Override
	@Transactional
	public EngClass getByName(String engclass_name) {
		// TODO Auto-generated method stub
		String hql = "from EngClass where name = ?";
		Object[] values = {engclass_name};
		return engClassDAO.getByHQL(hql, values);
	}

}
