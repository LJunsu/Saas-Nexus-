package kr.or.nexus.group.main.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nexus.group.main.service.GroupMainService;
import kr.or.nexus.group.project.service.ProjectService;
import kr.or.nexus.member.main.service.MemberMainService;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;
import kr.or.nexus.vo.ProjectVO;
import kr.or.nexus.vo.def.GroupManagementDefaultVO;

@Controller
@RequestMapping("/group/{groupId}")	//{groupId} 나중에 바꾸기.
public class GroupMainController {

	@Autowired
	private ProjectService service;

	@Autowired
	private GroupMainService groupService;

	//도연추가
	@Autowired
	private MemberMainService memberService;


	@GetMapping
	public String mainPage(@PathVariable String groupId, @RequestParam(required = false, value = "searchName") String search, Model model, HttpSession session) {
		GroupManagementDefaultVO group = groupService.retrieveGroup(groupId);
		List<ProjectVO> projectList = new ArrayList<ProjectVO>();
		if(search != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("groupId", groupId);
			map.put("search", search);
			projectList = service.retrieveGroupProjectSearchList(map);
		}else {
			projectList = service.retrieveGroupProjectList(groupId);
		}
		model.addAttribute("group", group);
		model.addAttribute("projectList", projectList);

		//도연추가
		MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");
		String memberId = user.getMemberId();

        Map<String, String> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("memberId", memberId);
		List<Map<String, Object>> projectLists = memberService.retrieveProjectList(params);
		session.setAttribute("projectLists", projectLists);
		//도연추가끝



//		List<ProjectMemberVO> proMemIdList = memberService.memIdProMemId(memberId);
//		System.out.println("!!!!!" + proMemIdList);
//
//
//		List<IssueVO> issueList = memberService.retrieveIssueList(proMemIdList);
//		System.out.println("!!!!!" + issueList);

		List<IssueVO> issueList = memberService.memAllIssue(memberId);

		model.addAttribute("issueList", issueList);

		return "tiles:groupManager/gm-main";
	}

	@GetMapping("groupUpdate")
	public String groupUpdateUI(@PathVariable String groupId, Model model) {
		GroupManagementVO group = groupService.retrieveGroupById(groupId);
		model.addAttribute("group", group);
		return "tiles:groupManager/groupUpdate";
	}

	@PostMapping("groupUpdate")
	public String groupUpdate(@ModelAttribute("group") GroupManagementVO group
			, Errors errors) {
		if(!errors.hasErrors()) {
			groupService.modifyGroup(group);
			return "redirect:/group/"+group.getGroupId();
		}else {
			return "tiles:groupManager/groupUpdate";
		}
	}

}
