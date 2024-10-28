package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.GroupManagementVO;
import kr.or.nexus.vo.def.GroupManagementDefaultVO;

@Mapper
public interface GroupManagementDefaultMapper {
    int deleteByPrimaryKey(String groupId);

    int insert(GroupManagementDefaultVO row);

    GroupManagementDefaultVO selectByPrimaryKey(String groupId);

    List<GroupManagementDefaultVO> selectAll();

    int updateByPrimaryKey(GroupManagementDefaultVO row);
}