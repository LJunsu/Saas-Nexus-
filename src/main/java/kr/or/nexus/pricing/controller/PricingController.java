package kr.or.nexus.pricing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PricingController {
	
	// @RequestMapping(value = "/index.do") 등록을 해야 @Controller가 그냥 빈에서 컨트롤러가 됨
	@RequestMapping("/pricing.do")
	public String index() {
		return "tiles:groupMember/pricing";
	}
	
}
