package kr.or.nexus.ea.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nexus.ea.service.EAMainService;
import kr.or.nexus.vo.MemberManagementVO;

@Controller
@RequestMapping("/{groupId}/ea")
public class EAMainUIController {
	
	@Autowired
	private EAMainService service;

	@GetMapping
	public String index(Model model, HttpSession session) {
		MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");		
		String memberId = user.getMemberId();
		String groupId = user.getGroupMemberVO().getGroupId();
		String approvalProgressStatus = "waitingApprovalFromSender";
		Map<String, Object> params = new HashMap<>();
		params.put("memberId", memberId);
		params.put("approvalProgressStatus", approvalProgressStatus);
		List<Map<String, Object>> eaList = service.retrieveEAListByProgress(params);
		List<Map<String, Object>> eaCountList = service.retrieveEACountListByProgress(params);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!" + eaList);
		model.addAttribute("eaList", eaList);
		model.addAttribute("eaCountList", eaCountList);
		model.addAttribute("groupId", groupId);
		model.addAttribute("memberId", memberId);
		return "tiles:ea/eaList";
	}
}
