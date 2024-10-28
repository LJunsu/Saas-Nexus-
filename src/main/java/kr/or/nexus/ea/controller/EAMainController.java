package kr.or.nexus.ea.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.ea.service.EAMainService;
import kr.or.nexus.validate.groups.UpdateGroup;
import kr.or.nexus.vo.ElectronicApprovalVO;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;

@RestController
@RequestMapping("/{groupId}/ea/rest")
public class EAMainController {
	
	@Autowired
	private EAMainService service;
	
	@Autowired
	private SmartValidator validator;

	@GetMapping
	public Map<String,Object> list(@PathVariable String groupId, Model model, HttpSession session, @RequestParam(required = false) String approvalProgressStatus) {
		MemberManagementVO user = (MemberManagementVO) session.getAttribute("user");		
		String memberId = user.getMemberId();
		System.out.println("멤버아이디는 " + memberId);
		System.out.println("현재상태는 : " + approvalProgressStatus);
		List<Map<String, Object>> eaList = null;
		if(approvalProgressStatus.equals("totalApproval")) {
			eaList = service.retrieveEAList(memberId);
		}else {
			Map<String, Object> params = new HashMap<>();
			params.put("memberId", memberId);
			params.put("approvalProgressStatus", approvalProgressStatus);
			eaList = service.retrieveEAListByProgress(params);
		}
		Map<String, Object> response = new HashMap<>();
		response.put("eaList", eaList);
		System.out.println("eaList!!!!!!!!!!!!" + eaList);
		return response;
	}
	
	@PostMapping("{eaId}")
	public Map<String, Object> update(@RequestBody ElectronicApprovalVO electronicApprovalVO, BindingResult errors, @PathVariable String eaId) {
		Map<String, Object> result = new HashMap<>();
		result.put("target", electronicApprovalVO);
		validator.validate(electronicApprovalVO, errors, UpdateGroup.class);
		if(errors.hasErrors()) {
			result.put("success", false);
			result.put("errors", 
				errors.getFieldErrors().stream()
					.collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage))
			);
		}else {
		   service.modifyElectronicApproval(electronicApprovalVO);
		   result.put("success", true);
		   result.put("target", service.retrieveEa(electronicApprovalVO.getEaId()));
		}
		return result;
	}
}
