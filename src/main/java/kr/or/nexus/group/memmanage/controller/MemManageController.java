package kr.or.nexus.group.memmanage.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.or.nexus.commons.paging.DefaultPaginationRenderer;
import kr.or.nexus.commons.paging.DefaultPaginationRenderer2;
import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.commons.paging.PaginationRenderer;
import kr.or.nexus.commons.paging.SimpleCondition;
import kr.or.nexus.group.memmanage.service.GroupMemberManageService;
import kr.or.nexus.vo.GroupMemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/group/{groupId}/memberManage")
@Slf4j
public class MemManageController {
	
	@Autowired
	private GroupMemberManageService service;
	
	
	@GetMapping("list.do")
	public String groupMemberList(@PathVariable String groupId
			, @RequestParam(required = false, defaultValue = "1") int page
			, @ModelAttribute("condition") SimpleCondition simpleCondition
			, Model model) {
		
		PaginationInfo paging = new PaginationInfo();
		paging.setPage(page);
		paging.setSimpleCondition(simpleCondition);
		
		Map<String, Object> map = new HashedMap<String, Object>();
		map.put("groupId", groupId);
		map.put("paging", paging);
		
		List<GroupMemberVO> groupMemberList = service.retrieveGroupMemberList(map);
		
		PaginationRenderer renderer = new DefaultPaginationRenderer();
		String pagingHTML = renderer.renderPagination(paging);
		
		Integer totalCount = service.countGroupMember(groupId);
		
		model.addAttribute("pagingHTML", pagingHTML);
		model.addAttribute("groupId", groupId);
		model.addAttribute("groupMemberList", groupMemberList);
		model.addAttribute("totalCount", totalCount);
		return "tiles:groupManager/memberManage/groupMemberList";
	}
	
	@GetMapping("groupMember/{memberId}")
	public GroupMemberVO groupMemberDetail(@PathVariable String memberId) {
		GroupMemberVO groupMember = service.retrieveGroupMember(memberId);
		
		return groupMember;
	}
	
	@PostMapping("groupMember/{memberId}/deleteGroupMember")
	public void deleteGroupMember(@PathVariable String memberId, @PathVariable String groupId) {
		GroupMemberVO vo = new GroupMemberVO();
		vo.setMemberId(memberId);
		vo.setGroupId(groupId);
		service.removeGroupMember(vo);
	}
	
	@GetMapping("groupMember/search")
	public List<GroupMemberVO> searchGroupMember(@PathVariable String groupId, @RequestParam String memberName) {
		Map<String, Object> params = new HashedMap<String, Object>();
		params.put("groupId", groupId);
		params.put("memberName", memberName);
		List<GroupMemberVO> groupMember = service.searchGroupMember(params);
		
		return groupMember;
	}
}
