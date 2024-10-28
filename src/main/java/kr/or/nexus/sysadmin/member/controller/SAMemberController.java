package kr.or.nexus.sysadmin.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nexus.commons.paging.DefaultPaginationRenderer2;
import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.commons.paging.PaginationRenderer;
import kr.or.nexus.commons.paging.SimpleCondition;
import kr.or.nexus.sysadmin.cs.exception.WriterAuthenticationException;
import kr.or.nexus.sysadmin.member.service.SAMemberService;
import kr.or.nexus.validate.groups.UpdateGroup;
import kr.or.nexus.vo.CustomerBoardAnswersVO;
import kr.or.nexus.vo.CustomerBoardVO;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/saMember")
public class SAMemberController {

	private final SAMemberService service;
	
	@Autowired
	private SmartValidator validator;

	@GetMapping("list.do")
	public String list(
			@RequestParam(required = false, defaultValue = "1") int page
			, Model model
			, @ModelAttribute("condition") SimpleCondition simpleCondition
	) {
		PaginationInfo paging = new PaginationInfo();
		paging.setPage(page);
		paging.setSimpleCondition(simpleCondition);

		List<MemberManagementVO> memberList = service.retrieveMemberList(paging);
		List<GroupManagementVO> groupList = service.retrieveGroupList();
		model.addAttribute("memberList", memberList);
		model.addAttribute("groupList", groupList);

		PaginationRenderer renderer = new DefaultPaginationRenderer2();
		String pagingHTML = renderer.renderPagination(paging);
		model.addAttribute("pagingHTML", pagingHTML);

		return "tiles3:sysAdmin/saMemberList";
	}
	
	@GetMapping("{memberId}")
	@ResponseBody
	public MemberManagementVO detail(@PathVariable String memberId) {
		System.out.println(memberId);
		return service.retrieveMember(memberId);
	}
	
	@PutMapping("{memberId}")
	@ResponseBody
	public Map<String, Object> update(@RequestBody MemberManagementVO memberManagementVO, BindingResult errors, @PathVariable String memberId) {
		Map<String, Object> result = new HashMap<>();
		result.put("target", memberManagementVO);
		validator.validate(memberManagementVO, errors, UpdateGroup.class);
		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors", 
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		}else {
			try {
				service.modifyMemberStatus(memberManagementVO);
				result.put("success", true);
				result.put("target", service.retrieveMember(memberManagementVO.getMemberId()));
			}catch (WriterAuthenticationException e) {
				result.put("success", false);
				result.put("message", e.getMessage());
			}
		}
		return result;
	}
	
	@GetMapping("group/{groupId}")
	@ResponseBody
	public Map<String, Object> list2(
			@PathVariable String groupId
			, @RequestParam(required = false, defaultValue = "1") int page
	) {		
		System.out.println("그룹 아이디임" + groupId);
		Map<String, Object> result = new HashMap<>();
		PaginationInfo paging = new PaginationInfo();
		paging.setPage(page);
		List<MemberManagementVO> memberListByGroup = null;
		
		if(groupId == null || groupId.trim().isEmpty()|| groupId.equals("null")) {
			memberListByGroup = service.retrieveMemberListNoGroup(paging, groupId);
		}else {			
			memberListByGroup = service.retrieveMemberListByGroup(paging, groupId);
		}
		PaginationRenderer renderer = new DefaultPaginationRenderer2();
		String pagingHTML = renderer.renderPagination(paging);
		result.put("success", true);
		result.put("target", memberListByGroup);
		result.put("pagingHTML", pagingHTML);
		return result;
	}
}
