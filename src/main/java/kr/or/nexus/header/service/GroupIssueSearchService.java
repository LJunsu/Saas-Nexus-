package kr.or.nexus.header.service;

import java.util.List;

import kr.or.nexus.header.vo.GroupIssueSearchVO;

public interface GroupIssueSearchService {

	
	public List<GroupIssueSearchVO> GroupIssueList(String groupId);
}
