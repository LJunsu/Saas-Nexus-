package kr.or.nexus.member.main.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.IssueVO;
import kr.or.nexus.vo.ProjectMemberVO;

@Mapper
public interface MemberMainMapper {

	public String retrieveGroupId(String memberId);

	public List<Map<String, Object>> retrieveProjectList(Map<String, String> params);

	public List<IssueVO> retrieveIssueList(List<ProjectMemberVO> proMemList);

	public List<IssueVO> memAllIssue(String memberId);

	public List<ProjectMemberVO> memIdProMemId(String memberId);
}
