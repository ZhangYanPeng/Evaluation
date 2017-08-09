package cn.edu.xjtu.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.OrganizationDAOImpl;
import cn.edu.xjtu.evaluation.entity.Organization;
import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.service.IOrganizationService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class OrganizationServiceImpl implements IOrganizationService{
	@Autowired
	OrganizationDAOImpl organizationDAO;

	@Override
	@Transactional
	public int add(Organization organization) {
		// TODO Auto-generated method stub
		try {
			organizationDAO.save(organization);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int edit(Organization organization) {
		// TODO Auto-generated method stub
		try {
			organizationDAO.update(organization);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public PageResults<Organization> list(int page, long sid) {
		// TODO Auto-generated method stub
		String hql = "from Organization ";
		String countHql = "select count(*) from Organization ";
		if(sid>=0){
			hql += "where school.id = ?";
			countHql += "where school.id = ?";
			Object[] values = {sid};
			return organizationDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}else{
			Object[] values = {};
			return organizationDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}
	}

	@Override
	@Transactional
	public Organization load(long id) {
		// TODO Auto-generated method stub
		return organizationDAO.get(id);
	}
}
