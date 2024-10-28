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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.or.nexus.ea.service.EAMainService;
import kr.or.nexus.vo.MemberManagementVO;
@Controller
@RequestMapping("/{groupId}/ea/buildUI")
public class EABuildUIController {	
	
	@Autowired
	private EAMainService service;

	@GetMapping
	public String index(Model model, HttpSession session) throws JsonProcessingException {
		MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");		
		String memberId = user.getMemberId();
		String memberName = user.getMemberName();
		String memberEmail = user.getMemberEmail();
		String groupId = user.getGroupMemberVO().getGroupId();

        Map<String, String> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("memberId", memberId);
		List<MemberManagementVO> memberList = service.retrieveMemberList(params);
		List<MemberManagementVO> allMemberList = service.retrieveAllMemberList(params);
		model.addAttribute("memberList", memberList);
		model.addAttribute("memberName", memberName);
		model.addAttribute("memberEmail", memberEmail);
		model.addAttribute("groupId", groupId);
		model.addAttribute("allMemberList", allMemberList);
		ObjectMapper objectMapper = new ObjectMapper();
		String memberListJson = objectMapper.writeValueAsString(memberList);
		model.addAttribute("memberListJson", memberListJson);
		return "tiles:ea/eaBuild";
	}
}
