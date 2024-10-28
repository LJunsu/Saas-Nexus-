package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.CustomerBoardDefaultVO;

@Mapper
public interface CustomerBoardDefaultMapper {
    int deleteByPrimaryKey(@Param("csId") String csId, @Param("memberId") String memberId);

    int insert(CustomerBoardDefaultVO row);

    CustomerBoardDefaultVO selectByPrimaryKey(@Param("csId") String csId, @Param("memberId") String memberId);

    List<CustomerBoardDefaultVO> selectAll();

    int updateByPrimaryKey(CustomerBoardDefaultVO row);
}