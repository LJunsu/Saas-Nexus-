package kr.or.nexus.group.project.service;

import java.util.List;
import java.util.Map;

import kr.or.nexus.vo.ProjectMemberVO;

public interface ProjectMemberService {
	
	public List<ProjectMemberVO> retrieveProjectMemberList(String projectId);

	public void createProjectMember(ProjectMemberVO vo);

	public List<ProjectMemberVO> retrieveProjectMemberSelectedList(ProjectMemberVO vo);

	public void removeProjectMember(ProjectMemberVO vo);

	public ProjectMemberVO confirmLeader(Map<String, Object> params);

	public ProjectMemberVO retrieveProjectMember(ProjectMemberVO input);

	public ProjectMemberVO findByPk(String projectMemIdx);

	public ProjectMemberVO retrieveProjectMemberVO(ProjectMemberVO input);

}
