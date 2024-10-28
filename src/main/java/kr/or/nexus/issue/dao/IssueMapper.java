package kr.or.nexus.issue.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Option;

import kr.or.nexus.commons.def.mapper.IssueDefaultMapper;
import kr.or.nexus.vo.IssueBoardAnswersVO;
import kr.or.nexus.vo.IssueVO;

@Mapper
public interface IssueMapper extends IssueDefaultMapper{



	/**
	 * 선택 한 이슈 상세 정보 조회
	 * @param issueIdx
	 * @return
	 */
	public IssueVO selectIssue(@Param("issueIdx") String issueIdx);
	/**
	 * 이슈 추가
	 * @param issueVO
	 * @return
	 */
	public int insertIssue(IssueVO issueVO);
	/**
	 * 프로젝트 이슈 조회
	 * @param projectId
	 * @param issue_title 
	 * @return
	 */
	public List<IssueVO> projectIssueList(@Param("projectId") String projectId, @Param("issueProgressStatus") String issueProgressStatus,@Param("issueTitle") String issueTitle);

	/**
	 * 이슈 내용을 변경
	 * @param issueContent
	 * @param issueIdx
	 * @return
	 */
	public int updateIssueContent(@Param("issueContent") String issueContent, @Param("issueIdx") String issueIdx);

	/**
	 * 이슈 제목을 변경
	 * @param issueContent
	 * @param issueIdx
	 * @return
	 */
	public int updateIssueTitle(@Param("issueTitle") String issueTitle, @Param("issueIdx") String issueIdx);


	/**
	 * 이슈에 댓글을 조회
	 * @param issueIdx
	 * @return
	 */
	public List<IssueBoardAnswersVO> issueCommentList(@Param("issueIdx") String issueIdx);

	/**
	 * 상위 이슈 변경
	 * @param issueIdx
	 * @param subIssueIdx
	 * @return
	 */
	public int updateUpIssue(@Param("issueIdx") String issueIdx, @Param("subIssueIdx") String subIssueIdx);
	/**
	 * 이슈 담당자를 변경
	 * @param issueIdx
	 * @param issueOfficer
	 * @return
	 */
	public int updateOfficer(@Param("issueIdx") String issueIdx, @Param("issueOfficer") String issueOfficer);


	/**
	 * 이슈 우선순위를 변경
	 * @param issueIdx
	 * @param issuePriorityName
	 * @return
	 */
	public int updatePriority(@Param("issueIdx") String issueIdx, @Param("issuePriorityName") String issuePriorityName);
	/**
	 * 이슈 진행상태 변경
	 * @param issueIdx
	 * @param issueProgressStatus
	 * @return
	 */
	public int updateProgressStatus(@Param("issueIdx") String issueIdx, @Param("issueProgressStatus") String issueProgressStatus);


    /**
     * 이슈 시작기한 업데이트
     * @param issueDate
     * @param issueIdx
     */
	public int updateIssueStartDate(@Param("issueDate") LocalDate issueDate, @Param("issueIdx") String issueIdx);

    /**
     * 이슈 마감기한 업데이트
     * @param issueDeadline
     * @param issueIdx
     */
	public int updateIssueDeadLineDate(@Param("issueDeadline") LocalDate issueDeadline, @Param("issueIdx") String issueIdx);
	/**
	 * 이슈 태그 업데이트
	 * @param issueTagName
	 * @param issueIdx
	 * @return
	 */
	public int updateIssueTags(@Param("issueTagName") String issueTagName, @Param("issueIdx") String issueIdx);

	public IssueVO beforeDetailIssue(String issueIdx);
	/**
	 * 이슈 댓글 추가
	 * @param issueBoardVO
	 * @param issueIdx
	 * @return
	 */
	public int insertIssueBoard(@Param("issueCmtWriter")String issueCmtWriter, @Param("issueCmtContent")String issueCmtContent, @Param("issueIdx")String issueIdx);
	/**
	 * 이슈 댓글 삭제
	 * @param issueIdx
	 * @param issueCommentId
	 * @return
	 */
	public int deleteIssueCmt( @Param("issueIdx") String issueIdx,@Param("issueCommentId") String issueCommentId);
	/**
	 * 이슈 댓글 수정
	 * @param issueIdx
	 * @param issueCommentId
	 * @param issueCmtContent
	 * @return
	 */
	public int updateIssueComment(@Param("issueIdx")String issueIdx,@Param("issueCommentId") String issueCommentId,@Param("issueCmtContent") String issueCmtContent);
	/** 이슈 삭제
	 * @param issueIdx
	 * @return
	 */
	public int issueDelete(@Param("issueIdx") String issueIdx);
	
	public List<IssueVO> selectIssueList(String projectId);
	
	/**
	 * 이슈 태그 생성
	 * @param issueVO
	 * @return
	 */
	public void createissueTag(IssueVO issueVO);
	

}
