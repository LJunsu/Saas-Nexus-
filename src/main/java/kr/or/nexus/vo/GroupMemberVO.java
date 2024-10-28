package kr.or.nexus.vo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GroupMemberVO implements Serializable {
	private int rnum;
	
	private String groupId;
	private String memberId;
	private LocalDate groupJoinDate;
	private String groupDelyn;
	private String groupAdminYn;
	
	private Integer count;
	
	private MemberManagementVO member;
	private List<ProjectMemberVO> projectMember;
	private ProjectRoleVO projectRole;
	private GroupManagementVO group;
}
