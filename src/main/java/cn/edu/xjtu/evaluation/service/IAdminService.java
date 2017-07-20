package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Admin;

public interface IAdminService {
	int add(Admin admin);
	int edit(Admin admin);
	Admin login(Admin admin);
	List<Admin> list();
}
