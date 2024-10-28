package kr.or.nexus.announcement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nexus.announcement.service.AnnouncementService;
import kr.or.nexus.vo.AnnouncementVO;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/{groupId}/{projectId}/announcement")
@RequiredArgsConstructor
public class AnnouncementModifyController {

	public static final String MODELNAME = "targetAnnouncement";

	@Autowired
	private AnnouncementService service;

	@GetMapping("/{boardId}/update")
	public String form(Model model, @PathVariable String boardId) {
		AnnouncementVO announcement = service.readAnnouncement(boardId); // 데이터 조회
		model.addAttribute(MODELNAME, announcement);
		return "tiles2:projectLeader/announcementUpdate";
	}

	@PostMapping("/{boardId}/update")
	public String update(@ModelAttribute(MODELNAME) AnnouncementVO announcement, RedirectAttributes redirectAttributes,
			BindingResult errors,
			Authentication authentication) {

		if (!errors.hasErrors()) {
			service.modifyBoard(announcement);
			return "redirect:/{groupId}/{projectId}/announcement"; // 성공 시 목록 화면으로 이동
		} else {
			redirectAttributes.addFlashAttribute(MODELNAME, announcement);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX, errors);
			return "redirect:/{groupId}/{projectId}/announcement/{boardId}/update"; // 실패 시 수정 화면으로 이동
		}
	}

	@GetMapping("{boardId}/delete")
	public String delete(@PathVariable String boardId, RedirectAttributes redirectAttributes) {
		service.removeAnnouncement(boardId);
		return "redirect:/{groupId}/{projectId}/announcement";

	}
}
