package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.or.nexus.vo.def.InformationDefaultVO;

@Mapper
public interface InformationDefaultMapper {
    int deleteByPrimaryKey(String ifBoardId);

    int insert(InformationDefaultVO row);

    InformationDefaultVO selectByPrimaryKey(String ifBoardId);

    List<InformationDefaultVO> selectAll();

    int updateByPrimaryKey(InformationDefaultVO row);
}