package kr.or.nexus.member.main.service;

import java.util.List;
import java.util.Map;

import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;

/**
 * @author PC-22
 *
 */
public interface MemberMainService {

	/**
	 * 멤버가 소속되어있는 그룹Id 조회
	 * @param memberId
	 * @return
	 */
	public String retrieveGroupId(String memberId);

	/**
	 * 멤버가 포함되어있는 프로젝트 조회
	 * @return
	 */
	public List<Map<String, Object>> retrieveProjectList(Map<String, String> params);

	/**
	 * 멤버에게 할당되어있는 이슈리스트 조회
	 * @param memberId
	 * @return
	 */
	public List<IssueVO> retrieveIssueList(List<ProjectMemberVO> proMemList);

	public List<IssueVO> memAllIssue(String memberId);

	public List<ProjectMemberVO> memIdProMemId(String memberId);
}