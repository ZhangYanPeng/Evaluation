package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.UniversityDAOImpl;
import cn.edu.xjtu.evaluation.entity.University;
import cn.edu.xjtu.evaluation.service.IUniversityService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class UniversityServiceImpl implements IUniversityService {
	@Autowired
	UniversityDAOImpl universityDAO;

	@Override
	@Transactional
	public int add(University university) {
		// TODO Auto-generated method stub
		try {
			universityDAO.save(university);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int delete(long id) {
		// TODO Auto-generated method stub
		try {
			University university = universityDAO.load(id);
			universityDAO.delete(university);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public PageResults<University> list(int page) {
		// TODO Auto-generated method stub
		String hql = "from University";
		String countHql = "select count(*) from University";
		String[] values = {};
		return universityDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
	}

	@Override
	@Transactional
	public int edit(University university) {
		// TODO Auto-generated method stub
		try {
			universityDAO.update(university);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public University load(long id) {
		// TODO Auto-generated method stub
		return universityDAO.get(id);
	}

	@Override
	@Transactional
	public int update(University university) {
		// TODO Auto-generated method stub
		try {
			universityDAO.update(university);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public List<University> getAll() {
		// TODO Auto-generated method stub
		return universityDAO.getListByHQL("from University", null);
	}

}
