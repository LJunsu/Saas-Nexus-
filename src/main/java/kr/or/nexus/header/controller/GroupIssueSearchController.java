package kr.or.nexus.header.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.nexus.header.service.GroupIssueSearchService;
import kr.or.nexus.header.service.GroupIssueSearchServiceImpl;
import kr.or.nexus.header.vo.GroupIssueSearchVO;

@RestController
@RequestMapping("/rest/{groupId}/issueSearch")
public class GroupIssueSearchController {
	@Autowired
	private GroupIssueSearchService service;

	@GetMapping
	public List<GroupIssueSearchVO> selectProjectIssue(@PathVariable("groupId") String groupId){
		List<GroupIssueSearchVO> list = service.GroupIssueList(groupId);
		return list;
	}
}
