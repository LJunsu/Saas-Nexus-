package kr.or.nexus.issuesummary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.issuesummary.dao.IssueSummaryMapper;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectVO;

@Service
public class IssueSummaryServiceImpl implements IssueSummaryService {

	@Autowired
	private IssueSummaryMapper issueSummaryMapper;

	@Override
	public List<IssueVO> issueSummaryDateChart(IssueVO issueVO) {

		return issueSummaryMapper.issueSummaryDateChart(issueVO);

	}

	@Override
	public List<IssueVO> issueSummaryPriorityChart(IssueVO issueVO) {

		return issueSummaryMapper.issueSummaryPriorityChart(issueVO);

	}

	@Override
	public List<IssueVO> issueSummaryStatusChart(IssueVO issueVO) {

		return issueSummaryMapper.issueSummaryStatusChart(issueVO);

	}

	@Override
	public List<ProjectMemberVO> projectMemberInformation(String projectId) {

		return issueSummaryMapper.projectMemberInformation(projectId);

	}

	@Override
	public ProjectVO issueProjectName(String projectId) {

		return issueSummaryMapper.issueProjectName(projectId);

	}

	@Override
	public GroupManagementVO issueGroupName(String groupId) {

		return issueSummaryMapper.issueGroupName(groupId);

	}

	@Override
	public List<ProjectVO> projectList(String groupId) {

		return issueSummaryMapper.projectList(groupId);

	}

	@Override
	public ProjectVO projectDetail(String projectId) {

		return issueSummaryMapper.projectDetail(projectId);

	}

	@Override
	public int projectMemCount(String projectId) {

		return issueSummaryMapper.projectMemCount(projectId);

	}

	@Override
	public List<ProjectMemberVO> projectLeader(String projectId) {

		return issueSummaryMapper.projectLeader(projectId);

	}

	@Override
	public List<IssueVO> issueSummaryStatus(String projectId) {

		return issueSummaryMapper.issueSummaryStatus(projectId);

	}

	@Override
	public Double issueCompletedPercentage(String projectId) {

		return issueSummaryMapper.issueCompletedPercentage(projectId);

	}

}
