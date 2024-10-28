package kr.or.nexus.calendar.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.vo.CustomerBoardAnswersVO;
import kr.or.nexus.vo.CustomerBoardVO;
import kr.or.nexus.vo.GitVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;

@Mapper
public interface CalendarMapper {

	public List<IssueVO> retrieveIssueList(String projectId);

	public IssueVO retrieveIssue(String issueIdx);

	public List<ProjectMemberVO> retrieveProjectMemberList(String projectId);

	public void insertIssue(IssueVO issueVO);

	public void updateIssue(IssueVO issueVO);

	public void updateIssueTag(IssueVO issueVO);
	
	public void deleteIssue(String issueIdx);

	public void insertGit(GitVO gitVO);

	public GitVO retrieveGit(String projectId);
}
