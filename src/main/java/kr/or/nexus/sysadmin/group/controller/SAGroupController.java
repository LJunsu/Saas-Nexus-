package kr.or.nexus.sysadmin.group.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.nexus.commons.paging.DefaultPaginationRenderer2;
import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.commons.paging.PaginationRenderer;
import kr.or.nexus.commons.paging.SimpleCondition;
import kr.or.nexus.email.service.EmailService;
import kr.or.nexus.sysadmin.cs.exception.WriterAuthenticationException;
import kr.or.nexus.sysadmin.group.service.SAGroupService;
import kr.or.nexus.validate.groups.UpdateGroup;
import kr.or.nexus.vo.CustomerBoardAnswersVO;
import kr.or.nexus.vo.CustomerBoardVO;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.GroupRefusalVO;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/saGroup")
public class SAGroupController {
	
	private final SAGroupService service;

    private final EmailService emailService;
	
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
		
		List<GroupManagementVO> groupList = service.retrieveGroupList(paging);
		model.addAttribute("groupList", groupList);
		
		PaginationRenderer renderer = new DefaultPaginationRenderer2();
		String pagingHTML = renderer.renderPagination(paging);
		model.addAttribute("pagingHTML", pagingHTML);
		
		return "tiles3:sysAdmin/saGroupList";
	}
	
	@GetMapping("{groupId}")
	public String detail(Model model, @PathVariable String groupId) {
		GroupManagementVO groupDetail = service.retrieveGroup(groupId);
		model.addAttribute("groupDetail", groupDetail);
		model.addAttribute("groupId", groupId);
		return "tiles3:sysAdmin/saGroupDetail";
	}
	
	@PostMapping("{groupId}")
	@ResponseBody
	public Map<String, Object> insert(@RequestBody GroupRefusalVO groupRefusalVO, BindingResult errors, @PathVariable String groupId) throws Exception {
		Map<String, Object> result = new HashMap<>();
		result.put("target", groupRefusalVO);
		validator.validate(groupRefusalVO, errors, UpdateGroup.class);
		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors", 
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		}else {
		   service.insertGroupRefusal(groupRefusalVO);
		   emailService.sendGroupRefusal(groupRefusalVO);
		   result.put("success", true);
		   result.put("target", service.retrieveGroup(groupRefusalVO.getGroupId()));
		}
		return result;
	}
	
	@PutMapping("{groupId}")
	@ResponseBody
	public Map<String, Object> update(@RequestBody GroupManagementVO groupManagementVO, BindingResult errors, @PathVariable String groupId) {
		Map<String, Object> result = new HashMap<>();
		result.put("target", groupManagementVO);
		validator.validate(groupManagementVO, errors, UpdateGroup.class);
		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors", 
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		}else {
		   service.modifyGroupApproval(groupManagementVO);
		   result.put("success", true);
		   result.put("target", service.retrieveGroup(groupManagementVO.getGroupId()));
		}
		return result;
	}
}

