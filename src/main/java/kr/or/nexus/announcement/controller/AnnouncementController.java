package kr.or.nexus.announcement.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nexus.announcement.dto.ProjectLeader;
import kr.or.nexus.announcement.service.AnnouncementService;
import kr.or.nexus.commons.paging.BootStrap5PaginationRenderer;
import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.commons.paging.PaginationRenderer;
import kr.or.nexus.commons.paging.SimpleCondition;
import kr.or.nexus.vo.AnnouncementVO;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;

@Controller
@RequestMapping("/{groupId}/{projectId}/announcement")
public class AnnouncementController {

	@Autowired
	private AnnouncementService service;

	// @RequestMapping(value = "/index.do") 등록을 해야 @Controller가 그냥 빈에서 컨트롤러가 됨

	@GetMapping
	public String list(@RequestParam(required = false, defaultValue = "1") int page, Model model,
			@ModelAttribute("condition") SimpleCondition simplecondition,
			@PathVariable String groupId, @PathVariable  String projectId,
			Authentication authentication) {

		PaginationInfo paging = new PaginationInfo();
		paging.setPage(page);
		paging.setGroupId(groupId);
		paging.setProjectId(projectId);

		List<ProjectLeader> leaders = service.getProjectLeaders(projectId);

		paging.setSimpleCondition(simplecondition);
		model.addAttribute("leaders", leaders);
		model.addAttribute("projectId", projectId);
		model.addAttribute("groupId", groupId);

		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();
		model.addAttribute("realUser", realUser);

		List<AnnouncementVO> announcementList = service.readAnnouncementList(paging);
		model.addAttribute("announcementList", announcementList);
		PaginationRenderer renderer = new BootStrap5PaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging);
		model.addAttribute("pagingHTML", pagingHTML);


		return "tiles2:projectLeader/announcement";

	}

	@GetMapping("{boardId}")
	public String detail(@PathVariable String boardId, Model model
			, Authentication authentication
			,HttpServletRequest request,
			@PathVariable String groupId,
			@PathVariable String projectId) {
		String contextPath = request.getContextPath();
		AnnouncementVO announcement = service.readAnnouncement(boardId);
		model.addAttribute("announcement", announcement);
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();

		model.addAttribute("realUser", realUser);
		model.addAttribute("projectId", projectId);
		model.addAttribute("groupId", groupId);
		return "tiles2:projectLeader/announcementDetail";
	}


}
