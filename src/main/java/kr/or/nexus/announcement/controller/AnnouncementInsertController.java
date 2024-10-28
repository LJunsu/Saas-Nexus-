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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nexus.announcement.service.AnnouncementService;
import kr.or.nexus.informationBoard.vo.InformationBoardVO;
import kr.or.nexus.vo.AnnouncementVO;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.def.AtchFileVO;
import kr.or.nexus.vo.def.MemberManagementDefaultVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/{groupId}/{projectId}/announcement/insert")
@Slf4j
public class AnnouncementInsertController {

	public static final String MODELNAME = "newAnnouncement";

	@Autowired
	private AnnouncementService service;

	@ModelAttribute(name= MODELNAME)
	public AnnouncementVO notification() {
		return new AnnouncementVO();

	}
	@GetMapping
	public String form() {
		return "tiles2:projectLeader/announcementInsert";
	}

	// @RequestMapping(value = "/index.do") 등록을 해야 @Controller가 그냥 빈에서 컨트롤러가 됨
	@PostMapping
	public String insert(
			@ModelAttribute(MODELNAME) AnnouncementVO newAnnouncement,
			BindingResult errors,
			RedirectAttributes redirectAttributes,
			Authentication authentication,
			Model model,
			@PathVariable String groupId,
			@PathVariable String projectId


			) {

		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();
		newAnnouncement.setMemberId(realUser.getMemberId());

		model.addAttribute("projectId", projectId);
		model.addAttribute("groupId", groupId);


		String lvn = null;
		if(!errors.hasErrors()) {
			service.createAnnouncement(newAnnouncement);
			lvn = "redirect:/{groupId}/{projectId}/announcement/";
		}else {
			redirectAttributes.addFlashAttribute(MODELNAME, newAnnouncement);
			redirectAttributes.addFlashAttribute(BindingResult.MODEL_KEY_PREFIX+MODELNAME, errors);
			lvn= "redirect:/{groupId}/{projectId}/announcement/insert";
		}
		newAnnouncement.setAtchFile(null);
		return lvn;
	}

}
