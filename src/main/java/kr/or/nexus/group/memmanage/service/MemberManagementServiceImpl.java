package kr.or.nexus.group.memmanage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.or.nexus.enumpkg.ServiceResult;
import kr.or.nexus.group.memmanage.dao.GroupMemberManageMapper;
import kr.or.nexus.group.memmanage.dao.MemberManagementMapper;
import kr.or.nexus.security.util.PasswordUtil;
import kr.or.nexus.vo.GroupMemberVO;
import kr.or.nexus.vo.MemberManagementVO;

@Service
public class MemberManagementServiceImpl implements MemberManagementService {

	@Autowired
	private MemberManagementMapper dao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	GroupMemberManageMapper gmDao;

	@Override
	public MemberManagementVO findByUsername(String username) {
		MemberManagementVO member = dao.selectByPrimaryKey(username);
		if (member == null)
			throw new UsernameNotFoundException(username);
		return member;
	}

	@Override
	public List<MemberManagementVO> retrieveMemberFromEmail(String memberEmail) {
		return dao.selectByEmail(memberEmail);
	}

	@Override
	public void createGroupMember(GroupMemberVO gm) {
		gmDao.insertGroupMember(gm);
	}

	@Override
	public GroupMemberVO retrieveGroupMember(GroupMemberVO gm) {
		return gmDao.selectGroupMemberFromKey(gm);
	}

	@Override
	public ServiceResult createMember(MemberManagementVO member) {
		ServiceResult result = null;

		String encodedPassword = passwordEncoder.encode(member.getMemberPw());
		member.setMemberPw(encodedPassword);

		if (dao.selectByPrimaryKey(member.getMemberId()) == null) {
			result = dao.insertMember(member) > 0 ? ServiceResult.OK : ServiceResult.FAILED;
		} else {
			result = ServiceResult.PKDUPLICATE;
		}
		return result;
	}

	@Override
	public MemberManagementVO searchId(MemberManagementVO member) {
		return dao.searchId(member);
	}

	@Override
	public MemberManagementVO searchPw(MemberManagementVO member) {
		MemberManagementVO foundMember = dao.searchPw(member);
		if (foundMember != null) {
			String tempPassword = PasswordUtil.generateRandomPassword(10); // 10자리 임시 비밀번호 생성
			String encodedPassword = passwordEncoder.encode(tempPassword);
			foundMember.setMemberPw(encodedPassword);
			dao.updatePassword(foundMember); // 인코딩된 비밀번호로 업데이트
			foundMember.setMemberPw(tempPassword); // 이메일 전송을 위해 평문 비밀번호 설정
		}
		return foundMember;
	}

	@Override
	public MemberManagementVO findSessionUser(String memberId) {
		return dao.findSessionUser(memberId);
	}

	@Override
	public ServiceResult createGroupAdmin(MemberManagementVO member) {
		ServiceResult result = null;

		String encodedPassword = passwordEncoder.encode(member.getMemberPw());
		member.setMemberPw(encodedPassword);

		if (dao.selectByPrimaryKey(member.getMemberId()) == null) {
			result = dao.insertGroupAdmin(member) > 0 ? ServiceResult.OK : ServiceResult.FAILED;
		} else {
			result = ServiceResult.PKDUPLICATE;
		}
		return result;
	}

	@Override
	public ServiceResult createGroupAdminMember(GroupMemberVO gm) {
		ServiceResult result = null;
		
		result = dao.insertGroupAdminMember(gm) > 0 ? ServiceResult.OK : ServiceResult.FAILED;
		
		return result;
	}

	@Override
	public MemberManagementVO findByUseremail(String useremail) {
		MemberManagementVO member = dao.selectByUserEmail(useremail);
		if (member == null)
			throw new UsernameNotFoundException(useremail);
		return member;
	}

}
