package kr.or.nexus.group.memmanage.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.vo.AnnouncementVO;
import kr.or.nexus.vo.GroupMemberVO;

@Mapper
public interface GroupMemberManageMapper {

	public List<GroupMemberVO> selectGroupMemberList(String groupId);
	
	public int selectTotalRecord(Map<String, Object> map);
	
	public AnnouncementVO selectAnnouncement(String boardId);

	public GroupMemberVO selectGroupMember(String memberId);

	public void insertGroupMember(GroupMemberVO gm);

	public GroupMemberVO selectGroupMemberFromKey(GroupMemberVO gm);

	public void deleteGroupMember(GroupMemberVO vo);

	public List<GroupMemberVO> searchGroupMember(Map<String, Object> params);

	public List<GroupMemberVO> pagingGroupMemberList(Map<String, Object> map);

	public Integer countGroupMember(String groupId);

}
