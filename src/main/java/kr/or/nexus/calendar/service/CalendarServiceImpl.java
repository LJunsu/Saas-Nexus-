package kr.or.nexus.calendar.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.calendar.dao.CalendarMapper;
import kr.or.nexus.vo.CustomerBoardVO;
import kr.or.nexus.vo.GitVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;

@Service
public class CalendarServiceImpl implements CalendarService {

	@Autowired
	private CalendarMapper dao;

	@Override
	public List<IssueVO> retrieveIssueList(String projectId) {
		return dao.retrieveIssueList(projectId);
	}

	@Override
	public IssueVO retrieveIssue(String issueIdx) {
		return dao.retrieveIssue(issueIdx);
	}

	@Override
	public List<ProjectMemberVO> retrieveProjectMemberList(String projectId) {
		return dao.retrieveProjectMemberList(projectId);
	}

	@Override
	public void insertIssue(IssueVO issueVO) {
		dao.insertIssue(issueVO);
	}

	@Override
	public void updateIssue(IssueVO issueVO) {
		dao.updateIssue(issueVO);
		if(issueVO.getIssueTags() != null && issueVO.getIssueTags().size() > 0) {
			dao.updateIssueTag(issueVO);
		}
	}

	@Override
	public void deleteIssue(String issueIdx) {
		dao.deleteIssue(issueIdx);
	}

	@Override
	public void insertGit(GitVO gitVO) {
		dao.insertGit(gitVO);
	}

	@Override
	public GitVO retrieveGit(String projectId) {
		return dao.retrieveGit(projectId);
	}
}
