package cn.edu.xjtu.evaluation.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.AnswerDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.ExerciseDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.InterventionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.PartDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.PartExerDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.QuestionDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.RecordDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.TestDAOImpl;
import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.Exercise;
import cn.edu.xjtu.evaluation.entity.Intervention;
import cn.edu.xjtu.evaluation.entity.Part;
import cn.edu.xjtu.evaluation.entity.PartExer;
import cn.edu.xjtu.evaluation.entity.Question;
import cn.edu.xjtu.evaluation.entity.Record;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.ITestService;
import cn.edu.xjtu.evaluation.support.PageResults;

@Service
public class TestServiceImpl implements ITestService {

	@Autowired
	TestDAOImpl testDAO;
	@Autowired
	PartDAOImpl partDAO;
	@Autowired
	ExerciseDAOImpl exerciseDAO;
	@Autowired
	QuestionDAOImpl questionDAO;
	@Autowired
	InterventionDAOImpl interventionDAO;
	@Autowired
	AnswerDAOImpl answerDAO;
	@Autowired
	RecordDAOImpl recordDAO;
	@Autowired
	StudentDAOImpl studentDAO;
	@Autowired
	PartExerDAOImpl partExerDAO;

	@Override
	@Transactional
	public Test add(Test test) {
		// TODO Auto-generated method stub
		testDAO.save(test);
		return test;
	}

