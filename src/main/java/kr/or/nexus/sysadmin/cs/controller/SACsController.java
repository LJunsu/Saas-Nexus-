package kr.or.nexus.sysadmin.cs.controller;

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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import kr.or.nexus.sysadmin.cs.exception.WriterAuthenticationException;
import kr.or.nexus.sysadmin.cs.service.SACsService;
import kr.or.nexus.validate.groups.InsertGroup;
import kr.or.nexus.validate.groups.UpdateGroup;
import kr.or.nexus.vo.CustomerBoardAnswersVO;
import kr.or.nexus.vo.CustomerBoardVO;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/saSc")
public class SACsController {

	private final SACsService service;
	
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

		List<CustomerBoardVO> csList = service.retrieveCsList(paging);
		model.addAttribute("csList", csList);

		PaginationRenderer renderer = new DefaultPaginationRenderer2();
		String pagingHTML = renderer.renderPagination(paging);
		model.addAttribute("pagingHTML", pagingHTML);

		return "tiles3:sysAdmin/saCsList";
	}
	
	@GetMapping("{csId}")
	@ResponseBody
	public CustomerBoardVO detail(@PathVariable String csId) {
		System.out.println(csId);
		return service.retrieveCs(csId);
	}
	
	@PostMapping("{csId}")
	@ResponseBody
	public Map<String, Object> insert(@RequestBody CustomerBoardAnswersVO boardAnswers, BindingResult errors, @PathVariable String csId) {
		Map<String, Object> result = new HashMap<>();
		result.put("target", boardAnswers);
		validator.validate(boardAnswers, errors, InsertGroup.class);
		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors", 
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		}else {
			service.insertBoardAnswer(boardAnswers);
			result.put("success", true);
			result.put("target", service.retrieveCs(boardAnswers.getCsId()));
		}
		return result;
	}
	
	@DeleteMapping("{csId}")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody CustomerBoardAnswersVO boardAnswers, @PathVariable String csId){
		Map<String, Object> result = new HashMap<>();
		try {
			service.removeBoardAnswer(boardAnswers.getCsAnsIdx());
			result.put("success", true);
		}catch (WriterAuthenticationException e) {
			result.put("success", false);
			result.put("message", e.getMessage());
		}
		return result;
	}
	
	@PutMapping("{csId}")
	@ResponseBody
	public Map<String, Object> update(@RequestBody CustomerBoardAnswersVO boardAnswers, BindingResult errors, @PathVariable String csId) {
		Map<String, Object> result = new HashMap<>();
		result.put("target", boardAnswers);
		validator.validate(boardAnswers, errors, UpdateGroup.class);
		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors", 
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		}else {
			try {
				service.modifyBoardAnswer(boardAnswers);
				result.put("success", true);
				result.put("target", service.retrieveCs(boardAnswers.getCsId()));
			}catch (WriterAuthenticationException e) {
				result.put("success", false);
				result.put("message", e.getMessage());
			}
		}
		return result;
	}
}
