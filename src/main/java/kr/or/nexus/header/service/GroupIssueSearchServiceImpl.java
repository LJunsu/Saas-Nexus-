package kr.or.nexus.header.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.header.dao.GroupIssueSearchMapper;
import kr.or.nexus.header.vo.GroupIssueSearchVO;

@Service
public class GroupIssueSearchServiceImpl implements GroupIssueSearchService{
	
	@Autowired
	private GroupIssueSearchMapper mapper;

	public List<GroupIssueSearchVO> GroupIssueList(String groupId) {
		return mapper.GroupIssueSearch(groupId);
	}

}
