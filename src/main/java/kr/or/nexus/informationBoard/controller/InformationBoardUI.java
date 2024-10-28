package kr.or.nexus.informationBoard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.nexus.informationBoard.service.InformationBoardService;

@Controller
@RequestMapping("group/{groupId}/informationBoardUI")
public class InformationBoardUI {

	@Autowired
	private InformationBoardService informationService;

	@GetMapping
	public String list(
		@PathVariable String groupId
		, Model model
	) {

		model.addAttribute("groupId", groupId);
		return "tiles:informationBoard/informationBoardList";

	}

}
