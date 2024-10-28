package kr.or.nexus.group.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nexus.group.project.service.ProjectRoleService;
import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.def.ProjectRoleDefaultVO;

@Controller
@RequestMapping("/group/project")
public class ProjectRoleInsertController {
	
	@Autowired
	private ProjectRoleService service;
	
	@PostMapping("roleInsert")
	@ResponseBody
	public List<ProjectRoleVO> roleInsert(@RequestBody ProjectRoleDefaultVO projectRoleVO) {
		int cnt = service.createProjectRole(projectRoleVO);
		if(cnt>0) {
			List<ProjectRoleVO> roleList = service.retrieveRoleList(projectRoleVO.getProjectId());
			return roleList;
		}else {
			throw new RuntimeException("에러");
		}
	}
	
	@PostMapping("roleDelete")
	@ResponseBody
	public List<ProjectRoleVO> roleDelete(@RequestBody ProjectRoleDefaultVO projectRoleVO) {
		int cnt = service.removeProjectRole(projectRoleVO);
		if(cnt>0) {
			List<ProjectRoleVO> roleList = service.retrieveRoleList(projectRoleVO.getProjectId());
			return roleList;
		}else {
			throw new RuntimeException("에러");
		}
	}
}
