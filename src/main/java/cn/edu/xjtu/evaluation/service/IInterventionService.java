package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Question;

public interface IInterventionService {
	int add(Question question,Intervention intervention);
	int remove(Intervention intervention);
	int edit(Intervention intervention);
	Intervention load(long id);
}
