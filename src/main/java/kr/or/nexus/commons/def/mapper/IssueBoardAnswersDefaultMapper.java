package kr.or.nexus.commons.def.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.or.nexus.vo.def.IssueBoardAnswersDefaultVO;

@Mapper
public interface IssueBoardAnswersDefaultMapper {
    int deleteByPrimaryKey(@Param("issueCommentId") String issueCommentId, @Param("issueIdx") String issueIdx);

    int insert(IssueBoardAnswersDefaultVO row);

    IssueBoardAnswersDefaultVO selectByPrimaryKey(@Param("issueCommentId") String issueCommentId, @Param("issueIdx") String issueIdx);

    List<IssueBoardAnswersDefaultVO> selectAll();

    int updateByPrimaryKey(IssueBoardAnswersDefaultVO row);
}