package kr.or.nexus.vo;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = {"issueBoardAnswers", "issueTags", "member"})
@EqualsAndHashCode(of = "issueIdx")
public class IssueVO {
//	@NotBlank
	private String issueIdx;
	@NotBlank
	private String issueTitle;
	private String subIssueIdx;
	private LocalDate issueModifiedDate;
//	@NotBlank
	private String issueDelyn;
	@NotBlank
	private String projectMemIdx;
	private String issueProgressStatus;
	@NotBlank
	private String issuePriorityName;

	@NotBlank
	private String projectId;
	private String issueContent;
	@NotBlank
	private String issueOfficer;
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate issueDate;
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate issueDeadline;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate issueCreationDate;

	private List<IssueBoardAnswersVO> issueBoardAnswers;
	private List<IssueTagsVO> issueTags;


	// 이준수 추가
	private String searchName;
	private String searchInp;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate startDate;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate endDate;
	private Integer completedCount;
	private Integer allCount;
	private Integer priorityCount;
	private Integer statusCount;
	private Double completedPercentage;
	private ProjectVO project;
	private ProjectMemberVO projectMember;




	//석준 추가 코드
	@Nullable
	private String memberId;

	@Nullable
	private String senderId;

	@Nullable
	private String senderName;

	private String issueCount;

	@Nullable
	private MemberManagementVO member;

	@Nullable
	private String issueTagName;

}
