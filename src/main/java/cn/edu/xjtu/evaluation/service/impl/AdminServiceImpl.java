package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.AdminDAOImpl;
import cn.edu.xjtu.evaluation.entity.Admin;
import cn.edu.xjtu.evaluation.service.IAdminService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	AdminDAOImpl adminDAO;
	
	@Override
	@Transactional
	public int add(Admin admin) {
		// TODO Auto-generated method stub
		try {
			adminDAO.save(admin);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int edit(Admin admin) {
		// TODO Auto-generated method stub
		try {
			adminDAO.update(admin);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public Admin login(Admin admin) {
		// TODO Auto-generated method stub
		String hql = "from Admin where username = ? and password = ?";
		String[] values = { admin.getUsername(), admin.getPassword() };
		return adminDAO.getByHQL(hql,values);
	}

	@Override
	@Transactional
	public PageResults<Admin> list( String username, int page) {
		// TODO Auto-generated method stub
		String hql = "from Admin where username like ? ";
		String countHql = "select count(*) from Admin where username like ?";
		String[] values = { username };
		return adminDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
	}

	@Override
	@Transactional
	public Admin load(long id) {
		// TODO Auto-generated method stub
		return adminDAO.get(id);
	}

	@Override
	@Transactional
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return adminDAO.deleteById(id);
	}

}
