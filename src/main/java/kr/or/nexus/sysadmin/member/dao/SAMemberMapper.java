package kr.or.nexus.sysadmin.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;

@Mapper
public interface SAMemberMapper {
	
	public int selectTotalRecord(PaginationInfo paging);

	public List<MemberManagementVO> retrieveMemberList(PaginationInfo paging);

	public MemberManagementVO retrieveMember(String memberId);

	public void modifyMemberStatus(MemberManagementVO memberManagementVO);

	public List<GroupManagementVO> retrieveGroupList();

	public List<MemberManagementVO> retrieveMemberListByGroup(@Param("paging") PaginationInfo paging, @Param("groupId") String groupId);

	public List<MemberManagementVO> retrieveMemberListNoGroup(@Param("paging") PaginationInfo paging, @Param("groupId") String groupId);

	public int selectTotalRecord2(PaginationInfo paging,@Param("groupId") String groupId);
}
