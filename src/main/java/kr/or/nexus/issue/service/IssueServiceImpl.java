package kr.or.nexus.issue.service;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.issue.dao.IssueMapper;
import kr.or.nexus.vo.IssueBoardAnswersVO;
import kr.or.nexus.vo.IssueVO;

@Service
public class IssueServiceImpl implements IssueService {

	@Autowired
	IssueMapper mapper;



	@Override
	public IssueVO issueSelect(String issueIdx) {
		return mapper.selectIssue(issueIdx);
	}


	@Override
	public int correctionIssueContent(String issueContent, String issueIdx) {
		return mapper.updateIssueContent(issueContent, issueIdx);
	}
	@Override
	public int correctionIssueTitle(String issueTitle, String issueIdx) {
		return mapper.updateIssueTitle(issueTitle, issueIdx);
	}

	@Override
	public List<IssueBoardAnswersVO> issueCommentList(String issueIdx) {
		return mapper.issueCommentList(issueIdx);
	}

	@Override
	public int correctionUpIssue(String issueIdx, String subIssueIdx) {
		return mapper.updateUpIssue(issueIdx, subIssueIdx);
	}

	@Override
	public int correctionOfficer(String issueIdx, String issueOfficer) {
		return mapper.updateOfficer(issueIdx, issueOfficer);
	}

	@Override
	public int correctionPriority(String issueIdx, String issuePriorityName) {
		return mapper.updatePriority(issueIdx, issuePriorityName);
	}

	@Override
	public String createIssue(IssueVO issueVO) {
	    // issueVO와 issueTagModal 값을 하나의 Map에 담아 매퍼로 전달
	   

	    // insertIssue 쿼리 실행
	    int result = mapper.insertIssue(issueVO);

	    // issueIdx 반환
	    return issueVO.getIssueIdx();
	}

	@Override
	public int correctionProgressStatus(String issueIdx, String issueProgressStatus) {
		return mapper.updateProgressStatus(issueIdx, issueProgressStatus);
	}

	@Override
	public int correctionIssueStartDate(LocalDate issueDate, String issueIdx) {
		return mapper.updateIssueStartDate(issueDate, issueIdx);
	}

	@Override
	public int correctionIssueDeadLineDate(LocalDate issueDeadline, String issueIdx) {
		return mapper.updateIssueDeadLineDate(issueDeadline, issueIdx);
	}

	@Override
	public int correctionIssueTags(String issueTagName, String issueIdx) {
		return mapper.updateIssueTags(issueTagName, issueIdx);
	}

	@Override
	public IssueVO beforeIssueDetail(String issueIdx) {
		return mapper.beforeDetailIssue(issueIdx);
	}


	@Override
	public int createIssueBoard(String issueCmtWriter, String issueCmtContent, String issueIdx) {
		return mapper.insertIssueBoard(issueCmtWriter,issueCmtContent ,issueIdx);
	}

	@Override
	public int issueCmtDelete(String issueIdx, String issueCommentId) {
		return mapper.deleteIssueCmt(issueIdx,issueCommentId);
	}

	@Override
	public int issueCmtDelete(String issueIdx, String issueCommentId, String issueCmtContent) {
		return mapper.updateIssueComment(issueIdx,issueCommentId,issueCmtContent);
	}

	@Override
	public List<IssueVO> projectIssueList(String projectId, String issueProgressStatus, String issueTitle) {
		return mapper.projectIssueList(projectId, issueProgressStatus, issueTitle);
	}


	@Override
	public int issueDelete(String issueIdx) {
		return mapper.issueDelete(issueIdx);
	}


	@Override
	public List<IssueVO> retrieveIssueList(String projectId) {
		return mapper.selectIssueList(projectId);
	}


	@Override
	public void issueCreateTag(IssueVO issueVO) {
		mapper.createissueTag(issueVO);
	}







}
