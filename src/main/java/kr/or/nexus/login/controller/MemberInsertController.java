package kr.or.nexus.login.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.nexus.cloud.service.GcpService;
import kr.or.nexus.enumpkg.ServiceResult;
import kr.or.nexus.group.main.service.GroupMainService;
import kr.or.nexus.group.memmanage.service.MemberManagementService;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/login")
@Slf4j
public class MemberInsertController {
	
	@Autowired
	private MemberManagementService service;
	
	@Autowired
	private GcpService gcpService;
	
	@Autowired
	private GroupMainService groupService;
	
	@ModelAttribute("member")
	public MemberManagementVO member() {
		return new MemberManagementVO();
	}
	
	@ModelAttribute("group")
	public GroupManagementVO group() {
		return new GroupManagementVO();
	}
	
	@GetMapping("/selectAccount")
	public String selectAccountUI() {
		
		return "tiles4:login/selectAccount";
	}
	
	@PostMapping("/idChk")
	public void idCheck(@RequestBody Map<String, Object> payload, Model model) {
		try {
			service.findByUsername((String) payload.get("inputId"));
			model.addAttribute("valid", false);
			model.addAttribute("message", "아이디 중복");
		}catch (UsernameNotFoundException e) {
			model.addAttribute("valid", true);
			model.addAttribute("message", "아이디 사용가능");
		}
	}
	
	@PostMapping("/eMailChk")
	public void eMailCheck(@RequestBody Map<String, Object> payload, Model model) {
		try {
			service.findByUseremail((String) payload.get("inputEmail"));
			model.addAttribute("valid", false);
			model.addAttribute("message", "이메일 중복");
		}catch (UsernameNotFoundException e) {
			model.addAttribute("valid", true);
			model.addAttribute("message", "");
		}
	}
	
	@GetMapping("/createAccount")
	public String createAccountUI() {
		
		return "tiles4:login/createAccountUI";
	}
	
	@GetMapping("/createAccount_gm")
	public String createAccountUI_gm() {
		
		return "tiles4:login/createAccountUI_gm";
	}
	
	@GetMapping("/createGroup")
	public String createGroupUI() {
		
	    return "tiles4:login/createGroupUI";
	}
	
	@PostMapping("/createGroup")
	public String createGroup(HttpSession session
	        , @ModelAttribute("group") GroupManagementVO group
	        , @RequestParam("groupProfile") MultipartFile groupProfile
	        , @RequestParam("businessLicense") MultipartFile businessLicense
	        , Errors errors
	        , RedirectAttributes redirectAttributes) throws IOException {
	    
		MemberManagementVO member = (MemberManagementVO) session.getAttribute("accountMember");
		log.info("ppp {}",member);
	    if(!errors.hasErrors()) {
	        
	    	// 그룹 관리자 생성 및 그룹 생성을 함께 처리
	    	ServiceResult result = groupService.createGroupWithAdmin(group, member);
	    	
	        String folderPath = group.getGroupId();
	        String url = gcpService.uploadProfile(groupProfile, folderPath);
	        String licenseId = gcpService.uploadEnterFile(businessLicense, folderPath);
	        if(url != null) {
	        	group.setGroupProfilePicture(url);
	        	group.setBusinessLicenseId(licenseId);
	        	groupService.updateGroupProfile(group);
	        }
	        
	        
	        if(ServiceResult.OK.equals(result)) {
	        	session.removeAttribute("accountMember");
	            return "redirect:/login.do";
	        } else {
	            redirectAttributes.addFlashAttribute("message", "회원가입 중 에러");
	            redirectAttributes.addFlashAttribute("group", group);
	            redirectAttributes.addFlashAttribute("member", member);
	            return "redirect:/login/createGroup";
	        }
	    } else {
	        redirectAttributes.addFlashAttribute("group", group);
	        redirectAttributes.addFlashAttribute("member", member);
	        String attrName = BindingResult.MODEL_KEY_PREFIX + "group";
	        redirectAttributes.addFlashAttribute("attrName", attrName);
	        return "redirect:/login/createGroup";
	    }
	}
	
	@PostMapping("/createAccount_gm")
	public String createAccount_gm(@ModelAttribute("member") MemberManagementVO member
			, @RequestParam ("memberImage") MultipartFile file
			, HttpSession session
			, Errors errors
			, RedirectAttributes redirectAttributes) throws IOException {
		
		String view = null;
		if(!errors.hasErrors()) {
			String folderPath = member.getMemberId();
			String url = gcpService.uploadProfile(file, folderPath);
			if(url != null)
				member.setMemberProfilePict(url);
			
	        session.setAttribute("accountMember", member);
	        view = "redirect:/login/createGroup";
		}else {
			redirectAttributes.addFlashAttribute("member", member);
			String attrName = BindingResult.MODEL_KEY_PREFIX+"member";
			redirectAttributes.addFlashAttribute("attrName", attrName);
			view = "redirect:/login/createAccount_gm";
		}
		return view;
		
	}
	
	@PostMapping("/createAccount")
	public String createAccount(@ModelAttribute("member") MemberManagementVO member
			, @RequestParam ("memberImage") MultipartFile file
			, Errors errors
			, RedirectAttributes redirectAttributes) throws IOException {
		
		String view = null;
		if(!errors.hasErrors()) {
			String folderPath = member.getMemberId();
			String url = gcpService.uploadProfile(file, folderPath);
			if(url != null)
				member.setMemberProfilePict(url);
			ServiceResult result = service.createMember(member);
			if(ServiceResult.OK.equals(result)) {
				view = "redirect:/login.do";
				
			}else {
				redirectAttributes.addFlashAttribute("message", "아이디 중복");
				redirectAttributes.addFlashAttribute("member", member);
				view = "redirect:/login/createAccount";
			}
			
		}else {
			redirectAttributes.addFlashAttribute("member", member);
			String attrName = BindingResult.MODEL_KEY_PREFIX+"member";
			redirectAttributes.addFlashAttribute("attrName", errors);
			view = "redirect:/login/createAccount";
		}
		return view;
		
	}
}
