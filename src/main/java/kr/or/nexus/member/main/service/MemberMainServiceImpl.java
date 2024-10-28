package kr.or.nexus.member.main.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.member.main.dao.MemberMainMapper;
import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;

@Service
public class MemberMainServiceImpl implements MemberMainService {

	@Autowired
	private MemberMainMapper dao;

	@Override
	public String retrieveGroupId(String memberId) {
		return dao.retrieveGroupId(memberId);
	}

	@Override
	public List<Map<String, Object>> retrieveProjectList(Map<String, String> params) {
		return dao.retrieveProjectList(params);
	}

	@Override
	public List<IssueVO> retrieveIssueList(List<ProjectMemberVO> proMemList) {
		return dao.retrieveIssueList(proMemList);
	}

	@Override
	public List<ProjectMemberVO> memIdProMemId(String memberId) {

		return dao.memIdProMemId(memberId);

	}

	@Override
	public List<IssueVO> memAllIssue(String memberId) {

		return dao.memAllIssue(memberId);

	}
}
