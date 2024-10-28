package kr.or.nexus.cloud.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.commons.util.Utils;
import kr.or.nexus.group.project.service.ProjectMemberService;
import kr.or.nexus.group.project.service.ProjectRoleService;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectRoleVO;

@Controller
@RequestMapping("/group/{groupId}/project/{projectId}/cloud")
public class CloudController {
	
	@Autowired
	private ProjectMemberService service;
	
	@Autowired
	private ProjectRoleService roleService;
	
	@GetMapping
	public String cloudUI(@PathVariable String groupId, @PathVariable String projectId, Model model, HttpSession session) {
		MemberManagementVO member = (MemberManagementVO) session.getAttribute("user");
		String memberId = member.getMemberId();
		ProjectMemberVO input = new ProjectMemberVO();
		input.setMemberId(memberId);
		input.setProjectId(projectId);
		input.setGroupId(groupId);
		
		ProjectMemberVO vo = service.retrieveProjectMemberVO(input);
		List<ProjectRoleVO> roles = roleService.retrieveRoleList(projectId);
		model.addAttribute("user", vo);
		model.addAttribute("roles", roles);
		model.addAttribute("projectId", projectId);
		
		return "tiles2:cloud/cloudUI";
	}
	
}
