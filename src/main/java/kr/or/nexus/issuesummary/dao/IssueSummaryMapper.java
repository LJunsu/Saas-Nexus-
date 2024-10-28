package kr.or.nexus.issuesummary.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectVO;

@Mapper
public interface IssueSummaryMapper {

	/**
	 * 시작 날짜와 끝 날짜 사이의 이슈에 대한 이슈 완료 수, 총 이슈 수를 조회
	 */
	public List<IssueVO> issueSummaryDateChart(IssueVO issueVO);

	/**
	 * 시작 날짜와 끝 날짜 사이의 이슈에 대한 각 우선순위의 수 조회
	 */
	public List<IssueVO> issueSummaryPriorityChart(IssueVO issueVO);

	/**
	 * 시작 날짜와 끝 날짜 사이의 이슈에 대한 각 상태의 수 조회
	 */
	public List<IssueVO> issueSummaryStatusChart(IssueVO issueVO);

	/**
	 * 해당 프로젝트에 해당하는 멤버 정보 조회
	 */
	public List<ProjectMemberVO> projectMemberInformation(String projectId);

	/**
	 * 프로젝트 아이디로 프로젝트 이름 조회
	 */
	public ProjectVO issueProjectName(String projectId);

	/**
	 * 그룹 아이디로 그룹 이름 조회
	 */
	public GroupManagementVO issueGroupName(String groupId);

	/**
	 * 그룹 아이디로 해당 그룹의 프로젝트 리스트 조회
	 */
	public List<ProjectVO> projectList(String groupId);

	public ProjectVO projectDetail(String projectId);

	/**
	 * 프로젝트 아이디로 해당 프로젝트의 멤버 수 조회
	 */
	public int projectMemCount(String projectId);

	/**
	 * 프로젝트 아이디로 해당 프로젝트의 리더 조회
	 */
	public List<ProjectMemberVO> projectLeader(String projectId);

	/**
	 * 프로젝트 아이디로 해당 프로젝트의 이슈 상태 별 개수 조회
	 */
	public List<IssueVO> issueSummaryStatus(String projectId);

	/**
	 * 프로젝트 아이디로 해당 프로젝트의 이슈 완료 퍼센트 조회
	 */
	public Double issueCompletedPercentage(String projectId);

}
