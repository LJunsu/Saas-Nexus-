package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.GroupMemberDefaultVO;

@Mapper
public interface GroupMemberDefaultMapper {
    int deleteByPrimaryKey(@Param("groupId") String groupId, @Param("memberId") String memberId);

    int insert(GroupMemberDefaultVO row);

    GroupMemberDefaultVO selectByPrimaryKey(@Param("groupId") String groupId, @Param("memberId") String memberId);

    List<GroupMemberDefaultVO> selectAll();

    int updateByPrimaryKey(GroupMemberDefaultVO row);
}