package kr.or.nexus.group.main.service;

import kr.or.nexus.enumpkg.ServiceResult;
import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.def.GroupManagementDefaultVO;

public interface GroupMainService {

	public GroupManagementDefaultVO retrieveGroup(String groupId);

	public GroupManagementVO retrieveGroupById(String groupId);

	public void modifyGroup(GroupManagementVO group);

	public ServiceResult createGroup(GroupManagementVO group);

	public ServiceResult createGroupWithAdmin(GroupManagementVO group, MemberManagementVO member);

	public void updateGroupProfile(GroupManagementVO group);
	
}
