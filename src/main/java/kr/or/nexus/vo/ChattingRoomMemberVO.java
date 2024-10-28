package kr.or.nexus.vo;

import lombok.Data;

@Data
public class ChattingRoomMemberVO {
	private String chattingRoomId;

	private String memberId;

	private MemberManagementVO member;


}
