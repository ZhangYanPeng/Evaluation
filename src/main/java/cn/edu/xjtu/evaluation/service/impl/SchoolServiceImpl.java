package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.SchoolDAOImpl;
import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.entity.University;
import cn.edu.xjtu.evaluation.service.ISchoolService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class SchoolServiceImpl implements ISchoolService {
	@Autowired
	SchoolDAOImpl schoolDAO;
	
	@Override
	@Transactional
	public int add(School school) {
		// TODO Auto-generated method stub
		try {
			schoolDAO.save(school);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int delete(long id) {
		// TODO Auto-generated method stub
		try {
			School school = schoolDAO.load(id);
			if(school.getOrganizations()==null || school.getOrganizations().size()==0){
				schoolDAO.delete(school);
			}else{
				return -1;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public PageResults<School> list(int page, long university) {
		// TODO Auto-generated method stub
		String hql = "from School ";
		String countHql = "select count(*) from School ";
		if(university>=0){
			hql += "where university.id = ?";
			countHql += "where university.id = ?";
			Object[] values = {university};
			return schoolDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}else{
			Object[] values = {};
			return schoolDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}
	}

	@Override
	@Transactional
	public int edit(School school) {
		// TODO Auto-generated method stub
		try {
			schoolDAO.update(school);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public School load(Long id) {
		// TODO Auto-generated method stub
		return schoolDAO.get(id);
	}

	@Override
	@Transactional
	public List getAll(long u_id) {
		// TODO Auto-generated method stub
		String hqlString = "from School where university.id = ?";
		Object[] values = {u_id};
		return schoolDAO.getListByHQL(hqlString, values);
	}

}
