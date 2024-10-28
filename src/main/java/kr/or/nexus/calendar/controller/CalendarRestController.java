package kr.or.nexus.calendar.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.calendar.service.CalendarService;
import kr.or.nexus.sysadmin.cs.exception.WriterAuthenticationException;
import kr.or.nexus.validate.groups.InsertGroup;
import kr.or.nexus.validate.groups.UpdateGroup;
import kr.or.nexus.vo.GitVO;
import kr.or.nexus.vo.IssueVO;


@RestController
@RequestMapping("{groupId}/{projectId}/calendar")
public class CalendarRestController {

	@Autowired
	private CalendarService service;
	
	@Autowired
	private SmartValidator validator;
	
    private String groupId;
    private String projectId;
    
    @ModelAttribute
    public void setPathVariables(@PathVariable String groupId, @PathVariable String projectId) {
        this.groupId = groupId;
        this.projectId = projectId;
    }
    
	
	@GetMapping("{issueIdx}")
	@ResponseBody
	public IssueVO detail(@PathVariable String issueIdx) {
		System.out.println(issueIdx);
		return service.retrieveIssue(issueIdx);
	}

	@PostMapping
	public Map<String, Object> insert(IssueVO issueVO,  BindingResult errors) {
		System.out.println("issueVO !!!!! : " + issueVO);
		issueVO.setProjectId(projectId);
		Map<String, Object> result = new HashMap<>();
		validator.validate(issueVO, errors, InsertGroup.class);
		if(issueVO.getSubIssueIdx() == null || issueVO.getSubIssueIdx().trim().isEmpty() || issueVO.getSubIssueIdx().equals("선택")) {
			issueVO.setSubIssueIdx(null);
		}

		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {
			service.insertIssue(issueVO);
			List<IssueVO> issueList = service.retrieveIssueList(projectId);
			result.put("success", true);
			result.put("target", issueList);
		}
		return result;
	}
	
	@PostMapping("{issueIdx}")
	public Map<String, Object> update(@PathVariable String issueIdx, IssueVO issueVO,  BindingResult errors) {
		issueVO.setProjectId(projectId);
		issueVO.setIssueIdx(issueIdx);
		System.out.println("issueVO !!!!! : " + issueVO);
		Map<String, Object> result = new HashMap<>();
		validator.validate(issueVO, errors, UpdateGroup.class);
	    // issueTags가 null인 경우 빈 리스트로 초기화
	    if (issueVO.getIssueTags() == null || issueVO.getIssueTags().isEmpty() || issueVO.getIssueTags().equals("선택")) {
	        issueVO.setIssueTags(null);
	    }
		
		if(issueVO.getSubIssueIdx() == null || issueVO.getSubIssueIdx().trim().isEmpty() || issueVO.getSubIssueIdx().equals("선택")) {
			issueVO.setSubIssueIdx(null);
		}
		
		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
					errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
					);
		} else {
			service.updateIssue(issueVO);
			List<IssueVO> issueList = service.retrieveIssueList(projectId);
			result.put("success", true);
			result.put("target", issueList);
		}
		return result;
	}
	
	@DeleteMapping("{issueIdx}")
	public Map<String, Object> delete(@PathVariable String issueIdx) {
		Map<String, Object> result = new HashMap<>();
		try {
			service.deleteIssue(issueIdx);
			List<IssueVO> issueList = service.retrieveIssueList(projectId);
			result.put("success", true);
			result.put("target", issueList);
		}catch (WriterAuthenticationException e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	@PostMapping("git")
	public Map<String, Object> insertGit(@RequestBody GitVO gitVO, BindingResult errors) {
		Map<String, Object> result = new HashMap<>();
		System.out.println("gitVO !!!!! : " + gitVO);
		try {
			service.insertGit(gitVO);
			GitVO gitDetail = service.retrieveGit(projectId);
			result.put("success", true);
			result.put("target", gitDetail);
		}catch (WriterAuthenticationException e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
}