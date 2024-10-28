package kr.or.nexus.commons.alarmAndLog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class AlarmMidController {
	@RequestMapping("/issueAdapter")
	public String issueAdapter(String url, String last, RedirectAttributes redirectAttributes) {
		log.info("이슈어뎁터 들어와써??"+last);
		redirectAttributes.addFlashAttribute("last",last);
		return "redirect:"+url;
	}


	@RequestMapping("/gNoticeAdapter")
	public String projectAdapter(String url, String last, RedirectAttributes redirectAttributes) {
		log.info("그룹공지어뎁터들어와써??"+last);
		redirectAttributes.addFlashAttribute("last",last);
		return "redirect:"+url;
	}
	
	
	@RequestMapping("/eaAdapter")
	public String eaAdapter(String url, String last, RedirectAttributes redirectAttributes) {
		log.info("전자결재 들어와써???"+last);
		redirectAttributes.addFlashAttribute("last",last);
		return "redirect:"+url;
	}


}
