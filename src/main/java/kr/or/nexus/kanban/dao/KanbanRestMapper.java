package kr.or.nexus.kanban.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.IssueVO;

@Mapper
public interface KanbanRestMapper {

	
	List<IssueVO> getMyIssueList(Map<String, Object> data);
	
	int updateIssue(IssueVO issueVO);
	
	int timeLineUpdate(IssueVO issueVO);
	int addIssue(IssueVO issueVO);
	int addIssueTags(Map<String,String> tag);
	int removeIssue(@Param("issueIdx")String issueIdx);
	int updateTag(IssueVO issueVO);
	
	
}
