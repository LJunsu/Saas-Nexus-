package kr.or.nexus.sysadmin.group.service;

import java.util.List;

import kr.or.nexus.commons.paging.PaginationInfo;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.GroupRefusalVO;

/**
 * @author PC-22
 *
 */
public interface SAGroupService {
	
	/**
	 * 회원 목록 조회
	 * @return
	 */
	public List<GroupManagementVO> retrieveGroupList(PaginationInfo paging);
	
    /**
     * 상세 정보 조회
     * @param memId
     * @return 존재하지 않는 경우, {@link UserNotFoundException 발생}
     */
    public GroupManagementVO retrieveGroup(String groupId);

	/**
	 * 그룹 승인여부 변경
	 * @param groupManagementVO
	 */
	public void modifyGroupApproval(GroupManagementVO groupManagementVO);

	/**
	 * 그룹 거절사유 전송
	 * @param groupRefusalVO
	 */
	public void insertGroupRefusal(GroupRefusalVO groupRefusalVO);
}