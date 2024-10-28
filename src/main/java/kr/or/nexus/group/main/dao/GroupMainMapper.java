package kr.or.nexus.group.main.dao;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.GroupManagementVO;

@Mapper
public interface GroupMainMapper {

	GroupManagementVO selectGroupById(String groupId);

	void updateGroup(GroupManagementVO group);

	int insertGroup(GroupManagementVO group);

	void updateGroupProfile(GroupManagementVO group);
	
}
