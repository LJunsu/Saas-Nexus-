package kr.or.nexus.sysadmin.subscribe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.sysadmin.subscribe.service.SASubService;
import kr.or.nexus.vo.GroupManagementVO;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("/saSub")
public class SASubController {
	
	private final SASubService service;

	@GetMapping("list.do")
	public String list(Model model) {

		Map<String, String> params = new HashMap<>();        
		params.put("justYearMonth", "2024-09");
		params.put("startDate", "2024-09-01");
		params.put("lastDate", "2024-09-30");
		List<Integer> memCountByRegDt = service.retrieveMemCountByRegDt(params);
		List<Integer> subCountByRegDt = service.retrieveSubCountByRegDt(params);
		System.out.println("구독자 수 날별 카운드" + subCountByRegDt);
		model.addAttribute("memCountByRegDt", memCountByRegDt);
		model.addAttribute("subCountByRegDt", subCountByRegDt);
		
		List<GroupManagementVO> groupList = service.retrieveGroupList();
		model.addAttribute("groupList", groupList);
		
		List<Integer> paymentAmount = service.retrievePaymentAmountList();
		model.addAttribute("paymentAmount", paymentAmount);
		
		List<Map<String, Object>> paymentDetailList = service.retrievePaymentDetailList(params);
		model.addAttribute("paymentDetailList", paymentDetailList);
		
		String totalPay = service.retrieveTotalPay();
		model.addAttribute("totalPay", totalPay);

		return "tiles3:sysAdmin/saSubList";
	}
}