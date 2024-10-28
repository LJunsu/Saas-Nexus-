package kr.or.nexus.group.project.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.commons.def.mapper.ProjectDefaultMapper;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectVO;

@Mapper
public interface ProjectMapper extends ProjectDefaultMapper {

	public List<GroupMemberVO> selectGroupMemberList();

	public List<ProjectVO> selectGroupProjectList(String groupId);

	public void insertProjectForm(ProjectVO vo);

	public void insertProjectFormRole(ProjectVO vo);

	public List<ProjectVO> selectProjectList(String groupId);

	public ProjectVO selectProjectId();

	public ProjectVO selectProject(String projectId);

	public List<ProjectMemberVO> selectProjectMemberInsertList(ProjectMemberVO pmvo);

	public void updateProject(ProjectVO project);

	public void updateInsertProject(ProjectVO project);

	public void deleteProject(String projectId);

	public List<ProjectMemberVO> selectSearchMember(Map<String, Object> payload);

	public List<ProjectVO> selectGroupProjectSearchList(Map<String, Object> map);

	/**
	 * 석준 추가 코드
	 */
	public ProjectVO AfterInsertProject(String projectId);

	public List<MemberManagementVO> participateMember(String projectId);


}
