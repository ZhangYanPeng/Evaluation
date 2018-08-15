package cn.edu.xjtu.evaluation.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import cn.edu.xjtu.evaluation.entity.Answer;

@Repository
public class AnswerDAOImpl extends BaseDAOImpl<Answer, Long>{
}
