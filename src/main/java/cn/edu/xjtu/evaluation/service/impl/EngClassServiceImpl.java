package cn.edu.xjtu.evaluation.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.xjtu.evaluation.common.Constants;
import cn.edu.xjtu.evaluation.dao.impl.AnswerDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.EngClassDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.SchoolDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.StudentDAOImpl;
import cn.edu.xjtu.evaluation.dao.impl.TestDAOImpl;
import cn.edu.xjtu.evaluation.entity.Answer;
import cn.edu.xjtu.evaluation.entity.EngClass;
import cn.edu.xjtu.evaluation.entity.EngClassResult;
import cn.edu.xjtu.evaluation.entity.Record;
import cn.edu.xjtu.evaluation.entity.School;
import cn.edu.xjtu.evaluation.entity.Student;
import cn.edu.xjtu.evaluation.entity.Test;
import cn.edu.xjtu.evaluation.service.IEngClassService;
import cn.edu.xjtu.evaluation.support.PageResults;
import cn.edu.xjtu.evaluation.support.XlsxCreator;

@Service
public class EngClassServiceImpl implements IEngClassService {
	@Autowired
	EngClassDAOImpl engClassDAO;
	@Autowired
	StudentDAOImpl studentDAO;
	@Autowired
	SchoolDAOImpl schoolDAO;
	@Autowired
	AnswerDAOImpl answerDAO;
	@Autowired
	TestDAOImpl testDAO;
	
	@Override
	@Transactional
	public PageResults<EngClass> list(int page, long university) {
		// TODO Auto-generated method stub
		if(university <0) {
			String hql = "from EngClass ";
			String countHql = "select count(*) from EngClass";
			String[] values = {};
			return engClassDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}
		else {
			String hql = "from EngClass where university.id = ? ";
			String countHql = "select count(*) from EngClass where university.id = ? ";
			Object[] values = {university};
			return engClassDAO.findPageByFetchedHql(hql, countHql, page, Constants.PAGE_SIZE, values);
		}
	}

	@Override
	@Transactional
	public EngClass get(long id) {
		// TODO Auto-generated method stub
		return engClassDAO.get(id);
	}

