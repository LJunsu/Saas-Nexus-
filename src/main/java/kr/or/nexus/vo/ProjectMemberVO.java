package kr.or.nexus.vo;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of = {"projectId", "memberId"})
public class ProjectMemberVO {
	private String projectMemIdx;
	private LocalDate projectJoinDate;
	private LocalDate projectDelDate;
	private String projectDelyn;
	private String groupId;
	private String memberId;
	private String projectRoleIdx;
	private String projectId;
	
	
	private List<ProjectVO> project;
	private List<String> projectMemIdxes;
	private List<ProjectRoleVO> projectRole;
	private ProjectRoleVO projectRoleVO;
	private GroupMemberVO groupMember;
	private MemberManagementVO member;
}
