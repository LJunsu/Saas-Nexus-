package kr.or.nexus.group.memmanage.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.group.memmanage.dao.GroupMemberManageMapper;
import kr.or.nexus.vo.AnnouncementVO;
import kr.or.nexus.vo.GroupMemberVO;

@Service
public class GroupMemberManageServiceImpl implements GroupMemberManageService {

	@Autowired
	private GroupMemberManageMapper dao;
	
	@Override
	public List<GroupMemberVO> retrieveGroupMemberList(String groupId) {
		return dao.selectGroupMemberList(groupId);
	}

	@Override
	public GroupMemberVO retrieveGroupMember(String memberId) {
		return dao.selectGroupMember(memberId);
	}

	@Override
	public void removeGroupMember(GroupMemberVO vo) {
		dao.deleteGroupMember(vo);
	}

	@Override
	public List<GroupMemberVO> searchGroupMember(Map<String, Object> params) {
		return dao.searchGroupMember(params);
	}

	@Override
	public List<GroupMemberVO> retrieveGroupMemberList(Map<String, Object> map) {
		PaginationInfo paging = (PaginationInfo) map.get("paging");
		int totalRecord = dao.selectTotalRecord(map);
		paging.setTotalRecord(totalRecord);
		
		return dao.pagingGroupMemberList(map);
	}

	@Override
	public Integer countGroupMember(String groupId) {
		return dao.countGroupMember(groupId);
	}

}
