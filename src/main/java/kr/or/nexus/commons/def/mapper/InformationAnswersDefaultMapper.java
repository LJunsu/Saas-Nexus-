package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.InformationAnswersDefaultVO;

@Mapper
public interface InformationAnswersDefaultMapper {
    int deleteByPrimaryKey(@Param("ifBoardCmtId") Long ifBoardCmtId, @Param("ifBoardId") String ifBoardId);

    int insert(InformationAnswersDefaultVO row);

    InformationAnswersDefaultVO selectByPrimaryKey(@Param("ifBoardCmtId") Long ifBoardCmtId, @Param("ifBoardId") String ifBoardId);

    List<InformationAnswersDefaultVO> selectAll();

    int updateByPrimaryKey(InformationAnswersDefaultVO row);
}