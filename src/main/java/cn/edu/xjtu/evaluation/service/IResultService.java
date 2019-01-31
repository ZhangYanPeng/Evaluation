package cn.edu.xjtu.evaluation.service;

import java.util.List;

import cn.edu.xjtu.evaluation.entity.EngClassResult;
import cn.edu.xjtu.evaluation.entity.EvaluationResult;
import cn.edu.xjtu.evaluation.entity.OverallReport;
import cn.edu.xjtu.evaluation.entity.TestResult;

public interface IResultService {
	public TestResult getTestResult(long uid, long tid); 
	public double[] getEvaluationResult(long uid, long tid);
	public double[] getTotalResult(long uid, long tid);
	public EvaluationResult getEvaluationReport(Long uid, Long tid);
	public OverallReport getOverallReport(Long valueOf);
	public List<EngClassResult> loadResult(Long ecid, Integer tno, int sortby);
}
