package kr.or.nexus.group.project.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.group.project.dao.ProjectMemberMapper;
import kr.or.nexus.vo.ProjectMemberVO;

@Service
public class ProjectMemberServiceImpl implements ProjectMemberService {
	
	@Autowired
	private ProjectMemberMapper dao;

	@Override
	public List<ProjectMemberVO> retrieveProjectMemberList(String projectId) {
		return dao.selectProjectMemberList(projectId);
	}

	@Override
	public void createProjectMember(ProjectMemberVO vo) {
		dao.insertProjectMember(vo);
	}

	@Override
	public List<ProjectMemberVO> retrieveProjectMemberSelectedList(ProjectMemberVO vo) {
		return dao.selectProjectMemberSelectedList(vo);
	}

	@Override
	public void removeProjectMember(ProjectMemberVO vo) {
		dao.deleteProjectMember(vo);
	}

	@Override
	public ProjectMemberVO confirmLeader(Map<String, Object> params) {
		return dao.confirmLeader(params);
	}

	@Override
	public ProjectMemberVO retrieveProjectMember(ProjectMemberVO input) {
		return dao.selectProjectMember(input);
	}

	@Override
	public ProjectMemberVO findByPk(String projectMemIdx) {
		return dao.findByPk(projectMemIdx);
	}

	@Override
	public ProjectMemberVO retrieveProjectMemberVO(ProjectMemberVO input) {
		return dao.selectProjectMemberVO(input);
	}
	
}