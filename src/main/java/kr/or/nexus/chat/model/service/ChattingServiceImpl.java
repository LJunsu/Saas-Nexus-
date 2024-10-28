package kr.or.nexus.chat.model.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.or.nexus.chat.model.dao.ChattingMapper;
import kr.or.nexus.vo.ChattingRoomMemberVO;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.ProjectRoleVO;
import kr.or.nexus.vo.def.ChattingRoomDefaultVO;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
public class ChattingServiceImpl implements ChattingService {



	@Autowired
	private ChattingMapper mapper;

	@Override
	public ChattingRoomDefaultVO selectChattingRoomById(String chattingRoomId) {

		return mapper.selectChattingRoomById(chattingRoomId);

	}

	@Override
	public String createChattingRoom(ChattingRoomDefaultVO chattingRoom) {
		mapper.insertChatRoom(chattingRoom);
		return mapper.selectLastInsertedRoomId(chattingRoom.getRoomName());

	}

	@Override
	public List<ChattingRoomDefaultVO> selectChattingRoomList() {

		return mapper.selectChattingRoomList();
	}

	public List<MemberManagementVO> getGroupMembersByUserId(String userId) {
	        try {
	            return mapper.retrieveGroupMemberByUserId(userId);
	        } catch (Exception e) {
	            // 예외를 로그에 기록하고, 예외를 다시 던집니다.
	            e.printStackTrace();
	            throw e;
	        }
	}

	@Override
	@Transactional
	public boolean addMembersToRoom(String chattingRoomId, List<String> members) {
	    log.info("Adding members to room. Room ID: {}, Members: {}", chattingRoomId, members);
	    try {
	        for (String memberId : members) {
	            ChattingRoomMemberVO memberVO = new ChattingRoomMemberVO();
	            memberVO.setChattingRoomId(chattingRoomId);
	            memberVO.setMemberId(memberId);
	            log.debug("Inserting member: {}", memberVO);
	            mapper.insertChatRoomMember(memberVO);
	        }
	        return true;
	    } catch (Exception e) {
	        log.error("Error adding members to room. Room ID: {}, Members: {}", chattingRoomId, members, e);
	        return false;
	    }
	}



	@Override
	public boolean removeMemberFromRoom(String chattingRoomId, String memberId) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("chattingRoomId", chattingRoomId);
	    params.put("memberId", memberId);

	    return mapper.deleteChattingRoomMember(params) > 0;
	}

	 @Override
	    public List<MemberManagementVO> getMembersByRoomId(String roomId) {
	        return mapper.getMembersByRoomId(roomId);
	    }

	@Override
	public List<ProjectRoleVO> getProjectRoleByUserId(String memberId) {
		// TODO Auto-generated method stub
		return mapper.getProjectRoleByUserId(memberId);
	}

}
