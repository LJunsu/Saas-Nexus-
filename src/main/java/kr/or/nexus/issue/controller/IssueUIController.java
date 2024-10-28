package kr.or.nexus.issue.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.MemberManagementVO;

@Controller
public class IssueUIController {

	@ModelAttribute("issue")
	public IssueVO issue() {
		return new IssueVO();
	}

	@GetMapping("/issue/{groupId}/{projectId}/issueMain")
	public String issue(Model model, @PathVariable String projectId,@PathVariable String groupId,Authentication authentication) {
		model.addAttribute("projectId",projectId);
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
	    MemberManagementVO realUser = wrapper.getRealUser();
	    String memberId = realUser.getMemberId();
	    model.addAttribute("memberId",memberId);
	    model.addAttribute("groupId",groupId);
		return "tiles2:/issue/issueMain";
	}

}
