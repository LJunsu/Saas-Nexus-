package kr.or.nexus.kanban.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.kanban.service.kanbanRestService;
import kr.or.nexus.timeline.service.TimeLineService;
import kr.or.nexus.vo.CustomUserVOWrapper;
import kr.or.nexus.vo.IssueTagsVO;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.MemberManagementVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("{projectId}/kanban/rest")
@RestController
public class KanbanRestController {

	@Autowired
	private kanbanRestService service;

	@Autowired
	private TimeLineService timeService;

	@GetMapping("getMyIssueList")
	public Map<String, Object> getIssueList(Authentication authentication, @PathVariable String projectId) {
		Map<String, Object> map = new HashMap<String, Object>();
		CustomUserVOWrapper wrapper = (CustomUserVOWrapper) authentication.getPrincipal();
		MemberManagementVO realUser = wrapper.getRealUser();
		String memberId = realUser.getMemberId();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("projectId", projectId);
		data.put("memberId", memberId);
		List<IssueVO> myIssueList = service.getMyIssueList(data);
		map.put("List", myIssueList);

		return map;
	}

	@PutMapping("updateIssue")
	public Map<String, Object> updateIssue(@RequestBody IssueVO issueVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(issueVO);
		int cnt = service.updateIssue(issueVO);
		if (cnt > 0) {
			map.put("success", "success");
			map.put("cnt", cnt);
		}
		return map;
	}

	@PutMapping("updateAllIssue")
	public Map<String, Object> updateAllIssue(@RequestBody IssueVO issueVO){
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(issueVO);
		int chk =  service.timeLineUpdate(issueVO);
		int cnt =  service.updateTag(issueVO);
		if(chk > 0 && cnt>0) {
			map.put("success", "success");
			map.put("cnt", chk);
		}
	
		return map;
	}
	
	
	@PostMapping("addIssue")
	public Map<String, Object> addIssue(@RequestBody IssueVO issueVO){
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println(issueVO);
		String issueIdx =  service.addIssue(issueVO);
		if(issueIdx !=null) {
			Map<String,String> tag = new HashMap<String, String>();
			tag.put("issueIdx", issueIdx);
			tag.put("issueTagName", issueVO.getIssueTagName());
			int chk =service.addIssueTags(tag);
			if(chk>0) {
				map.put("success", true);
				map.put("cnt", issueIdx);
			}
		}

		return map;
	}
	
	
	@DeleteMapping("/issues/{issueIdx}")
	public Map<String, Object> addIssue(@PathVariable String issueIdx){
		Map<String, Object> map = new HashMap<String, Object>();
		int chk =  service.removeIssue(issueIdx);
			if(chk>0) {
				map.put("success", true);
			}

		return map;
	}
	











}
