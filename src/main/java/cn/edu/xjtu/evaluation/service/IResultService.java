package cn.edu.xjtu.evaluation.service;

public interface IResultService {
	public double[] getTestResult(long uid, long tid); 
	public double[] getEvaluationResult(long uid, long tid);
	public double[] getTotalResult(long uid, long tid);
}
