package kr.or.nexus.commons.alarmAndLog.EventType;

import lombok.Data;

/**
 * @author PC-16
 *
 * 이 어플리케이션(nexus) 에서 발생한 이벤트 종류를 정의 (종류를 정의) 하는 클래스
 */

public enum IssueEventType {


	ISSUE_CREATED("이슈 생성 알림","/nexus/"),
	ISSUE_UPDATED("이슈 수정 알림","/nexus/"),
	ISSUE_DELETE("이슈 삭제 알림","/nexus/"),

	ISSUE_ASSIGNED("이슈 할당 알림","/nexus/"),
	ISSUE_UNASSIGNED("이슈 재할당 알림","/nexus/"),
	ISSUE_STATUS_CHANGED("이슈 상태 변경 알림","/nexus/"),
	ISSUE_CLOSED("해당 이슈 종료 알림","/nexus/"),
	ISSUE_ANSWER_ADDED("이슈에 대해 댓글 추가 알림","/nexus/"),
	ISSUE_ANSWER_UPDATED("이슈에 대한 댓글 수정 알림","/nexus/"),

	ISSUE_PRIORITY_CHANGED("이슈 우선순위 변경 알림","/nexus/");


	private	final String issueEventType;
	private final String issueEventUrl;

	private IssueEventType(String issueEventType, String issueEventUrl) {

		this.issueEventType = issueEventType;
		this.issueEventUrl = issueEventUrl;
	}


	public String getIssueEventType() {
		return issueEventType;
	}

	public String getIssueEventUrl() {
		return issueEventUrl;
	}








}

