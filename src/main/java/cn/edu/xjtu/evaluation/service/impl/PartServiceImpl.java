package cn.edu.xjtu.evaluation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.PartDAOImpl;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.service.IPartService;

@Service
public class PartServiceImpl implements IPartService{
	@Autowired
	PartDAOImpl partDAO;

	@Override
	@Transactional
	public int add(Part part) {
		// TODO Auto-generated method stub
		partDAO.save(part);
		return 0;
	}

	@Override
	@Transactional
	public Part load(long id) {
		// TODO Auto-generated method stub
		return partDAO.get(id);
	}

	@Override
	@Transactional
	public int edit(Part part) {
		// TODO Auto-generated method stub
		try {
			partDAO.update(part);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}
	
	

}
