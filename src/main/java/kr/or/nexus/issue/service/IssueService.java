package kr.or.nexus.issue.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import kr.or.nexus.vo.IssueBoardAnswersVO;
import kr.or.nexus.vo.IssueVO;


public interface IssueService {
	/**
	 * 이슈 조회
	 * @return 이슈VO
	 */
	public IssueVO issueSelect(@Param("issueIdx") String issueIdx);

	/**
	 * @param issue
	 * @param issueTagModal 
	 * @return	이슈 생성
	 */
	public String createIssue(IssueVO issueVO);
	
	public void issueCreateTag(IssueVO issueVO);

	/**
	 * 이슈 댓글 조회
	 * @param issueIdx
	 * @return
	 */
	public List<IssueBoardAnswersVO> issueCommentList(@Param("issueIdx") String issueIdx);

	public int correctionIssueContent(@Param("issueContent") String issueContent, @Param("issueIdx") String issueIdx);

	/**
	 * 이슈 제목 수정
	 * @param issueTitle
	 * @param issueIdx
	 * @return
	 */
	public int correctionIssueTitle(@Param("issueTitle") String issueTitle, @Param("issueIdx") String issueIdx);

	/**
	 * 상위 이슈 변경
	 * @param issueIdx
	 * @param subIssueIdx
	 * @return
	 */
	public int correctionUpIssue(@Param("issueIdx") String issueIdx, @Param("subIssueIdx") String subIssueIdx);

	/**
	 * 이슈 담당자 변경
	 * @param issueIdx
	 * @param issueOfficer
	 * @return
	 */
	public int  correctionOfficer(@Param("issueIdx") String issueIdx, @Param("issueOfficer") String issueOfficer);

	/**
	 * 이슈 우선 순위 변경
	 * @param issueIdx
	 * @param issuePriorityName
	 * @return
	 */
	public int correctionPriority(@Param("issueIdx") String issueIdx, @Param("issuePriorityName") String issuePriorityName);

	/**
	 * 이슈 진행상태 수정
	 * @param issueIdx
	 * @param issueProgressStatus
	 * @return
	 */
	public int correctionProgressStatus(@Param("issueIdx") String issueIdx, @Param("issueProgressStatus") String issueProgressStatus);

	/**
     * 이슈 시작기한 업데이트
     * @param localDate
     * @param issueIdx
     */
	public int correctionIssueStartDate(@Param("issueDate") LocalDate localDate, @Param("issueIdx") String issueIdx);

    /**
     * 이슈 마감기한 업데이트
     * @param localDate
     * @param issueIdx
     */
	public int correctionIssueDeadLineDate(@Param("issueDeadline") LocalDate localDate, @Param("issueIdx") String issueIdx);

	/**
	 * 이슈 태그 수정
	 * @param issueTagName
	 * @param issueIdx
	 * @return
	 */
	public int correctionIssueTags(@Param("issueTagName") String issueTagName, @Param("issueIdx") String issueIdx);



	/**
	 * 전의 상세 사항 가져오기
	 * @param issueIdx
	 * @return
	 */
	public IssueVO beforeIssueDetail(String issueIdx);

	/**
	 * 이슈 댓글 추가
	 * @param issueBoardVO
	 * @param issueIdx
	 * @return
	 */
	public int createIssueBoard(@Param("issueCmtWriter") String issueCmtWriter,@Param("issueCmtContent") String issueCmtContent, @Param("issueIdx") String issueIdx);

	/**
	 * 이슈 댓글 삭제
	 * @param issueIdx
	 * @param issueCommentId
	 * @return
	 */
	public int issueCmtDelete(String issueIdx, String issueCommentId);

	/**
	 * 이슈 댓글 수정
	 * @param issueIdx
	 * @param issueCommentId
	 * @param issueCmtContent
	 * @return
	 */
	public int issueCmtDelete(String issueIdx, String issueCommentId, String issueCmtContent);

	public List<IssueVO> projectIssueList(@Param("projectId")String projectId,@Param("issueProgressStatus") String issueProgressStatus,@Param("issueTitle") String issueTitle);

	public int issueDelete(@Param("issueIdx")String issueIdx);

	public List<IssueVO> retrieveIssueList(String projectId);

}
