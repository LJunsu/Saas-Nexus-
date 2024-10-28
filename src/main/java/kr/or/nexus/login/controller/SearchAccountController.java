package kr.or.nexus.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nexus.email.service.EmailService;
import kr.or.nexus.group.memmanage.service.MemberManagementService;
import kr.or.nexus.vo.MemberManagementVO;

@Controller
@RequestMapping("/login")
public class SearchAccountController {

	@Autowired
	private MemberManagementService service;

	@Autowired
	private EmailService emailService;

	@GetMapping("/searchId")
	public String searchIdUI() {

		return "tiles4:login/searchId";
	}

	@GetMapping("/searchPw")
	public String searchPwUI() {

		return "tiles4:login/searchPw";
	}

	@PostMapping("/searchId")
	public String searchId(@ModelAttribute MemberManagementVO member, RedirectAttributes redirectAttributes) {
		MemberManagementVO searchAccount = service.searchId(member);
		if (searchAccount != null) {

			String sendTo = searchAccount.getMemberEmail();
			String memberId = searchAccount.getMemberId();
			try {
				emailService.sendId(sendTo, memberId);
				redirectAttributes.addFlashAttribute("message", "ID가 이메일로 전송되었습니다.");
				return "redirect:/login.do";
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", "메시지 전송 실패!");
				return "redirect:/login/searchId";
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "해당 계정을 찾을 수 없습니다.");
			return "redirect:/login/searchId";
		}

	}

	@PostMapping("/searchPw")
	public String searchPw(@ModelAttribute MemberManagementVO member, RedirectAttributes redirectAttributes) {
		MemberManagementVO searchAccount = service.searchPw(member);
		if (searchAccount != null) {
			String sendTo = searchAccount.getMemberEmail();
			String tempPassword = searchAccount.getMemberPw();
			try {
				emailService.sendPw(sendTo, tempPassword);
				redirectAttributes.addFlashAttribute("message", "임시 비밀번호가 이메일로 전송되었습니다.");
				return "redirect:/login.do";
			} catch (Exception e) {
				e.printStackTrace();
				redirectAttributes.addFlashAttribute("message", "메시지 전송 실패!");
				return "redirect:/login/searchPw";
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "해당 계정을 찾을 수 없습니다.");
			return "redirect:/login/searchPw";
		}

	}
}
