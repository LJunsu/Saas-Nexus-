package kr.or.nexus.group.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nexus.group.project.service.ProjectMemberService;
import kr.or.nexus.group.project.service.ProjectRoleService;
import kr.or.nexus.group.project.service.ProjectService;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectRoleVO;

@Controller
@RequestMapping("/group/{groupId}/project/{projectId}/memberDelete")
public class ProjectMemberDeleteController {
	
	@Autowired
	private ProjectMemberService service;
	
	@Autowired
	private ProjectService prservice;
	
	@Autowired
	private ProjectRoleService roleService;
	
	@PostMapping
	@ResponseBody
	public Map<String, List> insert(@RequestBody ProjectMemberVO vo) {
		Map<String, List> map = new HashMap<>(); 
		service.removeProjectMember(vo);
		List<ProjectMemberVO> pmList = service.retrieveProjectMemberSelectedList(vo);
		List<ProjectMemberVO> groupList = prservice.retrieveProjectMemberInsertList(vo);
		List<ProjectRoleVO> prList = roleService.retrieveRoleList(vo.getProjectId());
		map.put("pmList", pmList);
		map.put("groupList", groupList);
		map.put("prList", prList);
		return map;
	}
}
