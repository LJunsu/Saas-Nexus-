package kr.or.nexus.sysadmin.cs.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.sysadmin.cs.service.SACsService;
import kr.or.nexus.validate.groups.InsertGroup;
import kr.or.nexus.vo.CustomerBoardVO;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/cs")
public class CsController {

	private final SACsService service;

	private SmartValidator validator;
	
	@PostMapping
	public Map<String, Object> insert(@RequestBody CustomerBoardVO customerBoardVO,  BindingResult errors) {
		System.out.println("컨트롤러로 넘어온 CustomerBoardVO : " + customerBoardVO);
		Map<String, Object> result = new HashMap<>();
//		validator.validate(customerBoardVO, errors, InsertGroup.class);
		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors",
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		} else {
			service.insertCustomerBoard(customerBoardVO);
			result.put("success", true);
		}
		return result;
	}
}
