package kr.or.nexus.group.memmanage.service;

import java.util.List;

import kr.or.nexus.enumpkg.ServiceResult;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;

public interface MemberManagementService {

	public MemberManagementVO findByUsername(String username);

	public List<MemberManagementVO> retrieveMemberFromEmail(String memberEmail);

	public void createGroupMember(GroupMemberVO gm);

	public GroupMemberVO retrieveGroupMember(GroupMemberVO gm);

	public ServiceResult createMember(MemberManagementVO member);

	public MemberManagementVO searchId(MemberManagementVO member);

	public MemberManagementVO searchPw(MemberManagementVO member);

	public MemberManagementVO findSessionUser(String memberId);

	public ServiceResult createGroupAdmin(MemberManagementVO member);

	public ServiceResult createGroupAdminMember(GroupMemberVO gm);

	public MemberManagementVO findByUseremail(String useremail);

}
