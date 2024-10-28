package kr.or.nexus.member.nogroup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/noGroup")
public class NoGroupController {

	@GetMapping
	public String main() {
		return "tiles:groupMember/noGroup";
	}
}