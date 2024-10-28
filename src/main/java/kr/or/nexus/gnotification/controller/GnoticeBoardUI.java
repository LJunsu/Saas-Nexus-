package kr.or.nexus.gnotification.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class GnoticeBoardUI {

	@GetMapping("/group/{groupId}/noticeBoardUI")
	public String list(@PathVariable String groupId,Model model) {
		model.addAttribute("groupId",groupId);
	 return "tiles:GnoticeBoard/BoardList";
	}



}
