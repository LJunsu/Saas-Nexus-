package kr.or.nexus.group.memmanage.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;

@Mapper
public interface MemberManagementMapper {

	public MemberManagementVO selectByPrimaryKey(String username);

	public List<MemberManagementVO> selectByEmail(String memberEmail);

	public int insertMember(MemberManagementVO member);

	public MemberManagementVO searchId(MemberManagementVO member);

	public MemberManagementVO searchPw(MemberManagementVO member);

	public void updatePassword(MemberManagementVO foundMember);

	public MemberManagementVO findSessionUser(String memberId);

	public int insertGroupAdmin(MemberManagementVO member);

	public int insertGroupAdminMember(GroupMemberVO gm);

	public MemberManagementVO selectByUserEmail(String useremail);

	
	
}
