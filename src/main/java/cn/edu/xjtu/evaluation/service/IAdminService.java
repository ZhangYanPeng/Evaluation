package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Admin;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface IAdminService {
	int add(Admin admin);
	int edit(Admin admin);
	Admin login(Admin admin);
	Admin load(long id);
	PageResults<Admin> list(String username, int page);
	boolean delete(long id);
}
