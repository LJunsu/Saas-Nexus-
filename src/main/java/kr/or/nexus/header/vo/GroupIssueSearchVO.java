package kr.or.nexus.header.vo;

import java.time.LocalDate;

import lombok.Data;

@Data
public class GroupIssueSearchVO {
	private String projectName;
	private String projectDelyn;
	private String issueTitle;
	private String projectId;
	private String issueContent;
	private String issueOfficer;
	private LocalDate issueCreationDate;
	private String issueIdx;
	private String subIssueIdx;
	private String issueDelyn;
	private String projectMemIdx;
	private String issueProgressStatus;
	private String memberId;
	private String memberName;
}
