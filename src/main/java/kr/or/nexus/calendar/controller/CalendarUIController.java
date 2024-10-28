package kr.or.nexus.calendar.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.calendar.service.CalendarService;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.GitVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("{groupId}/{projectId}/calendarUI")
public class CalendarUIController {
	
	private final CalendarService service;
	
	@GetMapping
	public String list(	Model model, @PathVariable String groupId, @PathVariable String projectId, Authentication authentication) {
		List<IssueVO> issueList = service.retrieveIssueList(projectId);
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();
		String memberId = realUser.getMemberId();
		List<ProjectMemberVO> projectMemberList = service.retrieveProjectMemberList(projectId);
		GitVO gitDetail = service.retrieveGit(projectId);

		model.addAttribute("issueList", issueList);
		model.addAttribute("projectMemberList", projectMemberList);
		model.addAttribute("projectId", projectId);
		model.addAttribute("gitDetail", gitDetail);
		return "tiles2:groupMember/calendar";
	}
}
