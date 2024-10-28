package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.MemberManagementVO;
import kr.or.nexus.vo.def.MemberManagementDefaultVO;

@Mapper
public interface MemberManagementDefaultMapper {
    int deleteByPrimaryKey(String memberId);

    int insert(MemberManagementDefaultVO row);

    MemberManagementVO selectByPrimaryKey(String memberId);

    List<MemberManagementDefaultVO> selectAll();

    int updateByPrimaryKey(MemberManagementDefaultVO row);

	List<MemberManagementVO> selectByEmail(String memberEmail);
}