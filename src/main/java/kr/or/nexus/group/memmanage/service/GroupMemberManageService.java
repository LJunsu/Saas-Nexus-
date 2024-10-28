package kr.or.nexus.group.memmanage.service;

import java.util.List;
import java.util.Map;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.vo.GroupMemberVO;

public interface GroupMemberManageService {

	List<GroupMemberVO> retrieveGroupMemberList(Map<String, Object> map);

	GroupMemberVO retrieveGroupMember(String memberId);

	void removeGroupMember(GroupMemberVO vo);

	List<GroupMemberVO> searchGroupMember(Map<String, Object> params);

	List<GroupMemberVO> retrieveGroupMemberList(String groupId);

	Integer countGroupMember(String groupId);

}
