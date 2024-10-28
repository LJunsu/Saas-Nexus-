package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.IssueDateDefaultVO;

@Mapper
public interface IssueDateDefaultMapper {
    int deleteByPrimaryKey(@Param("issueDateIdx") String issueDateIdx, @Param("issueIdx") String issueIdx);

    int insert(IssueDateDefaultVO row);

    IssueDateDefaultVO selectByPrimaryKey(@Param("issueDateIdx") String issueDateIdx, @Param("issueIdx") String issueIdx);

    List<IssueDateDefaultVO> selectAll();

    int updateByPrimaryKey(IssueDateDefaultVO row);
}