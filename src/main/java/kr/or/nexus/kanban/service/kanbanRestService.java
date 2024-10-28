package kr.or.nexus.kanban.service;

import java.util.List;
import java.util.Map;

import kr.or.nexus.vo.IssueVO;

public interface kanbanRestService {

	List<IssueVO> getMyIssueList(Map<String, Object> data);
	int updateIssue(IssueVO issueVO);
	int timeLineUpdate(IssueVO issueVO);
	String addIssue(IssueVO issueVO);
	int addIssueTags(Map<String,String> tag);
	int removeIssue(String issueIdx);
	int updateTag(IssueVO issueVO);
	
}