	@Override
	@Transactional
	public int add(EngClass engClass) {
		// TODO Auto-generated method stub
		try {
			engClassDAO.save(engClass);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int remove(long id) {
		// TODO Auto-generated method stub
		try {
			List<Student> sList = engClassDAO.get(id).getStudents();
			for( Student student : sList) {
				studentDAO.delete(student);
			}
			engClassDAO.deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int edit(EngClass engClass) {
		// TODO Auto-generated method stub
		try {
			engClassDAO.update(engClass);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	@Transactional
	public int importStudent(List<String[]> s_info, long id) {
		// TODO Auto-generated method stub
		EngClass engClass = engClassDAO.get(id);
		List<Student> sl = new ArrayList<Student>();
		for(String[] str : s_info){
			if(!str[3].equals(engClass.getName())){
				return -1;
			}
			Student s = new Student();
			s.setStudent_no(str[0]);
			s.setName(str[1]);
			s.setEngClass(engClass);
			s.setUsername(str[0]);
			s.setPassword("s111111");
			String shql = "from School where name = ?";
			Object[] svalues = {str[2]};
			School school = schoolDAO.getByHQL(shql, svalues);
			s.setSchool(school);
			s.setEngClass(engClass);
			sl.add(s);
		}
		for( Student s : sl){
			studentDAO.save(s);
		}
		return 1;
	}

	@Override
	@Transactional
	public int getStuNum(EngClass ec) {
		// TODO Auto-generated method stub
		String hql = "from Student where engClass.id = ?";
		Object[] values = {ec.getId()};
		return studentDAO.getListByHQL(hql, values).size();
	}

	@Override
	@Transactional
	public List<EngClass> listAll() {
		// TODO Auto-generated method stub
		String hql = "from EngClass ";
		Object[] values = {};
		return engClassDAO.getListByHQL(hql, values);
	}

	@Override
	@Transactional
	public EngClass getByName(String engclass_name) {
		// TODO Auto-generated method stub
		String hql = "from EngClass where name = ?";
		Object[] values = {engclass_name};
		return engClassDAO.getByHQL(hql, values);
	}

	@Override
	@Transactional
	public List<String> getInfo(Long eid) {
		// TODO Auto-generated method stub
		EngClass ec = engClassDAO.get(eid);
		List<String> info = new ArrayList<String>();
		info.add(ec.getUniversity().getName());
		info.add(ec.getName());
		info.add(String.valueOf(ec.getStudents().size()));
		return info;
	}

	@Override
	@Transactional
	public String[][] getTestInfo(Long engid, Integer testno) {
		// TODO Auto-generated method stub
		String hql = "from Answer where test.testno = ? and student.engClass.id = ?";
		Object[] values = {testno, engid};
		List<Answer> answers = answerDAO.getListByHQL(hql, values);
		int[][] sta = new int[5][16];
		for(Answer ans : answers){
			for(Record rec : ans.getRecords()){
				sta[rec.getResult().split("\\|\\|").length-2][rec.getNo()] ++;
			}
		}
		String[][] result = new String[6][17];
		for(int i=0;i<5;i++){
			int sum_t = 0;
			for(int j=0;j<16; j++){
				result[i][j] = String.valueOf(sta[i][j]);
				sum_t += sta[i][j];
			}
			result[i][16] = String.valueOf(sum_t);
		}
		for(int j=0;j<16; j++){
			double ave_t = 0;
			for(int i=0;i<5;i++)
				ave_t += sta[i][j]*i;
			ave_t = ave_t/answers.size();
			result[5][j] = String.format("%.2f", ave_t);
		}
		return result;
	}

	@Override
	@Transactional
	public String[][] getAbilityInfo(Long engid, Integer testno) {
		// TODO Auto-generated method stub
		String[][] result = new String[5][3];
		String hql = "from Answer where test.testno = ? and student.engClass.id = ?";
		Object[] values = {testno, engid};
		List<Answer> answers = answerDAO.getListByHQL(hql, values);
		if(answers.size() == 0)
			return result;
		else{
			int[] all_inter_freq = new int[5];
			int[] avg_inter_freq = new int[5];
			for(int i=0; i<5; i++){
				all_inter_freq[i] = 0;
				avg_inter_freq[i] = 0;
				for(int j=0; j<3; j++)
					result[i][j] = "";
			}
			for(int i=0; i<answers.size(); i++){
				Answer ans = answers.get(i);
				for( Record rec : ans.getRecords()){
					int ab_t;
					switch(rec.getQuestion().getType()){
						case presentation: ab_t=0; break;
						case grammar: ab_t=1; break;
						case comprehension: ab_t=2; break;
						case details: ab_t=3; break;
						case inference: ab_t=4; break;
						default : ab_t=0; break;
					}
					if(i==0){
						if(result[ab_t][0] == "")
							result[ab_t][0] = ((Integer)(rec.getNo()+1)).toString();
						else
							result[ab_t][0] += "、" + ((Integer)(rec.getNo()+1)).toString();
					}
					all_inter_freq[ab_t] += (rec.getResult()).split("\\|\\|").length - 2;
					avg_inter_freq[ab_t] ++;
				}
			}
			for(int i=0; i<5; i++){
				result[i][1] = ((Integer)all_inter_freq[i]).toString();
				if(avg_inter_freq[i] == 0)
					result[i][2] = "0";
				else
					result[i][2] = String.format("%.2f", ((double)all_inter_freq[i]/(double)avg_inter_freq[i]));
			}
		}
		return result;
	}
	
	@Override
	@Transactional
	public List<EngClassResult> loadResult(Long ecid, Integer tno, int sortby) {
		// TODO Auto-generated method stub
		String hql = "from Student where engClass.id = ?";
		Object[] values = {ecid};
		List<Student> students = studentDAO.getListByHQL(hql, values);
		
		List<EngClassResult> lecr = new ArrayList<EngClassResult>();
		for(Student student : students){
			String hqlstring = "from Answer where student.id = ? and test.testno = ?";
			Object[] vals = {student.getId(), tno};
			Answer answer = answerDAO.getByHQL(hqlstring, vals);
			lecr.add(new EngClassResult(student, answer));
		}
		return lecr;
	}
	
	@Override
	@Transactional
	public void outputData(String id, HttpServletRequest request) {
		// TODO Auto-generated method stub

		EngClass engclass = get(Long.valueOf(id));
		List<List> stulist = new ArrayList();
		List<String[][]> testlist = new ArrayList();
		List<String[][]> ablist = new ArrayList();
		
		for(int i = 1; i<4; i++){
			List<EngClassResult> ls = loadResult(engclass.getId(), i, 0);
			stulist.add(ls);
			String[][] lt = getTestInfo(engclass.getId(), i);
			testlist.add(lt);
			String[][] la = getAbilityInfo(engclass.getId(), i);
			ablist.add(la);
		}
		
		String path = request.getSession().getServletContext().getRealPath("/download/xlsx/");
		String filename = String.valueOf(engclass.getId())+"data.xls";
		try {
			XlsxCreator.createOuputData(engclass, stulist, testlist, ablist, path + filename);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
