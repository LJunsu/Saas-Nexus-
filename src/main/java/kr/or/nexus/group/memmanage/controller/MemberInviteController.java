package kr.or.nexus.group.memmanage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nexus.group.main.service.GroupMainService;
import kr.or.nexus.group.memmanage.service.MemberManagementService;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.def.GroupManagementDefaultVO;

@Controller
@RequestMapping("/group/{groupId}")
public class MemberInviteController {
	
	@Autowired
	private MemberManagementService service;
	
	@Autowired
	private GroupMainService groupService;
	
	@GetMapping("memberInvite")
	public String inviteUI(@PathVariable String groupId, Model model, Authentication authentication) {
		GroupManagementDefaultVO group = groupService.retrieveGroup(groupId);
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();
		MemberManagementVO member = service.findByUsername(realUser.getMemberId());
		model.addAttribute("group", group);
		model.addAttribute("member", member);
		return "tiles:groupManager/memberManage/memberInvite";
	}
	
	@PostMapping("memberInvite/emailSearch")
	public List<MemberManagementVO> emailSearch(@RequestBody Map<String, Object> payload){
		String memberEmail = (String) payload.get("memberEmail");
		List<MemberManagementVO> memberList = service.retrieveMemberFromEmail(memberEmail);
		
		return memberList;
	}
	
	@GetMapping("insertGroupMember")
	public String insertUI(@PathVariable String groupId, Model model, Authentication authentication) {
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();
		MemberManagementVO member = service.findByUsername(realUser.getMemberId());
		GroupManagementVO group = groupService.retrieveGroupById(groupId);
		
		model.addAttribute("group", group);
		model.addAttribute("member", member);
		return "tiles4:groupManager/memberManage/groupAccessForm";
	}
	
	@PostMapping("insertGroupMember")
	public String insert(@PathVariable String groupId, Authentication authentication, Model model, RedirectAttributes redirectAttributes) {
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();
		String memberId = realUser.getMemberId();
		GroupMemberVO gm = new GroupMemberVO();
		gm.setGroupId(groupId);
		gm.setMemberId(memberId);
		GroupMemberVO check = service.retrieveGroupMember(gm);
		if(check != null) {
			String error = "이미 그룹에 등록된 회원입니다.";
			redirectAttributes.addFlashAttribute("error", error);
			return "redirect:/group/{groupId}/insertGroupMember";
		}else {
			service.createGroupMember(gm);			
			return "redirect:/{groupId}/memMain.do";
		}
		
	}
}
