package kr.or.nexus.group.project.service;

import java.util.List;
import java.util.Map;

import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectVO;

public interface ProjectService {

	public List<GroupMemberVO> retrieveGroupMemberList();

	public List<ProjectVO> retrieveGroupProjectList(String groupId);

	public List<ProjectVO> retrieveProjectList(String groupId);

	public void createProjectForm(ProjectVO vo);

	public void createProjectFormRole(ProjectVO vo);

	public ProjectVO retrieveProjectId();

	public ProjectVO retrieveProject(String projectId);

	public List<ProjectMemberVO> retrieveProjectMemberInsertList(ProjectMemberVO pmvo);

	public void modifyProject(ProjectVO project);

	public void modifyInsertProject(ProjectVO project);

	public void deleteProject(String projectId);

	public List<ProjectMemberVO> retrieveSearchMember(Map<String, Object> payload);

	public ProjectVO retrieveAfterInsertProject(String projectId);

	public List<MemberManagementVO> participateMember(String projectId);

	public List<ProjectVO> retrieveGroupProjectSearchList(Map<String, Object> map);

}
