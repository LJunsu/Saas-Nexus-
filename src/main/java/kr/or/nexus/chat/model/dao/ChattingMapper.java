package kr.or.nexus.chat.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.ChattingRoomMemberVO;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.def.ChattingRoomDefaultVO;

@Mapper
public interface ChattingMapper {

public void insertChatRoom(ChattingRoomDefaultVO memberVO);

 ChattingRoomDefaultVO selectChattingRoomById(@Param("chattingRoomId")String chattingRoomId);

 List<ChattingRoomDefaultVO> selectChattingRoomList();

 List<MemberManagementVO> retrieveGroupMemberByUserId(@Param("memberId")String memberId);

public void insertMemberIntoChattingRoom(String chattingRoomId, List<String> members);

 int deleteChattingRoomMember(String chattingRoomId, String memberId);

public void addMemberToRoom(String chattingRoomId, String memberId);

public void insertChatRoomMember(ChattingRoomMemberVO memberVO);

public String selectLastInsertedRoomId(String roomName);

public int deleteChattingRoomMember(Map<String, Object> params);

public List<MemberManagementVO> getMembersByRoomId(String roomId);

public List<ProjectRoleVO> getProjectRoleByUserId(String memberId);


}
