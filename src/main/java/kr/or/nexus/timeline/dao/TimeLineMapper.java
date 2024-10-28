package kr.or.nexus.timeline.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;

@Mapper
public interface TimeLineMapper {

	/**
	 * 파라미터로 넘긴 projectId 값으로 타임라인에 사용할 이슈 리스트 조회
	 */
	public List<IssueVO> timeLineList(String projectId);

	/**
	 * 파라미터로 넘긴 issueVO 값으로 타임라인에서 이슈를 생성
	 */
	public int timeLineInsert(IssueVO issueVO);

	/**
	 * 파라미터로 넘긴 projectId 값으로 타임라인 생성 시 사용할 프로젝트 명단 조회
	 */
	public List<ProjectMemberVO> timeLineProjectMemberList(String projectId);

	/**
	 * 파라미터로 넘긴 projectId 값으로 타임라인 생성 시 하위 이슈로 추가하기 위한 이슈 리스트 조회
	 */
	public List<IssueVO> timeLineIssueList(String projectId);

	public List<IssueVO> timeLineIssueListDisab(IssueVO issueVO);

	/**
	 * 타임라인에서 선택한 컬럼에 대한 이슈 상세 조회
	 */
	public IssueVO timeLineUpdateInputData(String issueIdx);

	/**
	 * 타임라인에서 선택한 컬럼에 대한 이슈 수정
	 */
	public int timeLineUpdate(IssueVO issueVO);

	/**
	 * 타임라인에서 선택한 컬럼에 대한 이슈 태그 수정
	 */
	public int timeLineTagUpdate(IssueVO issueVO);

	/**
	 * 검색한 타임라인에 해당하는 이슈 리스트 조회
	 */
	public List<IssueVO> timeLineSearch(IssueVO issueVO);

	/**
	 * 타임라인에 해당 담당자의 이슈 리스트 조회
	 */
	public List<IssueVO> timeLineMemSelect(IssueVO issueVO);

	/**
	 * 타임라인 상세 보기 시 버튼 활성화를 위한 현재 로그인 한 회원의 해당 프로젝트 멤버 아이디 제공
	 */
	public ProjectMemberVO timeLineOfficerChk(ProjectMemberVO projectMemberVO);

	/**
	 * 타임라인에서 issueIdx로 이슈 삭제
	 */
	public int timeLineDelete(String issueIdx);

	public ProjectMemberVO projectMemId(ProjectMemberVO projectMemberVO);

	public List<IssueVO> timelineChart(IssueVO issueVO);
	public List<IssueVO> timelinePriorityChart(IssueVO issueVO);
	public List<IssueVO> timelineStatusChart(IssueVO issueVO);

}
