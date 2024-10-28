package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.CommonDetailsDefaultVO;

@Mapper
public interface CommonDetailsDefaultMapper {
    int deleteByPrimaryKey(@Param("commDetailsId") String commDetailsId, @Param("comm") String comm);

    int insert(CommonDetailsDefaultVO row);

    CommonDetailsDefaultVO selectByPrimaryKey(@Param("commDetailsId") String commDetailsId, @Param("comm") String comm);

    List<CommonDetailsDefaultVO> selectAll();

    int updateByPrimaryKey(CommonDetailsDefaultVO row);
}