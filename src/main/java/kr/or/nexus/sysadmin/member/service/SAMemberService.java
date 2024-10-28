package kr.or.nexus.sysadmin.member.service;

import java.util.List;
import java.util.Map;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;

/**
 * @author PC-22
 *
 */
public interface SAMemberService {
	
	/**
	 * 회원 목록 조회
	 * @return
	 */
	public List<MemberManagementVO> retrieveMemberList(PaginationInfo paging);
	
    /**
     * 회원 상세 정보 조회
     * @param memId
     * @return 존재하지 않는 경우, {@link UserNotFoundException 발생}
     */
    public MemberManagementVO retrieveMember(String memId);

	/**
	 * 회원 탈퇴 상태 변경
	 * @param memberManagementVO
	 */
	public void modifyMemberStatus(MemberManagementVO memberManagementVO);

	/**
	 * 회원 소속 그룹 리스트 출력
	 * @return
	 */
	public List<GroupManagementVO> retrieveGroupList();

	/**
	 * 그룹별 회원 리스트
	 * @param paging
	 * @param groupId
	 * @return
	 */
	public List<MemberManagementVO> retrieveMemberListByGroup(PaginationInfo paging, String groupId);

	/**
	 * 그룹이 없는 회원 리스트 
	 * @param paging
	 * @param groupId
	 * @return
	 */
	public List<MemberManagementVO> retrieveMemberListNoGroup(PaginationInfo paging, String groupId);
    
}