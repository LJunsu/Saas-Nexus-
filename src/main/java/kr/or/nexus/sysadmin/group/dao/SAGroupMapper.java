package kr.or.nexus.sysadmin.group.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.GroupRefusalVO;
import kr.or.nexus.vo.MemberManagementVO;

@Mapper
public interface SAGroupMapper {
	
	public int selectTotalRecord(PaginationInfo paging);

	public List<GroupManagementVO> retrieveGroupList(PaginationInfo paging);

	public GroupManagementVO retrieveGroup(String groupId);

	public void modifyGroupApproval(GroupManagementVO groupManagementVO);

	public void insertGroupRefusal(GroupRefusalVO groupRefusalVO);
}
