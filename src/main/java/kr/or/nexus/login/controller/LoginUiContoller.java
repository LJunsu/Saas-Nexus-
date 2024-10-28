package kr.or.nexus.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.group.memmanage.service.MemberManagementService;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping
@Slf4j
public class LoginUiContoller {
	
	@Autowired
	private MemberManagementService service;

	@GetMapping("/login.do")
	public String index() {
		return "tiles4:login/loginUI";
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		HttpSession session = request.getSession();
		session.removeAttribute("user");
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return ResponseEntity.ok().body("Logout successful");
	}

	@GetMapping("/login/loginSuccess")
	public String process(HttpSession session) {
		
		MemberManagementVO member = (MemberManagementVO) session.getAttribute("user");

		String groupId = member.getGroupMemberVO().getGroupId();
		if(member.getMemberRole().equals("ROLE_ADMIN")) {
			return "redirect:/saMain.do";
		}else if(member.getMemberRole().equals("ROLE_GROUPADMIN")) {
			
			return "redirect:/group/"+groupId;
		}else { // ROLE_USER일 때
			if(member.getGroupMemberVO().getGroupId() == null) {
				return "landing-page";
			}
			
			return "redirect:/"+ groupId + "/memMain.do";
		}

	}

}
