package kr.or.nexus.group.project.service;

import java.util.List;

import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.def.ProjectRoleDefaultVO;

public interface ProjectRoleService {

	public int createProjectRole(ProjectRoleDefaultVO vo);

	public List<ProjectRoleVO> retrieveRoleList(String projectId);

	public int removeProjectRole(ProjectRoleDefaultVO projectRoleVO);

	public ProjectRoleVO retrieveRole(String role);

	public ProjectRoleVO findById(String projectRoleIdx);

	public ProjectRoleDefaultVO findIdx(ProjectRoleDefaultVO prvo);
	
	
}
