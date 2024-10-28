package kr.or.nexus.kanban.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.timeline.service.TimeLineService;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;

@Controller
@RequestMapping("/{groupId}/{projectId}/kanbanBoard")
public class KanbanUIController {
	
	@Autowired
	private TimeLineService timeLineService;
	
	@GetMapping
	public String kanBanBoardUi(@PathVariable String projectId, @PathVariable String groupId, Model model, 
		Authentication authentication) {
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();
		String memberId = realUser.getMemberId();
		ProjectMemberVO projectMemberVO = new ProjectMemberVO();
		projectMemberVO.setProjectId(projectId);
		projectMemberVO.setMemberId(memberId);
		
		ProjectMemberVO projectMemberInfo = timeLineService.projectMemId(projectMemberVO);
		
		List<ProjectMemberVO> projectMemberList = timeLineService.timeLineProjectMemberList(projectId);
		List<IssueVO> issueList = timeLineService.timeLineIssueList(projectId);
		model.addAttribute("projectMemberList",projectMemberList);
		model.addAttribute("issueList", issueList);
		model.addAttribute("groupId",groupId);
		model.addAttribute("projectId",projectId);
		model.addAttribute("projectMemberInfo", projectMemberInfo);
		return "tiles2:kanbanBoard/kanbanBoard";
	}
}
