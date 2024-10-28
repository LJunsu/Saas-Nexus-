package kr.or.nexus.group.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nexus.group.project.service.ProjectMemberService;
import kr.or.nexus.group.project.service.ProjectRoleService;
import kr.or.nexus.group.project.service.ProjectService;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectRoleVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/group/{groubId}/project/{projectId}")
@Slf4j
public class ProjectMemberInsertController {

	@Autowired
	private ProjectMemberService service;

	@Autowired
	private ProjectService prservice;

	@Autowired
	private ProjectRoleService roleService;

	@PostMapping("memberInsert")
	@ResponseBody
	public Map<String, List> insert(@RequestBody Map<String, Object> payload) {
		Map<String, List> map = new HashMap<>();
		ProjectMemberVO vo = new ProjectMemberVO();
		vo.setGroupId((String) payload.get("groupId"));
		vo.setProjectId((String) payload.get("projectId"));
		vo.setMemberId((String) payload.get("memberId"));
		String selectedRole = (String) payload.get("selectedRole");
		Map<String, Object> params = new HashMap<String, Object>();
		String roleName = "프로젝트 리더";
		params.put("projectRoleName", roleName);
		params.put("projectId", payload.get("projectId"));
		vo.setProjectRoleIdx(selectedRole);
		ProjectRoleVO projectRole = roleService.retrieveRole(selectedRole);
		if (projectRole.getProjectRoleName().equals("프로젝트 리더")) {
			ProjectMemberVO pm = service.confirmLeader(params);
			if (pm != null) {
				throw new RuntimeException();
			}
		}
		service.createProjectMember(vo);
		List<ProjectMemberVO> pmList = service.retrieveProjectMemberSelectedList(vo);
		List<ProjectMemberVO> groupList = prservice.retrieveProjectMemberInsertList(vo);
		List<ProjectRoleVO> prList = roleService.retrieveRoleList(vo.getProjectId());
		map.put("pmList", pmList);
		map.put("groupList", groupList);
		map.put("prList", prList);
		return map;
	}

	@PostMapping("groupMemberSearch")
	@ResponseBody
	public Map<String, List> search(@RequestBody Map<String, Object> payload) {
		Map<String, List> map = new HashMap<>();
		String groupId = (String) payload.get("groupId");
		List<ProjectMemberVO> searchList = prservice.retrieveSearchMember(payload);
		List<ProjectRoleVO> prList = roleService.retrieveRoleList((String) payload.get("projectId"));
		map.put("searchList", searchList);
		map.put("prList", prList);
		return map;
	}
}
