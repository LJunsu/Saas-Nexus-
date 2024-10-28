package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(exclude = {"projectRole", "projectMember", "issueList"})
@EqualsAndHashCode(of = "projectId")
public class ProjectVO implements Serializable {
	private String projectId;
	private String projectName;
	private String projectDescription;
	private String projectProfileImg;
	private LocalDate projectCreationDate;
	private String projectDelyn;
	private String projectSuccessStatus;
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate projectEndDate;
	private Integer projectBudget;
	private Integer projectRevenue;
	private String groupId;
	private String projectState;
	private String budget;
	
	private List<ProjectRoleVO> projectRole;
	private List<ProjectMemberVO> projectMember;
	private List<IssueVO> issueList;
}