	@Override
	@Transactional
	public int remove(Long id) {
		// TODO Auto-generated method stub
		try {
			Test t = testDAO.get(id);
			for (Part p : t.getParts()) {
				for (PartExer pe : p.getPartExers()) {
					Exercise e = pe.getExercise();
					for (Question q : e.getQuestions()) {
						for (Intervention i : q.getInterventions()) {
							interventionDAO.delete(i);
						}
						questionDAO.delete(q);
					}
					exerciseDAO.delete(e);
					partExerDAO.delete(pe);
				}
				partDAO.delete(p);
			}
			testDAO.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public Test get(long id) {
		// TODO Auto-generated method stub
		try {
			return testDAO.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public List<Test> getAll() {
		// TODO Auto-generated method stub
		String hql = "from Test";
		return testDAO.getListByHQL(hql, null);
	}

	@Override
	@Transactional
	public PageResults<Test> list(Integer page) {
		// TODO Auto-generated method stub
		String hql = "from Test";
		String countHql = "select count(*) from Test";
		Object[] values = {};
		return testDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
	}

	@Override
	@Transactional
	public int importTest(Test test) {
		return 1;
	}

	@Override
	@Transactional
	public int chooseTest(long id) {
		// TODO Auto-generated method stub
		String hql = "from Test where choose = 1";
		List<Test> tl = testDAO.getListByHQL(hql, null);
		if (tl != null) {
			for (Test t : tl) {
				t.setChoose(0);
				testDAO.update(t);
			}
		}
		Test t = testDAO.get(id);
		t.setChoose(1);
		testDAO.update(t);
		return 0;
	}

	@Override
	@Transactional
	public int check(Integer type, Long tid, Long uid) {
		// TODO Auto-generated method stub
		String hql = "from Answer where type = ? and student.id = ? and test.id = ?";
		Object[] values = { type, uid, tid };
		Answer a = answerDAO.getByHQL(hql, values);
		if (a == null)
			return 0;
		return 1;
	}

	@Override
	@Transactional
	public Test getChoose() {
		// TODO Auto-generated method stub
		String hql = "from Test where choose = 1";
		return testDAO.getByHQL(hql, null);
	}

	@Override
	@Transactional
	public int finishTest(int type, long tid, long uid, String[] records, String[] reasons, String stime, String etime) {
		// TODO Auto-generated method stub
		Answer answer = new Answer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d H:m:s");
		try {
			answer.setStart_time(sdf.parse(stime));
			answer.setEnd_time(sdf.parse(etime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		answer.setType(type);
		answer.setTest(testDAO.get(tid));
		answer.setStudent(studentDAO.get(uid));
		answerDAO.save(answer);
		int c_pn=0;
		int c_en=0;
		int c_qn=0;
		boolean check = true;
		for(int i=0; i<records.length; i++){
			check = true;
			for (Part p : testDAO.get(tid).getParts()) {
				if(!check)
					break;
				for (PartExer pe : p.getPartExers() ) {
					if(!check)
						break;
					for (Question q : pe.getExercise().getQuestions()) {
						if(!check)
							break;
						if(p.getP_no()==c_pn && pe.getE_no()==c_en && q.getQ_num()==c_qn){
							Record record = new Record();
							record.setAnswer(answer);
							record.setQuestion(q);
							record.setResult(records[i]);
							record.setReason(reasons[i]);
							record.setNo(i);
							recordDAO.save(record);
							c_qn++;
							if(c_qn == pe.getExercise().getQuestions().size()){
								c_qn=0;
								c_en++;
								if(c_en == p.getPartExers().size()){
									c_en=0;
									c_pn++;
								}
							}
							check = false;
							break;
						}
					}
				}
			}
		}
		return 0;
	}

	@Override
	@Transactional
	public List<Part> loadParts(Long id) {
		// TODO Auto-generated method stub
		String hqlString = "from Part where test.id = ? order by p_no asc";
		Object[] values = { id };
		return partDAO.getListByHQL(hqlString, values);
	}

	@Override
	@Transactional
	public List<Exercise> loadExercises(Long id) {
		// TODO Auto-generated method stub
		String hqlString = "from Exercise where part.id = ? order by e_no asc";
		Object[] values = { id };
		return exerciseDAO.getListByHQL(hqlString, values);
	}

	@Override
	@Transactional
	public List<Question> loadQuestions(Long id) {
		// TODO Auto-generated method stub
		String hqlString = "from Question where exercise.id = ? order by q_no asc";
		Object[] values = { id };
		return questionDAO.getListByHQL(hqlString, values);
	}

	@Override
	@Transactional
	public List<Intervention> loadInterventions(Long id) {
		// TODO Auto-generated method stub
		String hqlString = "from Intervention where question.id = ? order by level asc";
		Object[] values = { id };
		return interventionDAO.getListByHQL(hqlString, values);
	}

	@Override
	@Transactional
	public int collect(long id, Integer state) {
		// TODO Auto-generated method stub
		Test t = testDAO.get(id);
		t.setCollect(state);
		testDAO.update(t);
		return 0;
	}

	@Override
	@Transactional
	public int addExercise(long id, long eid) {
		// TODO Auto-generated method stub
		Test test = testDAO.get(id);
		Exercise exercise = exerciseDAO.get(eid);
		int check = 0;
		for (Part p : test.getParts()) {
			if (p.getPartExers().iterator().next().getExercise().getType().getId() == exercise.getType().getId()) {
				PartExer pe = new PartExer();
				pe.setExercise(exercise);
				pe.setPart(p);
				pe.setE_no(p.getPartExers().size());
				partExerDAO.save(pe);
				check = 1;
				break;
			}
		}
		if (check == 0) {
			Part p = new Part();
			p.setP_no(test.getParts().size());
			p.setDescription(exercise.getType().getName());
			p.setTest(test);
			partDAO.save(p);
			PartExer pe = new PartExer();
			pe.setExercise(exercise);
			pe.setPart(p);
			pe.setE_no(0);
			partExerDAO.save(pe);
		}
		return 0;
	}

	@Override
	@Transactional
	public int removeExercise(long id, long eid) {
		// TODO Auto-generated method stub
		Test test = testDAO.get(id);
		for (Part p : test.getParts()) {
			for (PartExer pe : p.getPartExers()) {
				if (pe.getId() == eid) {
					for (PartExer tpe : p.getPartExers()) {
						if (tpe.getE_no() > pe.getE_no()) {
							tpe.setE_no(tpe.getE_no() - 1);
							partExerDAO.update(tpe);
						}
					}
					partExerDAO.delete(pe);
					if (p.getPartExers().size() == 1) {
						for (Part tp : test.getParts()) {
							if (tp.getP_no() > p.getP_no()) {
								tp.setP_no(tp.getP_no() - 1);
								partDAO.update(tp);
							}
						}
						partDAO.delete(p);
					}
				}
			}
		}

		return 0;
	}

}
