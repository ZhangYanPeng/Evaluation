package cn.edu.xjtu.evaluation.service;

import cn.edu.xjtu.evaluation.entity.Organization;
import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.support.PageResults;

public interface IOrganizationService {

	int add(Organization organization);

	int edit(Organization organization);

	PageResults<Organization> list(int page, long uid, long sid);

	Organization load(long id);

}
