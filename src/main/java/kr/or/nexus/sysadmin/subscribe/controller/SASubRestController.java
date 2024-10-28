package kr.or.nexus.sysadmin.subscribe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.sysadmin.subscribe.service.SASubService;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/saSub/rest")
public class SASubRestController {
	
	private final SASubService service;

	@GetMapping
	public Map<String, Object> list(@RequestParam(required = false) String justYearMonth
			, @RequestParam(required = false) String startDate
			, @RequestParam(required = false) String lastDate) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> params = new HashMap<>();        
		params.put("justYearMonth", justYearMonth);
		params.put("startDate", startDate);
		params.put("lastDate", lastDate);
		List<Integer> memCountByRegDt = service.retrieveMemCountByRegDt(params);
		List<Integer> subCountByRegDt = service.retrieveSubCountByRegDt(params);
		result.put("success", true);
		result.put("target", memCountByRegDt);
		result.put("target2", subCountByRegDt);
		return result;
	}
	
	@GetMapping("{goToControllerDate}")
	public Map<String, Object> list2(@PathVariable String goToControllerDate) {
		Map<String, Object> result = new HashMap<>();   
		List<MemberManagementVO> memDetailByRegDt = service.retrieveMemDetailByRegDt(goToControllerDate);
		List<Map<String, Object>> subDetailByRegDt = service.retrieveSubDetailByRegDt(goToControllerDate);
		result.put("success", true);
		result.put("target", memDetailByRegDt);
		result.put("target2", subDetailByRegDt);
		return result;
	}
	
	@GetMapping("total/{groupId}")
	public Map<String, Object> list3(@PathVariable String groupId) {
		Map<String, Object> result = new HashMap<>();
		Map<String, Object> totalByGroup = service.retrieveTotalByGroup(groupId);
		List<Map<String, Object>> payTotalByGroup = service.retrievePayTotalByGroup(groupId);
		result.put("success", true);
		result.put("target", totalByGroup);
		result.put("target2", payTotalByGroup);
		return result;
	}
	
	@GetMapping("payment")
	public Map<String, Object> list4(@RequestParam(required = false) String startDate, @RequestParam(required = false) String lastDate) {
		Map<String, Object> result = new HashMap<>();
		Map<String, String> params = new HashMap<>();        
		params.put("startDate", startDate);
		params.put("lastDate", lastDate);
		List<Map<String, Object>> paymentDetailList = service.retrievePaymentDetailList(params);
		result.put("success", true);
		result.put("target", paymentDetailList);
		return result;
	}
}