package cn.edu.xjtu.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.dao.impl.TypeDAOImpl;
import cn.edu.xjtu.evaluation.entity.Type;
import cn.edu.xjtu.evaluation.service.ITypeService;

@Service
public class TypeServiceImpl implements ITypeService {
	@Autowired
	TypeDAOImpl typeDAO;

	@Override
	@Transactional
	public List<Type> getTypes() {
		// TODO Auto-generated method stub
		return typeDAO.getListByHQL("from Type", null);
	}

	@Override
	@Transactional
	public int add(Type type) {
		// TODO Auto-generated method stub
		try {
			typeDAO.save(type);
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
			Type type = typeDAO.get(id);
			if(type.getExercises().size()>0)
				return -1;
			typeDAO.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int update(Type type) {
		// TODO Auto-generated method stub
		try {
			typeDAO.update(type);
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public Type load(long id) {
		// TODO Auto-generated method stub
		return typeDAO.get(id);
	}
	
	
}
