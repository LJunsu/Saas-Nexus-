package kr.or.nexus.member.main.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.member.main.service.MemberMainService;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectMemberVO;

@Controller
@RequestMapping("/{groupId}/memMain.do")
public class MemberMainController {

	@Autowired
	private MemberMainService service;

	@GetMapping
	public String index(Model model, HttpSession session) {
		MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");
		String memberId = user.getMemberId();
		String groupId = user.getGroupMemberVO().getGroupId();

        Map<String, String> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("memberId", memberId);
		List<Map<String, Object>> projectList = service.retrieveProjectList(params);
		session.setAttribute("projectList", projectList);


//		List<ProjectMemberVO> proMemIdList = service.memIdProMemId(memberId);
//		System.out.println("!!!!!" + proMemIdList);
//
//
//		List<IssueVO> issueList = service.retrieveIssueList(proMemIdList);
//		System.out.println("issueList!!!!! ; " + issueList);

		List<IssueVO> issueList = service.memAllIssue(memberId);

		model.addAttribute("projectList", projectList);
		model.addAttribute("issueList", issueList);
		model.addAttribute("groupId", groupId);
		return "tiles:groupMember/gm-main";
	}
}
