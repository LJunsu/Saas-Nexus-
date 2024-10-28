package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.AnnouncementAnswersDefaultVO;

@Mapper
public interface AnnouncementAnswersDefaultMapper {
    int deleteByPrimaryKey(@Param("boardCmtId") Long boardCmtId, @Param("boardId") String boardId);

    int insert(AnnouncementAnswersDefaultVO row);

    AnnouncementAnswersDefaultVO selectByPrimaryKey(@Param("boardCmtId") Long boardCmtId, @Param("boardId") String boardId);

    List<AnnouncementAnswersDefaultVO> selectAll();

    int updateByPrimaryKey(AnnouncementAnswersDefaultVO row);
}