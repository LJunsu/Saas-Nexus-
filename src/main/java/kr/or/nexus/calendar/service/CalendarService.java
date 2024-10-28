package kr.or.nexus.calendar.service;

import java.util.List;

import kr.or.nexus.vo.GitVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;

/**
 * @author PC-22
 *
 */
public interface CalendarService {

	/**
	 * 이슈 리스트 조회
	 * @return
	 */
	public List<IssueVO> retrieveIssueList(String projectId);

	/**
	 * 이슈 상세 조회
	 * @param issueIdx
	 * @return
	 */
	public IssueVO retrieveIssue(String issueIdx);

	/**
	 * 프로젝트별 멤버 조회
	 * @param projectId
	 * @return
	 */
	public List<ProjectMemberVO> retrieveProjectMemberList(String projectId);

	/**
	 * 이슈 insert
	 * @param issueVO
	 */
	public void insertIssue(IssueVO issueVO);

	/**
	 * 이슈 update
	 * @param issueVO
	 */
	public void updateIssue(IssueVO issueVO);

	/**
	 * 이슈 delete
	 * @param issueIdx
	 */
	public void deleteIssue(String issueIdx);

	/**
	 * 프로젝트별 깃연결 디테일 저장
	 * @param gitVO
	 */
	public void insertGit(GitVO gitVO);

	/**
	 * 프로젝트별 연결된 깃허브 디테일 조회
	 * @param projectId
	 * @return
	 */
	public GitVO retrieveGit(String projectId);

}