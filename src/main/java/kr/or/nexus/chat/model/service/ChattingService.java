package kr.or.nexus.chat.model.service;

import java.util.List;
import java.util.Map;

import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.def.ChattingRoomDefaultVO;

public interface ChattingService {

	ChattingRoomDefaultVO selectChattingRoomById(String chattingRoomId);

	String createChattingRoom(ChattingRoomDefaultVO newRoom);

	List<ChattingRoomDefaultVO> selectChattingRoomList();

	List<MemberManagementVO> getGroupMembersByUserId(String memberId);

	boolean addMembersToRoom(String chattingRoomId, List<String> members);

	boolean removeMemberFromRoom(String chattingRoomId, String memberId);

	List<MemberManagementVO> getMembersByRoomId(String chattingRoomId);

	List<ProjectRoleVO> getProjectRoleByUserId(String memberId);


}
