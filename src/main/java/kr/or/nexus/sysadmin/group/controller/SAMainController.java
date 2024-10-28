package kr.or.nexus.sysadmin.group.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.sysadmin.cs.service.SACsService;
import kr.or.nexus.sysadmin.group.service.SAGroupService;
import kr.or.nexus.sysadmin.member.service.SAMemberService;
import kr.or.nexus.vo.CustomerBoardVO;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SAMainController {
	
	private final SAMemberService memService;
	private final SAGroupService grpService;
	private final SACsService csService;
	
	@RequestMapping("/saMain.do")
	public String index(Model model, @RequestParam(required = false, defaultValue = "1") int page) {
		PaginationInfo paging = new PaginationInfo();
		paging.setPage(page);
		
		List<MemberManagementVO> memberList = memService.retrieveMemberList(paging);
		List<GroupManagementVO> groupList = grpService.retrieveGroupList(paging);
		List<CustomerBoardVO> csList = csService.retrieveCsList(paging);
		model.addAttribute("csList", csList);
		model.addAttribute("groupList", groupList);
		model.addAttribute("memberList", memberList);
		return "tiles3:sysAdmin/saMain";
	}
}
