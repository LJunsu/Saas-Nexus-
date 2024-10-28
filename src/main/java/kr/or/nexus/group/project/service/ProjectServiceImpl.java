package kr.or.nexus.group.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.group.project.dao.ProjectMapper;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectVO;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectMapper dao;

	@Override
	public List<GroupMemberVO> retrieveGroupMemberList() {

		return dao.selectGroupMemberList();
	}

	@Override
	public List<ProjectVO> retrieveGroupProjectList(String groupId) {
		return dao.selectGroupProjectList(groupId);
	}

	@Override
	public void createProjectForm(ProjectVO vo) {
		dao.insertProjectForm(vo);

	}

	@Override
	public void createProjectFormRole(ProjectVO vo) {
		dao.insertProjectFormRole(vo);
	}

	@Override
	public List<ProjectVO> retrieveProjectList(String groupId) {
		return dao.selectProjectList(groupId);
	}

	@Override
	public ProjectVO retrieveProjectId() {
		return dao.selectProjectId();
	}

	@Override
	public ProjectVO retrieveProject(String projectId) {
		return dao.selectProject(projectId);
	}

	@Override
	public List<ProjectMemberVO> retrieveProjectMemberInsertList(ProjectMemberVO pmvo) {
		return dao.selectProjectMemberInsertList(pmvo);
	}

	@Override
	public void modifyProject(ProjectVO project) {
		dao.updateProject(project);
	}

	@Override
	public void modifyInsertProject(ProjectVO project) {
		dao.updateInsertProject(project);
	}

	@Override
	public void deleteProject(String projectId) {
		dao.deleteProject(projectId);
	}

	@Override
	public List<ProjectMemberVO> retrieveSearchMember(Map<String, Object> payload) {
		return dao.selectSearchMember(payload);
	}

	@Override
	public List<ProjectVO> retrieveGroupProjectSearchList(Map<String, Object> map) {
		return dao.selectGroupProjectSearchList(map);
	}

	/**
	 * 석준 추가
	 */
	@Override
	public ProjectVO retrieveAfterInsertProject(String projectId) {

		return dao.AfterInsertProject(projectId);
	}

	@Override
	public List<MemberManagementVO> participateMember(String projectId) {
		return dao.participateMember(projectId);
	}


}
