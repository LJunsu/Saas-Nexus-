package kr.or.nexus.sysadmin.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.sysadmin.member.dao.SAMemberMapper;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;

@Service
public class SAMemberServiceImpl implements SAMemberService {
	
	@Autowired
	private SAMemberMapper dao;

	@Override
	public List<MemberManagementVO> retrieveMemberList(PaginationInfo paging) {
		int totalRecord = dao.selectTotalRecord(paging);
		paging.setTotalRecord(totalRecord);
		return dao.retrieveMemberList(paging);
	}

	@Override
	public MemberManagementVO retrieveMember(String memberId) {
		return dao.retrieveMember(memberId);
	}

	@Override
	public void modifyMemberStatus(MemberManagementVO memberManagementVO) {
		dao.modifyMemberStatus(memberManagementVO);
	}

	@Override
	public List<GroupManagementVO> retrieveGroupList() {
		return dao.retrieveGroupList();
	}

	@Override
	public List<MemberManagementVO> retrieveMemberListByGroup(PaginationInfo paging, String groupId) {
		int totalRecord = dao.selectTotalRecord2(paging, groupId);
		paging.setTotalRecord(totalRecord);
		return dao.retrieveMemberListByGroup(paging, groupId);
	}

	@Override
	public List<MemberManagementVO> retrieveMemberListNoGroup(PaginationInfo paging, String groupId) {
		int totalRecord = dao.selectTotalRecord2(paging, groupId);
		paging.setTotalRecord(totalRecord);
		return dao.retrieveMemberListNoGroup(paging, groupId);
	}

}
