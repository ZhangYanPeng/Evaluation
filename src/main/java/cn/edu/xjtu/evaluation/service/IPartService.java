package cn.edu.xjtu.evaluation.service;

import cn.edu.xjtu.evaluation.entity.Part;

public interface IPartService {

	int add(Part part);
	Part load(long id);
	int edit(Part part);
}
