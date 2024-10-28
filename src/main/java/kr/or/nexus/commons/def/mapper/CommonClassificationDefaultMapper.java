package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.CommonClassificationDefaultVO;

@Mapper
public interface CommonClassificationDefaultMapper {
    int deleteByPrimaryKey(String comm);

    int insert(CommonClassificationDefaultVO row);

    CommonClassificationDefaultVO selectByPrimaryKey(String comm);

    List<CommonClassificationDefaultVO> selectAll();

    int updateByPrimaryKey(CommonClassificationDefaultVO row);
}