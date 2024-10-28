package kr.or.nexus.group.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.commons.def.mapper.ProjectRoleDefaultMapper;
import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.def.ProjectRoleDefaultVO;

@Service
public class ProjectRoleServiceImpl implements ProjectRoleService {

	@Autowired
	private ProjectRoleDefaultMapper dao;
	
	@Override
	public int createProjectRole(ProjectRoleDefaultVO vo) {
		ProjectRoleDefaultVO prvo = dao.retrieve(vo);
		if(prvo != null) {
			return 1;
		}else {
			return dao.insert(vo);			
		}
	}

	@Override
	public List<ProjectRoleVO> retrieveRoleList(String projectId) {
		return dao.selectRoleList(projectId);
	}

	@Override
	public int removeProjectRole(ProjectRoleDefaultVO projectRoleVO) {
		return dao.deleteProjectRole(projectRoleVO);
	}

	@Override
	public ProjectRoleVO retrieveRole(String role) {
		return dao.selectRole(role);
	}

	@Override
	public ProjectRoleVO findById(String projectRoleIdx) {
		return dao.findByPk(projectRoleIdx);
	}

	@Override
	public ProjectRoleDefaultVO findIdx(ProjectRoleDefaultVO prvo) {
		return dao.retrieve(prvo);
	}
	
	
	
}
