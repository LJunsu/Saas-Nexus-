package kr.or.nexus.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode(of = "projectRoleIdx")
public class ProjectRoleVO implements Serializable {
	private String projectRoleIdx;
	private String projectId;
	private String projectRoleName;
	
	private ProjectVO project;
	private List<ProjectMemberVO> projectMember;
}
