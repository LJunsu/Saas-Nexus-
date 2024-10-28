package kr.or.nexus.video.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;

@Controller
@RequestMapping("/{groupId}/{projectId}/video")
public class VideoController {
	
	@GetMapping
	public String startVideo(@PathVariable String projectId,@PathVariable String groupId ,Model model, Authentication authentication) {
		model.addAttribute("projectId",projectId);
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
	    MemberManagementVO realUser = wrapper.getRealUser();
		String memberId = realUser.getMemberId();
		String memberName = realUser.getMemberName(); 
		model.addAttribute("memberId",memberId);  
		model.addAttribute("groupId",groupId);  
		model.addAttribute("memberName",memberName);  
		return "tiles2:/video/videoStart";
	}
	

}
