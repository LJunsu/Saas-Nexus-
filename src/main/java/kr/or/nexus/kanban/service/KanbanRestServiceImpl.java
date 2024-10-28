package kr.or.nexus.kanban.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.kanban.dao.KanbanRestMapper;
import kr.or.nexus.timeline.dao.TimeLineMapper;
import kr.or.nexus.vo.IssueVO;

@Service
public class KanbanRestServiceImpl implements kanbanRestService {

	@Autowired
	private KanbanRestMapper mapper;
	
	@Autowired
	private TimeLineMapper timeLineMapper;
	
	
	
	public List<IssueVO> getMyIssueList(Map<String, Object> data){
			return mapper.getMyIssueList(data);
	}
	
	
	public int updateIssue(IssueVO issueVO) {
		
		return mapper.updateIssue(issueVO);
	}
	
	
	public int timeLineUpdate(IssueVO issueVO) {

		int issueChk = mapper.timeLineUpdate(issueVO);


		return issueChk;

	}
	
	
	public String addIssue(IssueVO issueVO) {

		mapper.addIssue(issueVO);


		return (String)issueVO.getIssueIdx();

	}
	
	
	public int addIssueTags(Map<String,String> tag) {
		
		
		return mapper.addIssueTags(tag);
	}
	
public int removeIssue(String issueIdx) {
		
		
		return mapper.removeIssue(issueIdx);
	}
public int updateTag(IssueVO issueVO) {
	
	return mapper.updateTag(issueVO);
}
	
	
	
}
